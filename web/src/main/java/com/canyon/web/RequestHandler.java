package com.canyon.web;

import com.canyon.commons.JsonUtils;
import com.canyon.commons.StringUtils;
import com.canyon.core.TypeRef;
import com.canyon.web.parser.HandlerValueParser;
import com.canyon.web.router.WebRouter;
import com.canyon.web.router.WebRouterParam;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.RoutingContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class RequestHandler implements Handler<RoutingContext> {
    private WebRouter webRouter;
    private HandlerValueParser handlerValueParser;
    private WebParameterParser webParameterParser;

    public RequestHandler(WebRouter webRouter, HandlerValueParser handlerValueParser, WebParameterParser webParameterParser) {
        this.webRouter = webRouter;
        this.handlerValueParser = handlerValueParser;
        this.webParameterParser = webParameterParser;
    }

    @Override
    public void handle(RoutingContext rc) {
        HttpServerResponse response = rc.response();
        response.setChunked(true);
        Method method = webRouter.getMethod();
        Object[] parameters = new Object[webRouter.getWebParams().size()];
        for (int i = 0; i < parameters.length; i++) {
            WebRouterParam webParam = webRouter.getWebParams().get(i);
            Class<?> clazz = webParam.getClazz();
            if (clazz.equals(HttpServerRequest.class)) {
                parameters[i] = rc.request();
            } else if (clazz.equals(HttpServerResponse.class)) {
                parameters[i] = rc.response();
            } else if (clazz.equals(new TypeRef<List<FileUpload>>() {
            }.getType())) {
                if (StringUtils.isNotEmpty(webParam.getName())) {
                    List<FileUpload> lists = new ArrayList<>();
                    for (FileUpload file : rc.fileUploads()) {
                        if (webParam.getName().equals(file.name())) {
                            lists.add(file);
                        }
                    }
                    parameters[i] = lists;
                } else {
                    parameters[i] = new ArrayList<>(rc.fileUploads());
                }
            } else if (clazz.equals(RoutingContext.class)) {
                parameters[i] = rc;
            } else {
                String value = webParameterParser.parser(webParam, rc);
                parameters[i] = handlerValueParser.parser(webParam, value);
            }
        }

        try {
            Object retObj = method.invoke(webRouter.getController(), parameters);
            if (method.getReturnType() != null) {
                response.putHeader("content-type", webRouter.getResponse());
                if (retObj != null) {
                    if (MediaType.APPLICATION_JSON.equals(webRouter.getResponse())) {
                        response.write(JsonUtils.toString(retObj));
                    } else {
                        response.write(retObj.toString());
                    }
                }
            }
            response.end();
        } catch (IllegalAccessException e) {
            rc.fail(e);
        } catch (InvocationTargetException e) {
            rc.fail(e);
        }
    }
}

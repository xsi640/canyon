package com.canyon.web;

import com.canyon.commons.StringUtils;
import com.canyon.core.TypeRef;
import com.canyon.inject.Bean;
import com.canyon.web.router.WebRouter;
import com.canyon.web.router.WebRouterParam;
import io.vertx.ext.web.FileUpload;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Bean
public class StandardWebRouterParser implements WebRouterParser {

    @Override
    public List<WebRouter> parser(Object controller) {
        List<WebRouter> result = new ArrayList<>();
        Class<?> clazz = controller.getClass();
        Path path = clazz.getAnnotation(Path.class);
        for (Method method : clazz.getDeclaredMethods()) {
            WebMethod webMethod = method.getAnnotation(WebMethod.class);
            if (webMethod != null) {
                Path methodPath = method.getAnnotation(Path.class);
                String strPath = path != null ? path.path() : "";
                if (methodPath != null) {
                    strPath += methodPath.path();
                }
                if (StringUtils.isEmpty(strPath)) {
                    continue;
                }
                List<WebRouterParam> params = fillParameters(method);
                result.add(new WebRouter(strPath, Arrays.asList(webMethod.method()), webMethod.request(), webMethod.response(), params, method, controller));
            }
        }
        return result;
    }

    private List<WebRouterParam> fillParameters(Method method) {
        List<WebRouterParam> result = new ArrayList<>();
        if (method.getParameterCount() > 0) {
            for (Parameter param : method.getParameters()) {
                WebParam webParam = param.getAnnotation(WebParam.class);
                if (webParam == null) {
                    result.add(new WebRouterParam(param.getName(), From.ANY, "", param.getType()));
                } else {
                    if (StringUtils.isEmpty(webParam.name())) {
                        if (param.getType().equals(new TypeRef<List<FileUpload>>().getType())) {
                            result.add(new WebRouterParam("", webParam.from(), webParam.defaultValue(), param.getType()));
                        } else {
                            result.add(new WebRouterParam(param.getName(), webParam.from(), webParam.defaultValue(), param.getType()));
                        }
                    } else {
                        result.add(new WebRouterParam(webParam.name(), webParam.from(), webParam.defaultValue(), param.getType()));
                    }
                }
            }
        }
        return result;
    }
}

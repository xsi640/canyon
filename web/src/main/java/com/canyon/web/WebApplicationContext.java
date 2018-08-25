package com.canyon.web;

import com.canyon.boot.Boot;
import com.canyon.commons.StringUtils;
import com.canyon.inject.Autowire;
import com.canyon.inject.exceptions.InitializeException;
import com.canyon.inject.exceptions.NotFoundBeanException;
import com.canyon.web.parser.HandlerValueParser;
import com.canyon.web.router.WebRouter;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.StaticHandler;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class WebApplicationContext extends Boot {

    private Vertx vertx = Vertx.vertx();

    @Autowire
    private WebRouterParser webRouterParser;
    @Autowire
    private HandlerValueParser handlerValueParser;
    @Autowire
    private WebParameterParser webParameterParser;
    @Autowire
    private ExceptionHandler exceptionHandler;
    @Autowire
    private WebConfig config;
    @Autowire
    private Logger logger;

    private HttpServer httpServer;

    public WebApplicationContext() {
        super.order = -1000;
    }

    @Override
    public void run() throws Exception {
        logger.info(StringUtils.format("Prepare to start the web server...port:{0}", config.getPort()));
        List<WebRouter> routers = new ArrayList<>();
        try {
            List<Object> controllers = super.injectorContext.getBeansFromAnnotation(Controller.class, "");
            for (Object c : controllers) {
                List<WebRouter> rs = webRouterParser.parser(c);
                if (!rs.isEmpty()) {
                    routers.addAll(rs);
                }
            }
        } catch (Exception e) {
            logger.error(StringUtils.format("Loading controllers error. detail:{0}", e.getMessage()));
            throw e;
        }

        Router router = Router.router(vertx);
        router.route().handler(CookieHandler.create());
        String tempDir;
        try {
            tempDir = Files.createTempDirectory(config.getUploadTempDir()).toString();
        } catch (IOException e) {
            logger.error("Create temp dir error. temp dir path:" + config.getUploadTempDir());
            throw e;
        }
        router.route().handler(BodyHandler.create()
                .setMergeFormAttributes(false)
                .setDeleteUploadedFilesOnEnd(true)
                .setUploadsDirectory(tempDir));

        for (WebRouter r : routers) {
            if (!r.getMethods().isEmpty()) {
                for (HttpMethod method : r.getMethods()) {
                    logger.debug(StringUtils.format("Add route path:{0} method:{1} request:{2} response:{3}", r.getPath(), method, r.getRequest(), r.getResponse()));
                    router.route(WebRouterUtils.toMethod(method), r.getPath())
                            .consumes(r.getRequest())
                            .produces(r.getResponse())
                            .handler(rc -> new RequestHandler(r, handlerValueParser, webParameterParser).handle(rc));
                }
            }
        }
        router.route().handler(StaticHandler.create(config.getStaticRoot()));
        router.route().failureHandler(exceptionHandler);
        this.httpServer = vertx.createHttpServer().requestHandler(router::accept).listen(config.getPort());
        logger.info(StringUtils.format("Web server startup completed. port:{0}", config.getPort()));
    }

    @Override
    public void destory() {
        if (this.httpServer != null)
            this.httpServer.close();
    }
}

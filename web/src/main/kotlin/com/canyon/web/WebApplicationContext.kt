package com.canyon.web

import com.canyon.boot.Boot
import com.canyon.inject.Autowire
import io.vertx.core.Vertx
import io.vertx.core.http.HttpServer
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.handler.CookieHandler
import io.vertx.ext.web.handler.StaticHandler
import org.apache.logging.log4j.Logger

class WebApplicationContext : Boot() {

    private val vertx: Vertx = Vertx.vertx()

    @Autowire
    private var webRouterParser: WebRouterParser? = null
    @Autowire
    private var handlerValueParser: HandlerValueParser? = null
    @Autowire
    private var webParameterParser: WebParameterParser? = null
    @Autowire
    private var config: WebConfig? = null
    @Autowire
    private var logger: Logger? = null

    private var httpServer: HttpServer? = null

    init {
        super.order = -1000
    }

    override fun run() {
        logger!!.info("Prepare to start the web server...port:${config!!.port}")
        val routers = mutableListOf<WebRouter>()
        val controllers = super.injectorContext.getBeansFromAnnotation(Controller::class)
        for (controller in controllers) {
            val rs = webRouterParser!!.parser(controller)
            if (rs.isNotEmpty()) {
                routers.addAll(rs)
            }
        }

        val router = Router.router(vertx)
        router.route().handler(CookieHandler.create())
        val bodyHandler = BodyHandler.create()
                .setMergeFormAttributes(false)
                .setDeleteUploadedFilesOnEnd(true)
        router.route().handler(bodyHandler)
        for (r in routers) {
            if (r.method.isNotEmpty()) {
                for (method in r.method) {
                    logger!!.debug("Add route path:${r.path} method:${r.method} requestMediaType:${r.requestMediaType.toText()} responseMediaType:${r.responseMediaType.toText()}")
                    router.route(method.toMethod(), r.path)
                            .consumes(r.requestMediaType.toText())
                            .produces(r.responseMediaType.toText())
                            .handler { rc ->
                                RequestHandler(r, handlerValueParser!!, webParameterParser!!).handle(rc)
                            }
                }
            }
        }
        router.route().handler(StaticHandler.create(config!!.staticRoot))
        router.route().failureHandler(ExceptionHandler.create(config!!))
        this.httpServer = vertx.createHttpServer().requestHandler(router::accept).listen(config!!.port)
        logger!!.info("Web server startup completed. port:${config!!.port}")
    }

    override fun destory() {
        if (this.httpServer != null)
            this.httpServer!!.close()
    }
}
package com.canyon.web

import com.canyon.boot.Boot
import com.canyon.inject.Autowire
import io.vertx.core.Vertx
import io.vertx.ext.web.Router
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
        for (r in routers) {
            if (r.method.isNotEmpty()) {
                for (method in r.method) {
                    router.route(method.toMethod(), r.path)
                            .consumes(r.consumes.toText())
                            .produces(r.produces.toText())
                            .handler { rc ->
                                RequestHandler(r, handlerValueParser!!, webParameterParser!!).handle(rc)
                            }
                }
            }
        }
        vertx.createHttpServer().requestHandler(router::accept).listen(config!!.port)
        logger!!.info("Web server startup completed. port:${config!!.port}")
    }

    override fun destory() {
    }
}
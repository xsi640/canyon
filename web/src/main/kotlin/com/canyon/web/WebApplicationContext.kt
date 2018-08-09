package com.canyon.web

import com.canyon.boot.Boot
import com.canyon.inject.Autowire
import io.vertx.core.Vertx
import io.vertx.ext.web.Router

class WebApplicationContext : Boot() {

    private val vertx: Vertx = Vertx.vertx()

    @Autowire
    private var webRouterParser: WebRouterParser? = null
    @Autowire
    private var handlerValueParser: HandlerValueParser? = null
    @Autowire
    private var webParameterParser: WebParameterParser? = null

    override fun run() {
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
        vertx.createHttpServer().requestHandler(router::accept).listen(8080)
    }

    override fun destory() {
    }
}
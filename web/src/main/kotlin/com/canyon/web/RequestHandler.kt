package com.canyon.web

import io.vertx.core.Handler
import io.vertx.ext.web.RoutingContext

class RequestHandler(
        val webRouter: WebRouter
) : Handler<RoutingContext> {
    override fun handle(event: RoutingContext) {

    }
}
package com.canyon.web

import io.vertx.core.Handler
import io.vertx.ext.web.RoutingContext
import java.io.PrintWriter
import java.io.StringWriter

class ExceptionHandler(
        val config: WebConfig
) : Handler<RoutingContext> {

    override fun handle(rc: RoutingContext) {
        if (config.exceptionOutput) {
            val sw = StringWriter()
            PrintWriter(sw).use { pw ->
                rc.failure().printStackTrace(pw)
            }
            rc.response().end(sw.toString())
        } else {
            rc.next()
        }
    }

    companion object {
        fun create(config: WebConfig): ExceptionHandler {
            return ExceptionHandler(config)
        }
    }
}
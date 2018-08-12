package com.canyon.web

import io.vertx.core.Handler
import io.vertx.ext.web.RoutingContext
import java.io.PrintWriter
import java.io.StringWriter

class ExceptionHandler(
        val config: WebConfig
) : Handler<RoutingContext> {

    override fun handle(rc: RoutingContext) {
        if (rc.failure() is WebException) {
            val ex = rc.failure() as WebException
            rc.response().statusCode = ex.statusCode
        } else {
            rc.response().statusCode = 500
        }

        if (config.exceptionOutput) {
            val sw = StringWriter()
            PrintWriter(sw).use { pw ->
                rc.failure().printStackTrace(pw)
            }
            rc.response().end(sw.toString())
        } else {
            val statusCode = rc.response().statusCode
            if (config.errorPages!!.containsKey(statusCode.toString())) {
                rc.response().statusCode = 302
                rc.response().putHeader("content-type", MediaType.TEXT_HTML.toText())
                rc.response().putHeader("Location", config.errorPages!![statusCode.toString()]!!.unwrapped().toString())
                rc.response().end()
            } else {
                rc.next()
            }
        }
    }

    companion object {
        fun create(config: WebConfig): ExceptionHandler {
            return ExceptionHandler(config)
        }
    }
}
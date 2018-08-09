package com.canyon.web

import com.canyon.commons.JsonUtils
import io.vertx.core.Handler
import io.vertx.ext.web.RoutingContext
import java.nio.charset.StandardCharsets
import kotlin.reflect.jvm.jvmErasure

class RequestHandler(
        val webRouter: WebRouter
) : Handler<RoutingContext> {
    override fun handle(rc: RoutingContext) {
        val response = rc.response()

        val func = webRouter.function
        val parameters = arrayOfNulls<Any?>(webRouter.webParam.size)
        for (i in 0 until parameters.size) {
            parameters[i] = webRouter.webParam[i].toValue(rc)
        }
        val ret = func.call(webRouter.controller, *parameters)
        if (func.returnType != Unit::class && ret != null) {
            response.putHeader("content-type", webRouter.consumes.toText())
            if (ret !== Unit) {
                if (webRouter.consumes == MIME.JSON || ret !is String) {
                    response.end(JsonUtils.toString(ret))
                } else {
                    response.end(ret)
                }
            }
        }
    }
}

fun WebRouterParam.toValue(rc: RoutingContext): Any? {
    return when (this.from) {
        From.PATH, From.QUERY -> rc.request().getParam(this.name)
        From.HEADER -> rc.request().getHeader(this.name)
        From.ENTITY -> {
            val data = rc.body.bytes
            when (this.kType) {
                String::class -> String(data, StandardCharsets.UTF_8)
                else -> {
                    JsonUtils.parse(String(data, StandardCharsets.UTF_8), this.kType.jvmErasure.java)
                }
            }
        }
        From.COOKIE -> rc.getCookie(this.name).value
        else -> {
            rc.request().getParam(this.name)
        }
    }

}
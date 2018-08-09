package com.canyon.web

import com.canyon.commons.JsonUtils
import com.canyon.inject.Bean
import io.vertx.core.Handler
import io.vertx.core.http.HttpServerResponse
import io.vertx.ext.web.RoutingContext

@Bean
class RequestHandler(
        private val webRouter: WebRouter,
        private val handlerValueParser: HandlerValueParser,
        private val webParameterParser: WebParameterParser
) : Handler<RoutingContext> {
    override fun handle(rc: RoutingContext) {
        val response = rc.response()

        val func = webRouter.function
        val parameters = arrayOfNulls<Any?>(webRouter.webParam.size)
        for (i in 0 until parameters.size) {
            val webParam = webRouter.webParam[i]
            val strValue = webParameterParser.parser(webParam, rc)
            parameters[i] = handlerValueParser.parser(webParam, strValue)
        }
        val ret = func.call(webRouter.controller, *parameters)
        if (func.returnType != Unit::class && ret != null) {
            response.putHeader("content-type", webRouter.consumes.toText())
            if (ret !== Unit) {
                writeResponse(response, webRouter.consumes, ret)
            }
        }
        response.end()
    }

    private fun writeResponse(response: HttpServerResponse, mediaType: MediaType, obj: Any?) {
        if (obj == null)
            return
        when (mediaType) {
            MediaType.APPLICATION_JSON -> response.end(JsonUtils.toString(obj))
            else -> response.write(obj.toString())
        }
    }
}

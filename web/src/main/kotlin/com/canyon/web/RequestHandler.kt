package com.canyon.web

import com.canyon.commons.JsonUtils
import com.canyon.core.TypeRef
import com.canyon.inject.Bean
import io.vertx.core.Handler
import io.vertx.core.http.HttpServerRequest
import io.vertx.core.http.HttpServerResponse
import io.vertx.ext.web.FileUpload
import io.vertx.ext.web.RoutingContext
import kotlin.reflect.full.createType

@Bean
class RequestHandler(
        private val webRouter: WebRouter,
        private val handlerValueParser: HandlerValueParser,
        private val webParameterParser: WebParameterParser
) : Handler<RoutingContext> {
    override fun handle(rc: RoutingContext) {
        val response = rc.response()
        response.isChunked = true
        val func = webRouter.function
        val parameters = arrayOfNulls<Any?>(webRouter.webParam.size)
        for (i in 0 until parameters.size) {
            val webParam = webRouter.webParam[i]
            webParam.kType == object : TypeRef<List<FileUpload>> {}.kType
            when {
                webParam.kType.classifier == HttpServerRequest::class -> parameters[i] = rc.request()
                webParam.kType.classifier == HttpServerResponse::class -> parameters[i] = rc.response()
                webParam.kType == object : TypeRef<List<FileUpload>> {}.kType -> {
                    if(webParam.name.isNotEmpty()) {
                        val files = mutableListOf<FileUpload>()
                        rc.fileUploads().forEach {
                            if (it.name() == webParam.name) {
                                files.add((it))
                            }
                        }
                        parameters[i] = files
                    }else{
                        parameters[i] = rc.fileUploads().toList()
                    }
                }
                webParam.kType.classifier == RoutingContext::class -> parameters[i] = rc
                else -> {
                    val strValue = webParameterParser.parser(webParam, rc)
                    parameters[i] = handlerValueParser.parser(webParam, strValue)
                }
            }
        }
        val ret = func.call(webRouter.controller, *parameters)
        if (func.returnType != Unit::class && ret != null) {
            response.putHeader("content-type", webRouter.responseMediaType.toText())
            if (ret !== Unit) {
                writeResponse(response, webRouter.responseMediaType, ret)
            }
        }
        response.end()
    }

    private fun writeResponse(response: HttpServerResponse, mediaType: MediaType, obj: Any?) {
        if (obj == null)
            return
        when (mediaType) {
            MediaType.APPLICATION_JSON -> response.end(JsonUtils.toString(obj))
            else -> response.end(obj.toString())
        }
    }
}

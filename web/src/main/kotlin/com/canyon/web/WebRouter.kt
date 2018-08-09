package com.canyon.web

import io.vertx.core.http.HttpMethod
import kotlin.reflect.KFunction
import kotlin.reflect.KType

data class WebRouter(
        val path: String,
        val method: List<Method>,
        val consumes: MediaType,
        val produces: MediaType,
        val webParam: List<WebRouterParam>,
        val function: KFunction<*>,
        val controller: Any
)

data class WebRouterParam(
        val name: String,
        val from: From,
        val default: String,
        val kType: KType
)

fun Method.toMethod(): HttpMethod {
    return when (this) {
        Method.GET -> HttpMethod.GET
        Method.POST -> HttpMethod.POST
        Method.HEAD -> HttpMethod.HEAD
        Method.PUT -> HttpMethod.PUT
        Method.DELETE -> HttpMethod.DELETE
        else -> throw IllegalArgumentException("Can't support The method.")
    }
}
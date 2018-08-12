package com.canyon.web

import kotlin.reflect.KFunction
import kotlin.reflect.KType

data class WebRouter(
        val path: String,
        val method: List<HttpMethod>,
        val requestMediaType: MediaType,
        val responseMediaType: MediaType,
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

fun HttpMethod.toMethod(): io.vertx.core.http.HttpMethod {
    return when (this) {
        HttpMethod.GET -> io.vertx.core.http.HttpMethod.GET
        HttpMethod.POST -> io.vertx.core.http.HttpMethod.POST
        HttpMethod.HEAD -> io.vertx.core.http.HttpMethod.HEAD
        HttpMethod.PUT -> io.vertx.core.http.HttpMethod.PUT
        HttpMethod.DELETE -> io.vertx.core.http.HttpMethod.DELETE
        else -> throw IllegalArgumentException("Can't support The method.")
    }
}
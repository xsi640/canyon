package com.canyon.web

import com.canyon.core.Description

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Controller

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Path(
        val path: String
)

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class WebMethod(
        vararg val method: Method,
        val requestMediaType: MediaType = MediaType.ALL,
        val responseMediaType: MediaType = MediaType.ALL
)

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class WebParam(
        val name: String = "",
        val from: From = From.ANY,
        val default: String = ""
)

enum class Method {
    GET, POST, HEAD, PUT, DELETE
}

enum class From {
    ANY, PATH, QUERY, FORM, HEADER, ENTITY, COOKIE
}

enum class MediaType {
    @Description("*/*")
    ALL,
    @Description("application/x-www-form-urlencoded")
    APPLICATION_FORM_URLENCODED,
    @Description("application/json")
    APPLICATION_JSON,
    @Description("text/plain;charset=utf-8")
    TEXT_PLAIN,
    @Description("text/html;charset=utf-8")
    TEXT_HTML
}

fun MediaType.toText(): String {
    return this::class.java.getField(this.name).getAnnotation(Description::class.java).description
}
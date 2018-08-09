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
        val consumes: MIME = MIME.FORM_URLENCODED,
        val produces: MIME = MIME.FORM_URLENCODED
)

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class WebParam(
        val name: String,
        val from: From = From.ANY,
        val default: String = ""
)

enum class Method {
    GET, POST, HEAD, PUT, DELETE
}

enum class From {
    ANY, PATH, QUERY, FORM, HEADER, ENTITY, COOKIE
}

enum class MIME {
    @Description("text/plain; charset=UTF-8")
    TEXT_PLAIN,
    @Description("text/html; charset=UTF-8")
    TEXT_HTML,
    @Description("application/x-www-form-urlencoded")
    FORM_URLENCODED,
    @Description("application/json")
    JSON
}

fun MIME.toText(): String {
    this.declaringClass.annotations.forEach {
        if (it.annotationClass == Description::class) {
            return (it as Description).description
        }
    }
    return ""
}
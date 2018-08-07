package com.canyon.web

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Controller

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class RestController

@Target(AnnotationTarget.CLASS, AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class Path(
        vararg val path: String
)

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class WebMethod(
        vararg val method: Method,
        val consumes: String = "",
        val produces: String = ""
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
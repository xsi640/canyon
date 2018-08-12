package com.canyon.web

open class WebException(
        message: String,
        cause: Throwable,
        val statusCode: Int) : Exception(message,
        cause)
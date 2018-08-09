package com.canyon.web

import com.canyon.inject.Autowire
import com.canyon.inject.Bean

interface HandlerValueParser {
    fun parser(webParam: WebRouterParam, value: String): Any?
}

@Bean(singleton = true)
class HandlerValueParserImpl : HandlerValueParser {

    @Autowire
    private var parsers: List<WebParamConverter<*>>? = null

    override fun parser(webParam: WebRouterParam, value: String): Any? {
        return null
        /*
        return when (webParam.kType.jvmErasure) {
            Int::class ->
        }*/
    }
}

package com.canyon.web

import com.canyon.inject.Autowire
import com.canyon.inject.Bean
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KType
import kotlin.reflect.full.starProjectedType
import kotlin.reflect.jvm.jvmErasure

interface HandlerValueParser {
    fun parser(webParam: WebRouterParam, value: String): Any?
}

@Bean(singleton = true)
class HandlerValueParserImpl : HandlerValueParser {

    @Autowire
    private var parsers: List<WebParamConverter<*>>? = null

    private val map = ConcurrentHashMap<KType, WebParamConverter<*>>()

    override fun parser(webParam: WebRouterParam, value: String): Any? {
        if (webParam.kType == String::class.starProjectedType)
            return value

        val converter = map.computeIfAbsent(webParam.kType) { type ->
            parsers!!.find { c ->
                type.jvmErasure == ((c::class.java.genericInterfaces[0] as ParameterizedTypeImpl).actualTypeArguments[0] as Class<*>).kotlin
            }!!
        }
        return converter.convert(value)
    }
}

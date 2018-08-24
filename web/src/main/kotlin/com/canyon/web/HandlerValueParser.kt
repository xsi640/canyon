package com.canyon.web

import com.canyon.commons.JsonUtils
import com.canyon.inject.Autowire
import com.canyon.inject.Bean
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KType
import kotlin.reflect.jvm.javaType
import kotlin.reflect.jvm.jvmErasure

interface HandlerValueParser {
    fun parser(webParam: WebRouterParam, value: String?): Any?
}

@Bean(true)
class HandlerValueParserImpl : HandlerValueParser {

    @Autowire
    private var parsers: List<WebParamConverter<*>>? = null

    private val map = ConcurrentHashMap<KType, WebParamConverter<*>>()

    override fun parser(webParam: WebRouterParam, value: String?): Any? {
        var v = value
        if (value == null && webParam.default.isNotEmpty()) {
            v = webParam.default
        }
        if (v == null && !webParam.kType.isMarkedNullable) {
            throw IllegalArgumentException("The name ${webParam.name} of ${webParam.from} is required.")
        }
        if (v == null && webParam.kType.isMarkedNullable) {
            v = ""
        }
        if (webParam.kType.javaType == String::class.java) {
            return v
        }
        return if (webParam.from == From.ENTITY) {
            try {
                JsonUtils.parse(v!!, webParam.kType.javaType as Class<*>)
            } catch (ex: Exception) {
                throw IllegalArgumentException("The name ${webParam.name} of ${webParam.from} format failured. value:$v")
            }
        } else {
            val converter = map.computeIfAbsent(webParam.kType) { type ->
                parsers!!.find { c ->
                    type.jvmErasure == ((c::class.java.genericInterfaces[0] as ParameterizedTypeImpl).actualTypeArguments[0] as Class<*>).kotlin
                }!!
            }
            try {
                converter.convert(v!!)
            } catch (ex: Exception) {
                throw IllegalArgumentException("The name ${webParam.name} of ${webParam.from} format failured. value:$v")
            }
        }
    }
}

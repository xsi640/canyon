package com.canyon.core

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import kotlin.reflect.KClass
import kotlin.reflect.KType

typealias MethodInvoker = (kType: KType, method: Method, args: Array<Any>) -> Any

class DynamicInvocationHandler(
        private val kType: KType,
        private val methodInvoker: MethodInvoker
) : InvocationHandler {
    override fun invoke(proxy: Any, method: Method, args: Array<Any>): Any? {
        return methodInvoker(kType, method, args)
    }
}

object ProxyFactory {
    fun newInstance(kType: KType, invoker: MethodInvoker): Any {
        val clazz = (kType.classifier!! as KClass<*>).java
        return Proxy.newProxyInstance(clazz.classLoader, arrayOf(clazz), DynamicInvocationHandler(kType, invoker))
    }
}
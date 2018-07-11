package com.canyon.core

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import kotlin.reflect.KClass

class DynamicInvocationHandler(private val methodInvoker: (clazz: Class<*>, method: Method, args: Array<Any>) -> Any?) : InvocationHandler {
    override fun invoke(proxy: Any, method: Method, args: Array<Any>): Any? {
        return methodInvoker(proxy::class.java, method, args)
    }
}

object ProxyFactory {
    fun newInstance(clazz: KClass<*>, methodInvoker: (clazz: Class<*>, method: Method, args: Array<Any>) -> Any): Any? {
        return Proxy.newProxyInstance(clazz.java.classLoader, arrayOf<Class<*>>(clazz.java), DynamicInvocationHandler(methodInvoker))
    }
}
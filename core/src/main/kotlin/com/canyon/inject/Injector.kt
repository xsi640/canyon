package com.canyon.inject

import kotlin.reflect.KClass

interface Injector {
    fun <T : Any> getBean(kClass: KClass<T>): T
    fun <T : Any> getBean(kClass: KClass<T>, name: String): T
}

@Bean
class InjectorImpl : Injector {

    @Autowire
    private var injectorContext: InjectorContext? = null

    override fun <T : Any> getBean(kClass: KClass<T>): T {
        return injectorContext!!.getBean(kClass) as T
    }

    override fun <T : Any> getBean(kClass: KClass<T>, name: String): T {
        var lists = injectorContext!!.getBeansFromInterface(kClass, name)
        if (lists.isNotEmpty()) {
            return lists[0]
        } else {
            lists = injectorContext!!.getBeansFromSuper(kClass, name)
            if (lists.isNotEmpty()) {
                return lists[0]
            }
        }
        throw IllegalArgumentException("Not found Bean class{$kClass}.")
    }
}
package com.canyon.inject

import kotlin.reflect.KClass

/**
 * 对于没有实例化的Bean，需要注入，需要实现此接口
 * 如：Config配置
 */
interface InjectProvider {
    fun isMatch(kClass: KClass<*>): Boolean
    fun create(objClass: KClass<*>, fieldClass: KClass<*>): Any
}
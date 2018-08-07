package com.canyon.commons

import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
fun <T : Annotation> List<Annotation>.find(kClass: KClass<T>): T? {
    var result: T? = null
    this.forEach {
        if (it.annotationClass == kClass) {
            result = it as T?
        }
    }
    return result
}

@Suppress("UNCHECKED_CAST")
fun <T : Annotation> Array<Annotation>.find(kClass: KClass<T>): T? {
    var result: T? = null
    this.forEach {
        if (it.annotationClass == kClass) {
            result = it as T?
        }
    }
    return result
}
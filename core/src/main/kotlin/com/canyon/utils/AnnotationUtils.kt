package com.canyon.utils

import kotlin.reflect.KClass

fun <T : Annotation> List<Annotation>.find(kClass: KClass<T>): T? {
    var result: T? = null
    this.forEach {
        if (it.annotationClass == kClass) {
            result = it as T?
        }
    }
    return result
}

fun <T : Annotation> Array<Annotation>.find(kClass: KClass<T>): T? {
    var result: T? = null
    this.forEach {
        if (it.annotationClass == kClass) {
            result = it as T?
        }
    }
    return result
}
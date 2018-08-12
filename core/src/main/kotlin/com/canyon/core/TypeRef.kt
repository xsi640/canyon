package com.canyon.core

import com.canyon.commons.findOne
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.reflect.KType

interface TypeRef<T> {
    val javaType: Type
        get() {
            return (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
        }
    val kType: KType
        get() {
            return this::class.supertypes.findOne { it.classifier == TypeRef::class }!!.arguments[0].type!!
        }
}
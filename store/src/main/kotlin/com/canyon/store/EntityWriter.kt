package com.canyon.store

import com.canyon.inject.Bean
import kotlin.reflect.KClass

interface EntityWriter<T> {
    fun insert(t: T): T
    fun insert(list: List<T>): List<T>
    fun update(t: T): T
    fun update(list: List<T>): List<T>
    fun delete(id: String): Int
    fun delete(idList: List<String>): Int
}

interface EntityWriterBuilder {
    fun create(kClass: KClass<*>): EntityWriter<*>
}

@Bean
class EntityWriterBuilderImpl:EntityWriterBuilder{

    override fun create(kClass: KClass<*>): EntityWriter<*> {
        TODO()
    }

}
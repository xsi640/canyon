package com.canyon.store

import com.canyon.core.Reflection
import java.lang.reflect.Method
import kotlin.reflect.KType

interface EntityWriter<T> {
    fun insert(t: T): T
    fun insert(list: List<T>): List<T>
    fun update(t: T): T
    fun update(list: List<T>): List<T>
    fun delete(id: String): Int
    fun delete(idList: List<String>): Int
}

interface EntityWriterExecutor {
    fun execute(kType: KType, method: Method, args: Array<Any>): Any?
}

class EntityWriterExecutorImpl : EntityWriterExecutor {
    override fun execute(kType: KType, method: Method, args: Array<Any>): Any? {
        val clazz = kType.classifier!!.javaClass
        if (!EntityWriter::class.java.isAssignableFrom(clazz)) {
            throw IllegalArgumentException("The class not impl entityWriter")
        }
        var entityClazz = Reflection.getSuperInterfaceInnerClass(clazz, EntityWriter::class.java)
        if (entityClazz == null) {
            throw IllegalArgumentException("The class impl entityWriter is not entity.")
        }
        when (method.name) {
            "insert" -> {
                if (args[0].javaClass == entityClazz) {

                }
            }
        }
        TODO("反射数据库操作")
    }
}
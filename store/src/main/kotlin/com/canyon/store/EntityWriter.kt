package com.canyon.store

import com.canyon.core.Reflection
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl
import java.lang.reflect.Method

interface EntityWriter<T> {
    fun insert(t: T): T
    fun insert(list: List<T>): List<T>
    fun update(t: T): T
    fun update(list: List<T>): List<T>
    fun delete(id: String): Int
    fun delete(idList: List<String>): Int
}

interface EntityWriterExecutor {
    fun execute(clazz: Class<*>, method: Method, args: Array<Any>): Any?
}

class EntityWriterExecutorImpl : EntityWriterExecutor {
    override fun execute(clazz: Class<*>, method: Method, args: Array<Any>): Any? {
        if (!EntityWriter::class.java.isAssignableFrom(clazz)) {
            throw IllegalArgumentException("The class not impl entityWriter")
        }
        var entityClazz = Reflection.getSuperInterfaceInnerClass(clazz, EntityWriter::class.java)
        if (entityClazz == null) {
            throw IllegalArgumentException("The class impl entityWriter is not entity.")
        }
        when (method.name) {
            "insert" -> {
                if(args[0].javaClass == entityClazz){

                }
            }
        }
        TODO("反射数据库操作")
    }

}
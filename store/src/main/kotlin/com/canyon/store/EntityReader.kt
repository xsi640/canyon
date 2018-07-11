package com.canyon.store

import java.lang.reflect.Method

interface EntityReader<T> {
    fun get(id: String): T?
    fun getAll(idList: List<String>): List<T>
    fun find(expression: Expression): List<T>
    fun findOne(expression: Expression): T?
    fun findPage(expression: Expression): Page<T>
}

data class Page<T>(
        val total: Int,
        val items: List<T>
)

interface EntityReaderExecutor {
    fun execute(clazz: Class<*>, method: Method, args: Array<Any>): Any?
}
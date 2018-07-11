package com.canyon.store

interface EntityWriter<T> {
    fun insert(t: T): T
    fun insert(list: List<T>): List<T>
    fun update(t: T): T
    fun update(list: List<T>): List<T>
    fun delete(id: String)
    fun delete(idList: List<String>)
}
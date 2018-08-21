package com.canyon.store.ref

import kotlin.reflect.KClass

/**
 * 表示一个表
 */
data class TableRef(
        val tableName: String,
        val kClass: KClass<*>,
        val primaryKey: ColumnRef,
        val columns: List<ColumnRef>,
        val indexRefs: List<IndexRef>
)
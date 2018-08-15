package com.canyon.store.ref

import java.util.*
import kotlin.reflect.KClass

data class TableRef(
        val tableName: String,
        val kClass: KClass<*>,
        val primaryKey: ColumnRef,
        val columns: List<ColumnRef>,
        val indexRefs: List<IndexRef>
)
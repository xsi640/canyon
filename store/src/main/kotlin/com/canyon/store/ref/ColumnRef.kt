package com.canyon.store.ref

import kotlin.reflect.KProperty1

data class ColumnRef(
        val primaryKey: Boolean,
        val columnName: String,
        val nullable: Boolean,
        val length: Int,
        val property: KProperty1<out Any, Any?>
)
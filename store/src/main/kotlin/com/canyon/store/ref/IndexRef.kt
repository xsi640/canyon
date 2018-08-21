package com.canyon.store.ref

/**
 * 代表一个索引
 */
data class IndexRef(
        val indexName: String,
        val columnRefs: List<ColumnRef>,
        val unique: Boolean
)
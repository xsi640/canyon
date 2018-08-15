package com.canyon.store.ref

data class IndexRef(
        val indexName: String,
        val columnRefs: List<ColumnRef>,
        val unique: Boolean
)
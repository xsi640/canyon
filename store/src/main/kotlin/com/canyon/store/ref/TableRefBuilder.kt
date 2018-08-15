package com.canyon.store.ref

import com.canyon.commons.findAll
import com.canyon.inject.Bean
import com.canyon.store.Column
import com.canyon.store.Entity
import com.canyon.store.Id
import com.canyon.store.Index
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.jvm.jvmName

interface TableRefBuilder {
    fun build(kClass: KClass<*>): TableRef
}

@Bean
class TableRefBuilderImpl : TableRefBuilder {

    val cache = ConcurrentHashMap<KClass<*>, TableRef>()

    override fun build(kClass: KClass<*>): TableRef {
        val tableRef = cache[kClass]
        if (tableRef == null) {
            val entity = kClass.findAnnotation<Entity>()
                    ?: throw IllegalArgumentException("The ${kClass.jvmName} class is not entity annotation.")
            val tableName = if (entity.name.isNotEmpty()) {
                entity.name
            } else {
                kClass.jvmName
            }
            var columnPrimaryKey: ColumnRef? = null
            val columnRefs = mutableListOf<ColumnRef>()
            kClass.declaredMemberProperties.forEach { prop ->
                val column = prop.findAnnotation<Column>()
                val columnRef = ColumnRef(
                        prop.findAnnotation<Id>() != null,
                        if (column != null && column.name.isNotEmpty()) column.name else prop.name,
                        column != null && column.nullable,
                        column?.length ?: 255,
                        prop
                )
                if (columnRef.primaryKey) {
                    columnPrimaryKey = columnRef
                } else {
                    columnRefs.add(columnRef)
                }
            }
            if (columnPrimaryKey == null)
                throw IllegalArgumentException("The entity not found primary key.")
            val indexes = kClass.annotations.findAll({ it.annotationClass == Index::class }, false) as List<Index>
            val indexRefs = indexes.map { index ->
                val indexColumnRefs = mutableListOf<ColumnRef>()
                index.columnList.forEach { name ->
                    val columnRef = columnRefs.find { it.columnName == name }
                    if (columnRef == null)
                        throw IllegalArgumentException("The index $name  not with the entity $tableName.")
                    else
                        indexColumnRefs.add(columnRef)
                }
                IndexRef(index.name,
                        indexColumnRefs,
                        index.unique)

            }
            val tableRef0 = TableRef(
                    tableName,
                    kClass,
                    columnPrimaryKey!!,
                    columnRefs,
                    indexRefs)

            cache.putIfAbsent(kClass, tableRef0)
            return tableRef0
        } else {
            return tableRef
        }
    }

}

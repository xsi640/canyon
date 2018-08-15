package com.canyon.store

import com.canyon.inject.Autowire
import com.canyon.inject.Bean
import com.canyon.store.ref.TableRefBuilder
import kotlin.reflect.KClass

interface SqlParser<T : Any> {
    fun insert(t: T): SqlCommand
    fun update(t: T): SqlCommand
    fun delete(id: Any): SqlCommand
}

@Bean
class SqlParserImpl<T : Any>(
        val kClass: KClass<T>
) : SqlParser<T> {

    @Autowire
    private val tableRefBuilder: TableRefBuilder? = null

    override fun insert(t: T): SqlCommand {
        val tableRef = tableRefBuilder!!.build(kClass)
        val sb = StringBuilder("INSERT INTO `")
        sb.append(tableRef.tableName)
        sb.append("`(`")
        sb.append(tableRef.primaryKey.columnName)
        sb.append("`")
        tableRef.columns.forEach { column ->
            sb.append(",`")
            sb.append(column.columnName)
            sb.append("`")
        }
        sb.append(") VALUES")
        tableRef.columns.joinTo(sb, ",", "(", ")")

        val parameters = mutableListOf<Any?>()
        parameters += tableRef.primaryKey.property.getter.call(t)
        parameters.addAll(tableRef.columns.map { it.property.getter.call(t) })
        return SqlCommand(
                sb.toString(),
                parameters)
    }

    override fun update(t: T): SqlCommand {
        val tableRef = tableRefBuilder!!.build(kClass)
        val sb = StringBuilder("UPDATE `")
        sb.append(tableRef.tableName)
        sb.append("` SET ")
        tableRef.columns.joinTo(sb, "`=?, `", "`", "`")
        sb.append(" WHERE `")
        sb.append(tableRef.primaryKey.columnName)
        sb.append("`=?")

        val parameters = mutableListOf<Any?>()
        parameters.addAll(tableRef.columns.map { it.property.getter.call(t) })
        parameters.add(tableRef.primaryKey.property.getter.call(t))

        return SqlCommand(sb.toString(), parameters)
    }

    override fun delete(id: Any): SqlCommand {
        val tableRef = tableRefBuilder!!.build(kClass)
        val sb = StringBuilder("DELETE FROM `")
        sb.append(tableRef.tableName)
        sb.append("` WHERE `")
        sb.append(tableRef.primaryKey.columnName)
        sb.append("`=?")

        return SqlCommand(sb.toString(), mutableListOf(id))
    }
}
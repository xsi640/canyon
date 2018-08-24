package com.canyon.store

import com.canyon.inject.Autowire
import com.canyon.inject.Bean
import java.math.BigDecimal
import java.sql.PreparedStatement
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

interface EntityWriter<T : Any> {
    fun insert(t: T): T
    fun insert(list: List<T>): List<T>
    fun update(t: T): T
    fun update(list: List<T>): List<T>
    fun delete(id: String): Int
    fun delete(idList: List<String>): Int
}

interface EntityWriterBuilder {
    fun create(kClass: KClass<*>): EntityWriter<*>
}

@Bean
class EntityWriterBuilderImpl : EntityWriterBuilder, EntityWriter<Any> {

    @Autowire
    private var dataSource: DataSource? = null

    private var sqlParser: SqlParser<Any>? = null

    companion object {
        val mapper = ConcurrentHashMap<KClass<*>, EntityWriter<*>>()
    }

    override fun create(kClass: KClass<*>): EntityWriter<*> {
        sqlParser = SqlParserImpl(kClass) as SqlParser<Any>
        return this
    }

    override fun insert(t: Any): Any {
        val conn = dataSource!!.open()
        val command = sqlParser!!.insert(t)
        val ps = conn.prepareStatement(command.sql)
        for (index in command.parameters.indices) {
            ps.addParameter(index + 1, command.parameters[index])
        }
        ps.execute()
        return t
    }

    override fun insert(list: List<Any>): List<Any> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(t: Any): Any {
        val conn = dataSource!!.open()
        val command = sqlParser!!.update(t)
        val ps = conn.prepareStatement(command.sql)
        for (index in command.parameters.indices) {
            ps.addParameter(index + 1, command.parameters[index])
        }
        ps.execute()
        ps.closeOnCompletion()
        return t
    }

    override fun update(list: List<Any>): List<Any> {
        val conn = dataSource!!.open()
        conn.autoCommit = false
        for (any in list) {
            val command = sqlParser!!.update(any)
            val ps
        }

    }

    override fun delete(id: String): Int {
        val conn = dataSource!!.open()
        val command = sqlParser!!.delete(id)
        val ps = conn.prepareStatement(command.sql)
        for (index in command.parameters.indices) {
            ps.addParameter(index + 1, command.parameters[index])
        }
        return ps.executeUpdate()
    }

    override fun delete(idList: List<String>): Int {

    }
}

fun PreparedStatement.addParameter(index: Int, parameter: SqlParameter) {
    when (parameter.columnRef.property.returnType) {
        Int::class -> {
            this.setInt(index, parameter.value as Int)
        }
        Long::class -> {
            this.setLong(index, parameter.value as Long)
        }
        Boolean::class -> {
            this.setBoolean(index, parameter.value as Boolean)
        }
        Byte::class -> {
            this.setByte(index, parameter.value as Byte)
        }
        Short::class -> {
            this.setShort(index, parameter.value as Short)
        }
        Float::class -> {
            this.setFloat(index, parameter.value as Float)
        }
        Double::class -> {
            this.setDouble(index, parameter.value as Double)
        }
        BigDecimal::class -> {
            this.setBigDecimal(index, parameter.value as BigDecimal)
        }
        String::class -> {
            this.setString(index, parameter.value as String)
        }
        ByteArray::class -> {
            this.setBytes(index, parameter.value as ByteArray)
        }
        Date::class -> {
            this.setDate(index, java.sql.Date((parameter.value as Date).time))
        }
        else -> {
            throw IllegalArgumentException("not support the type.")
        }
    }
}
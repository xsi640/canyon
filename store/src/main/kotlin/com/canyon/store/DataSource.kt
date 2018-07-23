package com.canyon.store

import com.canyon.config.ConfigFactory
import com.canyon.inject.Autowire
import com.mchange.v2.c3p0.ComboPooledDataSource

interface DataSource {
    fun open(): Connection
    fun close(conn: Connection)
}

interface Connection {
    fun beginTransaction()
    fun commit()
    fun rollback()
    fun close()
}

interface DatabaseCommand {
    fun <T> query(sql: String, parameters: List<Any>, clazz: Class<T>): List<T>
    fun <T> queryOne(sql: String, parameters: List<Any>, clazz: Class<T>): T
    fun update(sql: String, parameters: List<Any>): Int
    fun execute(sql: String)
}

//@Autowire
//class C3P0DataSource : DataSource {
//
//    private var dataSource: ComboPooledDataSource = ComboPooledDataSource()
//    private var config = ConfigFactory.load("dataSource", StoreConfig::class.java)
//
//    init {
//        this.dataSource.driverClass = config.driverClass
//        this.dataSource.jdbcUrl = config.jdbcUrl
//        this.dataSource.user = config.user
//        this.dataSource.password = config.password
//
//        this.dataSource.minPoolSize = config.pool.minPoolSize
//        this.dataSource.acquireIncrement = config.pool.acquireIncrement
//        this.dataSource.maxPoolSize = config.pool.maxPoolSize
//        this.dataSource.maxIdleTime = config.pool.maxIdleTime
//    }
//
//    override fun open(): Connection {
//        return this.dataSource.connection
//    }
//
//    override fun close(conn: Connection) {
//        conn.close()
//    }
//}
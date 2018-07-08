package com.canyon.store

import com.canyon.inject.Autowire
import com.mchange.v2.c3p0.ComboPooledDataSource
import java.sql.Connection

interface DataSource {
    fun open(): Connection
    fun close(conn: Connection)
}


@Autowire
class C3P0DataSource(config: PoolStoreConfig) : DataSource {

    var dataSource: ComboPooledDataSource = ComboPooledDataSource()

    init {
        this.dataSource.driverClass = config.driverClass
        this.dataSource.jdbcUrl = config.jdbcUrl
        this.dataSource.user = config.user
        this.dataSource.password = config.password

        this.dataSource.minPoolSize = config.minPoolSize
        this.dataSource.acquireIncrement = config.acquireIncrement
        this.dataSource.maxPoolSize = config.maxPoolSize
        this.dataSource.maxIdleTime = config.maxIdleTime
    }

    override fun open(): Connection {
        return this.dataSource.connection
    }

    override fun close(conn: Connection) {
        conn.close()
    }
}
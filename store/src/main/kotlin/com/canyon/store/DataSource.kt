package com.canyon.store

import com.canyon.inject.Autowire
import com.canyon.inject.Bean
import com.canyon.inject.Named
import com.zaxxer.hikari.HikariDataSource
import java.sql.Connection

interface DataSource {
    fun open(): Connection
    fun close(conn: Connection)
}

@Named("hikari")
@Bean
class HikariDataSource : DataSource {

    @Autowire
    private var config: StoreConfig? = null
    private val dataSource: HikariDataSource by lazy {
        initDataSource()
    }

    private fun initDataSource(): HikariDataSource {
        val dataSource = HikariDataSource()
        dataSource.jdbcUrl = config!!.jdbcUrl
        dataSource.username = config!!.username
        dataSource.password = config!!.password

        val hikari = config!!.hikari!!

        dataSource.isAutoCommit = hikari.autoCommit
        dataSource.connectionTimeout = hikari.connectionTimeout
        dataSource.idleTimeout = hikari.idleTimeout
        dataSource.maxLifetime = hikari.maxLifetime
        dataSource.minimumIdle = hikari.minimumIdle
        dataSource.maximumPoolSize = hikari.maximumPoolSize

        dataSource.addDataSourceProperty("cachePrepStmts", hikari.cachePrepStmts)
        dataSource.addDataSourceProperty("prepStmtCacheSize", hikari.prepStmtCacheSize)
        dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", hikari.prepStmtCacheSqlLimit)
        return dataSource
    }

    override fun open(): Connection {
        return this.dataSource.connection
    }

    override fun close(conn: Connection) {
        conn.close()
    }
}
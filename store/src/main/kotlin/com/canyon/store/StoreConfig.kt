package com.canyon.store

open class StoreConfig(
        val driverClass: String,
        val jdbcUrl: String,
        val user: String,
        val password: String,
        val pool: PoolConfig
)

class PoolConfig(
        val minPoolSize: Int = 3,
        val maxPoolSize: Int = 10,
        val acquireIncrement: Int = 5,
        val maxIdleTime: Int = 240
)
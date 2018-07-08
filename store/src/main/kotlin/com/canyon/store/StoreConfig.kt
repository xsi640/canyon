package com.canyon.store

open class StoreConfig(
        open val driverClass: String,
        open val jdbcUrl: String,
        open val user: String,
        open val password: String
)

class PoolStoreConfig(
        override val driverClass: String,
        override val jdbcUrl: String,
        override val user: String,
        override val password: String,
        val minPoolSize: Int = 3,
        val maxPoolSize: Int = 10,
        val acquireIncrement: Int = 5,
        val maxIdleTime: Int = 240
) : StoreConfig(
        driverClass,
        jdbcUrl,
        user,
        password
)
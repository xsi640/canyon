package com.canyon.store

import com.canyon.inject.Config

@Config("data")
class StoreConfig {
    var jdbcUrl: String = ""
    var username: String = ""
    var password: String = ""
    var poolType: String = ""
    var hikari: Hikari? = null
}

class Hikari {
    var autoCommit: Boolean = true
    var connectionTimeout: Long = 30000
    var idleTimeout: Long = 600000
    var maxLifetime: Long = 1800000
    var minimumIdle: Int = 10
    var maximumPoolSize: Int = 10

    var cachePrepStmts: Boolean = true
    var prepStmtCacheSize: Int = 250
    var prepStmtCacheSqlLimit: Int = 2048
}
package com.canyon.store

interface DatabaseClient {
    fun openConnection()
    fun closeConnection()
}
package com.canyon.core

object Assertions {
    fun <T> notNull(name: String, value: T): T {
        if (value == null)
            throw IllegalArgumentException("$name can not be null")
        return value
    }

    fun <T> notNullOrEmpty(name: String, value: T): T {
        if (value == null || value.toString().isEmpty())
            throw IllegalArgumentException("$name can not be empty")
        return value
    }

    fun isTrue(name: String, value: Boolean) {
        if (!value)
            throw IllegalArgumentException("$name must be true")
    }

    fun isFalse(name: String, value: Boolean) {
        if (value)
            throw IllegalArgumentException("$name must be true")
    }
}
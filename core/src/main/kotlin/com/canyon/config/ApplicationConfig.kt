package com.canyon.config

import com.typesafe.config.ConfigBeanFactory
import com.typesafe.config.ConfigFactory

object ConfigFactory {
    val config = ConfigFactory.parseResources("application.conf").resolve().getConfig("canyon")

    fun <T> load(path: String, clazz: Class<T>): T {
        return ConfigBeanFactory.create(config.getConfig(path), clazz)
    }

    fun getString(path: String): String {
        return this.config.getString(path)
    }
}
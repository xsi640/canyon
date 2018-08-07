package com.canyon.inject

import com.typesafe.config.ConfigBeanFactory
import com.typesafe.config.ConfigFactory
import java.io.InputStreamReader

interface ConfigLoader {
    fun <T> load(fileName: String, clazz: Class<T>): T
}

@Autowire
class ConfigLoaerImpl : ConfigLoader {
    override fun <T> load(fileName: String, clazz: Class<T>): T {
        val inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName)
        val config = ConfigFactory.parseReader(InputStreamReader(inputStream))
        return ConfigBeanFactory.create(config, clazz)
    }
}
package com.canyon.inject

import com.typesafe.config.ConfigBeanFactory
import com.typesafe.config.ConfigFactory
import java.io.InputStreamReader
import kotlin.reflect.KClass

interface ConfigLoader {
    fun <T : Any> load(fileName: String, clazz: KClass<T>): T
}

@Bean
class ConfigLoaerImpl : ConfigLoader {
    override fun <T : Any> load(fileName: String, clazz: KClass<T>): T {
        val inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName)
        val config = ConfigFactory.parseReader(InputStreamReader(inputStream))
        return ConfigBeanFactory.create(config, clazz.java)
    }
}
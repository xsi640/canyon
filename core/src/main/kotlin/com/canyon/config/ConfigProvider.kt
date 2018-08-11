package com.canyon.config

import com.canyon.commons.find
import com.canyon.inject.Config
import com.canyon.inject.InjectProvider
import kotlin.reflect.KClass

class ConfigProvider : InjectProvider {

    override fun isMatch(kClass: KClass<*>): Boolean {
        return kClass.annotations.firstOrNull {
            it.annotationClass == Config::class
        } != null
    }

    override fun create(objClass: KClass<*>, fieldClass: KClass<*>): Any {
        val conf = fieldClass.annotations.find(Config::class)
        return if (conf!!.path.isEmpty()) {
            ConfigFactory.load("application.conf", fieldClass.java)
        } else {
            ConfigFactory.load(conf.path, fieldClass.java)
        }
    }
}
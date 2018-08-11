package com.canyon.logger

import com.canyon.inject.InjectProvider
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import kotlin.reflect.KClass
import kotlin.reflect.jvm.jvmName

class LoggerInjectProvider : InjectProvider {
    override fun isMatch(kClass: KClass<*>): Boolean {
        return kClass == Logger::class
    }
    override fun create(objClass: KClass<*>, fieldClass: KClass<*>): Any {
        return LogManager.getLogger(objClass.jvmName)
    }
}
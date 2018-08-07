package com.canyon.inject

import kotlin.reflect.full.createInstance

interface BeanFactory {
    var injectorContext: InjectorContext?
    fun createBean(classType: ClassType): Any
}

class BeanFactoryImpl : BeanFactory {
    override var injectorContext: InjectorContext? = null

    override fun createBean(classType: ClassType): Any {
        return if (classType.single) {
            if (injectorContext!!.injectedCached.containsKey(classType)) {
                injectorContext!!.injectedCached[classType]!!
            } else {
                buildBean(classType)
            }
        } else {
            buildBean(classType)
        }
    }

    private fun buildBean(classType: ClassType): Any {
        val instance = classType.kClass.createInstance()
        if (classType.dependentProperties.isNotEmpty()) {
            classType.dependentProperties.forEach { prop ->
                val propInstance = this.createBean(prop.classType)
                prop.field.set(instance, propInstance)
            }
        }
        return instance
    }
}
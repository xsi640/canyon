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
                gruntBean(classType)
            }
        } else {
            gruntBean(classType)
        }
    }

    private fun gruntBean(classType: ClassType): Any {
        var instance = classType.kClass.createInstance()
        if (classType.dependentProperties.isNotEmpty()) {
            classType.dependentProperties.forEach { prop ->
                var propInstance = this.createBean(prop.classType)
                prop.field.set(instance, propInstance)
            }
        }
        return instance
    }
}
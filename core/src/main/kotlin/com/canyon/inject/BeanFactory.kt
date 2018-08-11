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
                if (prop.provider != null) {
                    if (prop is SingleDependentProperty) {
                        val propInstance = this.createByProvider(classType, prop.classType, prop.provider!!)
                        prop.field.set(instance, propInstance)
                    } else if (prop is MultiDependentProperty) {
                        val lists = prop.classTypes.map {
                            this.createByProvider(classType, it, prop.provider!!)
                        }
                        prop.field.set(instance, lists)
                    }
                } else {
                    if (prop is SingleDependentProperty) {
                        val propInstance = this.createBean(prop.classType)
                        prop.field.set(instance, propInstance)
                    } else if (prop is MultiDependentProperty) {
                        val lists = prop.classTypes.map {
                            this.createBean(it)
                        }
                        prop.field.set(instance, lists)
                    }
                }


            }
        }
        return instance
    }

    private fun createByProvider(objClass: ClassType, fieldClassType: ClassType, provider: InjectProvider): Any {
        return provider.create(objClass.kClass, fieldClassType.kClass)
    }
}
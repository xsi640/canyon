package com.canyon.inject

import java.lang.reflect.Field
import kotlin.reflect.full.superclasses
import kotlin.reflect.jvm.jvmName

/**
 * 依赖处理器，从Classes中整理依赖关系
 */
interface DependenciesProcessor {
    fun process(classes: List<ClassType>)
}

class DependenciesProcessorImpl : DependenciesProcessor {
    override fun process(classes: List<ClassType>) {
        classes.forEach { classType ->
            classType.dependentProperties.addAll(grunt(classType, classes))
        }
    }

    private fun grunt(classType: ClassType, classes: List<ClassType>): List<DependentProperty> {
        var props = ArrayList<DependentProperty>()
        classType.kClass.java.declaredFields.forEach { field ->
            if (field.isAnnotationPresent(Autowire::class.java)) {
                field.isAccessible = true
                var autowire = field.getAnnotation(Autowire::class.java)
                var named = field.getAnnotation(Named::class.java)
                var name = if (named == null) "" else named.value
                props.add(DependentProperty(
                        name,
                        field,
                        field.type.kotlin,
                        findKClass(field, name, classes)
                ))
            }
        }
        return props
    }

    private fun findKClass(field: Field, name: String, classes: List<ClassType>): ClassType {
        var result: ClassType? = null
        if (field.type.isInterface) {
            var items = classes.filter {
                it.kClass.superclasses.contains(field.type.kotlin)
            }
            if (name.isEmpty() && items.size == 1) {
                result = items.get(0)
            } else if (name.isNotEmpty() && items.size > 1) {
                result = items.find {
                    it.name == name
                }
                if (result == null) {
                    result = items.find {
                        it.kClass.jvmName == name
                    }
                }
            }
        } else {
            result = classes.find {
                it.kClass == field.type.kotlin
            }
        }
        if (result == null) {
            throw  NotfoundDependencies("Not found Inject Class from field{${field.name}} in class{${field.type}}.")
        }
        return result
    }
}
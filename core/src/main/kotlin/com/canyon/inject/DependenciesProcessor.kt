package com.canyon.inject

import com.canyon.commons.findOne
import java.lang.reflect.Field
import java.lang.reflect.ParameterizedType
import java.lang.reflect.WildcardType
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.superclasses

/**
 * 依赖处理器，从Classes中整理依赖关系
 */
interface DependenciesProcessor {
    fun process(classes: List<ClassType>)
}

class DependenciesProcessorImpl : DependenciesProcessor {

    private val providers = mutableListOf<InjectProvider>()

    override fun process(classes: List<ClassType>) {
        classes.forEach {
            if (it.kClass.superclasses.contains(InjectProvider::class))
                providers.add(it.kClass.createInstance() as InjectProvider)
        }

        classes.forEach { classType ->
            classType.dependentProperties.addAll(grunt(classType, classes))
        }
    }

    private fun grunt(classType: ClassType, classes: List<ClassType>): List<BaseDependentProperty> {
        val props = ArrayList<BaseDependentProperty>()
        classType.kClass.java.declaredFields.forEach { field ->
            if (field.isAnnotationPresent(Autowire::class.java)) {
                field.isAccessible = true
                val named = field.getAnnotation(Named::class.java)
                val name = named?.value ?: ""
                if (field.type as Class<*> == List::class.java ||
                        field.type as Class<*> == Set::class.java) {
                    props.add(MultiDependentProperty(
                            "",
                            field,
                            field.type.kotlin,
                            findProvider(field),
                            findKClassList(field, name, classes)
                    ))
                } else {
                    @Suppress("NAME_SHADOWING")
                    val name = field.getAnnotation(Named::class.java)?.value ?: ""
                    props.add(SingleDependentProperty(
                            name,
                            field,
                            field.type.kotlin,
                            findProvider(field),
                            findKClass(field, name, classes)
                    ))
                }
            }
        }
        return props
    }


    private fun findKClassList(field: Field, name: String, classes: List<ClassType>): List<ClassType> {
        val result: List<ClassType> = classes.filter {
            var flag = field.isChildrenClass(it.kClass)
            if (flag) {
                if (name.isNotEmpty()) {
                    flag = it.name == name
                }
            }
            flag
        }
        if (result.isEmpty())
            throw NotfoundDependencies("Not found Inject Class from field{${field.name}} in class{${field.type}}.")
        return result
    }

    private fun findKClass(field: Field, name: String, classes: List<ClassType>): ClassType {
        val result: List<ClassType> = classes.filter {
            var flag = field.isChildrenClass(it.kClass)
            if (flag) {
                if (name.isNotEmpty()) {
                    flag = it.name == name
                }
            }
            flag
        }

        if (result.isEmpty())
            return field.type.toClassType()
        return result[0]
    }

    private fun findProvider(field: Field): InjectProvider? {
        val clazz = field.type
        return providers.findOne {
            it.isMatch(clazz.kotlin)
        }
    }
}

fun Field.isChildrenClass(kClass: KClass<*>): Boolean {
    if (kClass == this.type.kotlin)
        return true
    if (kClass.superclasses.contains(this.type.kotlin))
        return true
    if (this.genericType is ParameterizedType) {
        val parameterizedType = this.genericType as ParameterizedType
        val type = parameterizedType.actualTypeArguments[0]
        if (type is WildcardType) {
            val rawType = (type.upperBounds[0] as ParameterizedType).rawType
            if (kClass.superclasses.contains((rawType as Class<*>).kotlin)) {
                return true
            }
        }
    }
    return false
}
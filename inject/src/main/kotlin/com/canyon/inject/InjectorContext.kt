package com.canyon.inject

import kotlin.reflect.KClass
import kotlin.reflect.full.superclasses

/**
 * 依赖注入上下文
 */
interface InjectorContext {
    var injectedCached: MutableMap<ClassType, Any>
    var classTypeCached: MutableMap<KClass<*>, ClassType>

    fun excludedClass(kClass: KClass<*>)
    fun registAnnotation(kClass: KClass<*>)
    fun registSuperclass(kClass: KClass<*>)
    fun registInterface(kClass: KClass<*>)
    fun initialize()

    fun getBean(kClass: KClass<*>): Any
    fun <T : Any> getBeanFromSuper(kClass: KClass<T>): List<T>
}

class InjectorContextImpl(
        val classScanner: ClassScanner,
        val dependenciesProcessor: DependenciesProcessor,
        val beanFactory: BeanFactory,
        val packages: List<String>,
        override var injectedCached: MutableMap<ClassType, Any> = mutableMapOf(),
        override var classTypeCached: MutableMap<KClass<*>, ClassType> = mutableMapOf()
) : InjectorContext {
    var initialized = false
    var regAnnotations = mutableSetOf<KClass<*>>()
    var regSuperclasses = mutableSetOf<KClass<*>>()
    var regInterfaceClasses = mutableListOf<KClass<*>>()
    var excludedClasses = mutableSetOf<KClass<*>>()

    override fun getBean(kClass: KClass<*>): Any {
        if (!initialized) {
            throw InitializeException("InjectorContext initializing...")
        }
        var classType: ClassType? = classTypeCached.get(kClass)
                ?: throw NotFoundBeanException("Not found Bean class{${kClass}}.")
        return beanFactory.createBean(classType!!)
    }

    override fun <T : Any> getBeanFromSuper(kClass: KClass<T>): List<T> {
        if (!initialized) {
            throw InitializeException("InjectorContext initializing...")
        }
        var result = mutableListOf<T>()
        classTypeCached.keys.forEach {
            if (it.superclasses.contains(kClass)) {
                result.add(this.getBean(it) as T)
            }
        }
        return result
    }

    override fun registAnnotation(kClass: KClass<*>) {
        regAnnotations.add(kClass)
    }

    override fun registSuperclass(kClass: KClass<*>) {
        regSuperclasses.add(kClass)
    }

    override fun registInterface(kClass: KClass<*>) {
        regInterfaceClasses.add(kClass)
    }

    override fun excludedClass(kClass: KClass<*>) {
        excludedClasses.add(kClass)
    }

    override fun initialize() {
        classTypeCached.clear()

        var mutableList = mutableSetOf<ClassType>()
        regAnnotations.forEach {
            var classTypes = classScanner.scanByAnnotation(packages, it)
            if (classTypes.isNotEmpty())
                mutableList.addAll(classTypes)
        }
        regSuperclasses.forEach {
            var classTypes = classScanner.scanBySuperClass(packages, it)
            if (classTypes.isNotEmpty())
                mutableList.addAll(classTypes)
        }
        regInterfaceClasses.forEach {
            var classTypes = classScanner.scanByInterface(packages, it)
            if (classTypes.isNotEmpty())
                mutableList.addAll(classTypes)
        }

        dependenciesProcessor.process(mutableList.toList())

        mutableList.forEach { classType ->
            if (excludedClasses.find { classType.kClass == it } == null) {
                classTypeCached.put(classType.kClass, classType)
            }
        }

        this.initialized = true
    }
}
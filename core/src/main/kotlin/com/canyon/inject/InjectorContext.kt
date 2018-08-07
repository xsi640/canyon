package com.canyon.inject

import kotlin.reflect.KClass
import kotlin.reflect.full.superclasses

/**
 * 依赖注入上下文
 */
interface InjectorContext {
    /**
     * 已注入的Class的Cache
     */
    var injectedCached: MutableMap<ClassType, Any>

    var classTypeCached: MutableMap<KClass<*>, ClassType>

    fun excludedClass(kClass: KClass<*>)
    fun registAnnotation(kClass: KClass<*>)
    fun registSuperclass(kClass: KClass<*>)
    fun registInterface(kClass: KClass<*>)
    fun initialize()

    fun getBean(kClass: KClass<*>): Any
    fun <T : Any> getBeansFromSuper(kClass: KClass<T>): List<T>
    fun <T : Annotation> getBeansFromAnnotation(kClass: KClass<T>): List<Any>
    fun <T : Any> getBeansFromInterface(kClass: KClass<T>): List<T>
}

class InjectorContextImpl(
        private val classScanner: ClassScanner,
        private val dependenciesProcessor: DependenciesProcessor,
        private val beanFactory: BeanFactory,
        private val packages: List<String>,
        override var injectedCached: MutableMap<ClassType, Any> = mutableMapOf(),
        override var classTypeCached: MutableMap<KClass<*>, ClassType> = mutableMapOf()
) : InjectorContext {
    private var initialized = false
    private var regAnnotations = mutableSetOf<KClass<*>>()
    private var regSuperclasses = mutableSetOf<KClass<*>>()
    private var regInterfaceClasses = mutableListOf<KClass<*>>()
    private var excludedClasses = mutableSetOf<KClass<*>>()

    override fun getBean(kClass: KClass<*>): Any {
        if (!initialized) {
            throw InitializeException("InjectorContext initializing...")
        }
        val classType: ClassType? = classTypeCached.get(kClass)
                ?: throw NotFoundBeanException("Not found Bean class{$kClass}.")
        return beanFactory.createBean(classType!!)
    }

    override fun <T : Annotation> getBeansFromAnnotation(kClass: KClass<T>): List<Any> {
        if (!initialized) {
            throw InitializeException("InjectorContext initializing...")
        }
        val result = mutableListOf<Any>()
        classTypeCached.keys.forEach {
            if (it.annotations.firstOrNull { it::class == kClass } != null) {
                result.add(this.getBean(it))
            }
        }
        return result
    }

    override fun <T : Any> getBeansFromInterface(kClass: KClass<T>): List<T> {
        return getBeansFromSuper(kClass)
    }

    override fun <T : Any> getBeansFromSuper(kClass: KClass<T>): List<T> {
        if (!initialized) {
            throw InitializeException("InjectorContext initializing...")
        }
        val result = mutableListOf<T>()
        classTypeCached.keys.forEach {
            if (it.superclasses.contains(kClass)) {
                @Suppress("UNCHECKED_CAST")
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

        val mutableList = mutableSetOf<ClassType>()
        regAnnotations.forEach {
            val classTypes = classScanner.scanByAnnotation(packages, it)
            if (classTypes.isNotEmpty())
                mutableList.addAll(classTypes)
        }
        regSuperclasses.forEach {
            val classTypes = classScanner.scanBySuperClass(packages, it)
            if (classTypes.isNotEmpty())
                mutableList.addAll(classTypes)
        }
        regInterfaceClasses.forEach {
            val classTypes = classScanner.scanByInterface(packages, it)
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
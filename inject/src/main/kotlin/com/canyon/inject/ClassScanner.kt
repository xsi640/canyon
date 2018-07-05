package com.canyon.inject

import com.canyon.utils.find
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner
import java.lang.reflect.Field
import kotlin.reflect.KClass

/**
 * 类扫描器，从package中扫描匹配的Class
 */
interface ClassScanner {
    fun scanByAnnotation(packageNames: List<String>, annotationType: KClass<*>, threadCount: Int = Runtime.getRuntime().availableProcessors()): List<ClassType>
    fun scanBySuperClass(packageNames: List<String>, superClass: KClass<*>, threadCount: Int = Runtime.getRuntime().availableProcessors()): List<ClassType>
    fun scanByInterface(packageNames: List<String>, interfaceClass: KClass<*>, threadCount: Int = Runtime.getRuntime().availableProcessors()): List<ClassType>
}

/**
 * 描述类
 */
data class ClassType(
        val name: String,
        val kClass: KClass<*>,
        val single: Boolean,
        val dependentProperties: MutableList<DependentProperty>
)

/**
 * 描述类中，需要依赖注入的属性
 */
data class DependentProperty(
        val name: String,
        val field: Field,
        val kClass: KClass<*>,
        val classType: ClassType
)

@Suppress("MoveLambdaOutsideParentheses")
class ClassScannerImpl : ClassScanner {
    override fun scanByInterface(packageNames: List<String>, interfaceClass: KClass<*>, threadCount: Int): List<ClassType> {
        val classList = ArrayList<ClassType>()
        var fastClasspathScanner = FastClasspathScanner(*packageNames.toTypedArray())
        fastClasspathScanner.matchClassesImplementing(interfaceClass.java, { clazz ->
            classList.add(toClassType(clazz))
        }).scan(threadCount)
        return classList
    }

    override fun scanBySuperClass(packageNames: List<String>, superClass: KClass<*>, threadCount: Int): List<ClassType> {
        val classList = ArrayList<ClassType>()
        var fastClasspathScanner = FastClasspathScanner(*packageNames.toTypedArray())
        fastClasspathScanner.matchSubclassesOf(superClass.java, { clazz ->
            classList.add(toClassType(clazz))
        }).scan(threadCount)
        return classList
    }

    override fun scanByAnnotation(packageNames: List<String>, annotationType: KClass<*>, threadCount: Int): List<ClassType> {
        val classList = ArrayList<ClassType>()
        var fastClasspathScanner = FastClasspathScanner(*packageNames.toTypedArray())
        fastClasspathScanner.matchClassesWithAnnotation(annotationType.java, { clazz ->
            classList.add(toClassType(clazz))
        }).scan(threadCount)
        return classList
    }
}

fun toClassType(clazz: Class<*>): ClassType {
    var bean = clazz.annotations.find(Bean::class)
    var named = clazz.annotations.find(Named::class)
    return ClassType(
            if (named == null) "" else named.value,
            Class.forName(clazz.canonicalName).kotlin as KClass<*>,
            if (bean == null) false else bean.singleton,
            ArrayList()

    )
}
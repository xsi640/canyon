package com.canyon.inject

import com.canyon.commons.find
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
        val dependentProperties: MutableList<BaseDependentProperty>
)

/**
 * 描述类中，需要依赖注入的属性
 */
abstract class BaseDependentProperty(
        open val name: String,
        open val field: Field,
        open val kClass: KClass<*>
)

class SingleDependentProperty(
        override val name: String,
        override val field: Field,
        override val kClass: KClass<*>,
        val classType: ClassType
) : BaseDependentProperty(
        name,
        field,
        kClass
)

class MultiDependentProperty(
        override val name: String,
        override val field: Field,
        override val kClass: KClass<*>,
        val classTypes: List<ClassType>
) : BaseDependentProperty(
        name,
        field,
        kClass
)

@Suppress("MoveLambdaOutsideParentheses")
class ClassScannerImpl : ClassScanner {
    override fun scanByInterface(packageNames: List<String>, interfaceClass: KClass<*>, threadCount: Int): List<ClassType> {
        val classList = ArrayList<ClassType>()
        val fastClasspathScanner = FastClasspathScanner(*packageNames.toTypedArray())
        fastClasspathScanner.matchClassesImplementing(interfaceClass.java, { clazz ->
            classList.add(clazz.toClassType())
        }).scan(threadCount)
        return classList
    }

    override fun scanBySuperClass(packageNames: List<String>, superClass: KClass<*>, threadCount: Int): List<ClassType> {
        val classList = ArrayList<ClassType>()
        val fastClasspathScanner = FastClasspathScanner(*packageNames.toTypedArray())
        fastClasspathScanner.matchSubclassesOf(superClass.java, { clazz ->
            classList.add(clazz.toClassType())
        }).scan(threadCount)
        return classList
    }

    override fun scanByAnnotation(packageNames: List<String>, annotationType: KClass<*>, threadCount: Int): List<ClassType> {
        val classList = ArrayList<ClassType>()
        val fastClasspathScanner = FastClasspathScanner(*packageNames.toTypedArray())
        fastClasspathScanner.matchClassesWithAnnotation(annotationType.java, { clazz ->
            classList.add(clazz.toClassType())
        }).scan(threadCount)
        return classList
    }
}

fun Class<*>.toClassType(): ClassType {
    val bean = this.annotations.find(Bean::class)
    val named = this.annotations.find(Named::class)
    return ClassType(
            named?.value ?: "",
            Class.forName(this.canonicalName).kotlin as KClass<*>,
            bean?.singleton ?: false,
            ArrayList()
    )
}
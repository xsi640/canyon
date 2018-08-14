package com.canyon.inject

/**
 * 标注在属性上，表示这个属性的类型可以通过依赖注入直接创建
 */
@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Autowire

/**
 * 标注在类上，表示这个类的实例是可以被依赖注入创建的
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Bean(
        val singleton: Boolean = false
)

/**
 * 标注在类上，表示这个类的实例名称，通过autowire区分注入的示例
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class Named(
        val value: String = ""
)

@Bean(singleton = true)
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Config(
        val path: String = "",
        val fileName: String = ""
)

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class Value(
        val path: String
)

/**
 * 扫描的包名称
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class BeanScan(
        vararg val packages: String)
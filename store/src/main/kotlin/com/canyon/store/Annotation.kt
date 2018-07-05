package com.canyon.store

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Table(
        val name: String = "",
        val index: Array<Index>
)

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Id

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class Column(
        val name: String = "",
        val unique: Boolean = false,
        val nullable: Boolean = true,
        val length: Int = 255,
        val columnDef: String = ""
)

@Target
@Retention(AnnotationRetention.RUNTIME)
annotation class Index(
        val name: String,
        val columnList: Array<String>,
        val unique: Boolean = false
)

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class Version

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class LastUpdateTime
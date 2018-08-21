package com.canyon.store

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Entity(
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
        val nullable: Boolean = true,
        /**
         * length == Int.MAX_VALUE 表示text
         */
        val length: Int = 255
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

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Query(
        val sql: String
)
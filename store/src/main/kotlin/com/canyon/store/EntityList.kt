package com.canyon.store

interface EntityList<T> {
    fun where(expression: Expression): Limitable<T>
}

interface Limitable<T> : Orderable<T> {
    fun limit(skip: Long, take: Long): Orderable<T>
}

interface Orderable<T> : Queryable<T> {
    fun orderBy(vararg orderExpression: OrderExpression): Queryable<T>
}

interface Queryable<T> {
    fun query(): List<T>
}
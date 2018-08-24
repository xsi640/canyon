package com.canyon.store

import com.canyon.inject.Autowire
import com.canyon.inject.Bean
import com.canyon.store.ref.TableRef
import com.canyon.store.ref.TableRefBuilder
import kotlin.reflect.KClass

interface SqlBuilder {
    fun from(clazz: KClass<*>): FromAfter
}

interface WhereAble {
    fun where(expression: Expression): WhereAfter
}

interface LimitAble {
    fun limit(offset: Long, count: Long): LimitAfter
}

interface OrderAble {
    fun order(order: OrderExpression): QueryAble
}

interface SelectAble {
    fun select(vararg fieldNames: String): SelectAfter
}

interface DistinctAble {
    fun distinct(): DistinctAfter
}

interface CountAble {
    fun count(): CountAfter
}

interface QueryAble {
    fun build(): List<*>
}


interface FromAfter : WhereAble, SelectAble

interface WhereAfter : SelectAble, OrderAble, LimitAble, QueryAble, CountAble

interface LimitAfter : OrderAble, QueryAble

interface SelectAfter : OrderAble, DistinctAble

interface DistinctAfter : QueryAble

interface CountAfter : QueryAble

@Bean
class SqlBuilderImpl : SqlBuilder, FromAfter, WhereAfter, LimitAfter, SelectAfter, DistinctAfter, CountAfter {
    @Autowire
    private val tableRefBuilder: TableRefBuilder? = null

    var tableRef: TableRef? = null
    var whereExpr: Expression? = null
    var fields: Fields? = null
    var limit: Pair<Long, Long>? = null
    var order: OrderExpression? = null
    var count: Boolean = false
    var distinct: Boolean = false

    override fun from(clazz: KClass<*>): FromAfter {
        tableRef = tableRefBuilder!!.build(clazz)
        return this
    }

    override fun where(expression: Expression): WhereAfter {
        whereExpr = expression
        return this
    }

    override fun select(vararg fieldNames: String): SelectAfter {
        this.fields = Fields(fieldNames)
        return this
    }

    override fun limit(offset: Long, count: Long): LimitAfter {
        this.limit = Pair(offset, count)
        return this
    }

    override fun order(order: OrderExpression): QueryAble {
        this.order = order
        return this
    }

    override fun count(): CountAfter {
        this.count = true
        return this
    }

    override fun distinct(): DistinctAfter {
        this.distinct = true
        return this
    }

    override fun build(): List<*> {
        TODO()
    }
}
package com.canyon.store

import com.canyon.core.Assertions
import java.util.*

abstract class Expression {
    /**
     * 等于
     */
    fun eq(right: Expression): Expression {
        return RelaExpression(this, RelaOperator.EQ, right)
    }

    /**
     * 不等于
     */
    fun ne(right: Expression): Expression {
        return RelaExpression(this, RelaOperator.NE, right)
    }

    /**
     * 大于
     */
    fun gt(right: Expression): Expression {
        return RelaExpression(this, RelaOperator.GT, right)
    }

    /**
     * 小于
     */
    fun lt(right: Expression): Expression {
        return RelaExpression(this, RelaOperator.LT, right)
    }

    /**
     * 大于等于
     */
    fun gte(right: Expression): Expression {
        return RelaExpression(this, RelaOperator.GTE, right)
    }

    /**
     * 小于等于
     */
    fun lte(right: Expression): Expression {
        return RelaExpression(this, RelaOperator.LTE, right)
    }

    /**
     * like
     */
    fun like(right: Expression): Expression {
        return RelaExpression(this, RelaOperator.LIKE, right)
    }

    /**
     * IN
     */
    fun in0(right: Collection<Expression>): Expression {
        return MultRelaExpression(this, MultiRelaOperator.IN, right)
    }

    /**
     * IN
     */
    fun in0(vararg right: Expression): Expression {
        return this.in0(*right)
    }

    /**
     * NOT IN
     */
    fun notin(right: Collection<Expression>): Expression {
        return MultRelaExpression(this, MultiRelaOperator.NOT_IN, right)
    }

    /**
     * NOT IN
     */
    fun notin(vararg right: Expression): Expression {
        return this.notin(*right)
    }

    /**
     * 加
     */
    fun plus(right: Expression): Expression {
        return ArithExpression(this, ArithOperator.PLUS, right)
    }

    /**
     * 减
     */
    fun sub(right: Expression): Expression {
        return ArithExpression(this, ArithOperator.SUB, right)
    }

    /**
     * 乘
     */
    fun mul(right: Expression): Expression {
        return ArithExpression(this, ArithOperator.MUL, right)
    }

    /**
     * 除
     */
    fun div(right: Expression): Expression {
        return ArithExpression(this, ArithOperator.DIV, right)
    }

    /**
     * 取余
     */
    fun rem(right: Expression): Expression {
        return ArithExpression(this, ArithOperator.REM, right)
    }
}

data class Field(
        val fieldName: String
) : Expression()

data class Fields(
        val fieldNames: Array<out String>
) : Expression() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Fields

        if (!Arrays.equals(fieldNames, other.fieldNames)) return false

        return true
    }

    override fun hashCode(): Int {
        return Arrays.hashCode(fieldNames)
    }
}

data class Value<TValue>(
        val value: TValue
) : Expression()

data class Values<TValue>(
        val values: Array<TValue>
) : Expression() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Values<*>

        if (!Arrays.equals(values, other.values)) return false

        return true
    }

    override fun hashCode(): Int {
        return Arrays.hashCode(values)
    }
}

/**
 * 算术表达式
 */
data class ArithExpression(
        val left: Expression,
        val operator: ArithOperator,
        val right: Expression
) : Expression() {
    init {
        Assertions.notNull("left", left)
        Assertions.notNull("operator", operator)
        Assertions.notNull("right", right)
    }

    override fun toString(): String {
        return "fieldName:${left}, value:${right}"
    }
}

/**
 * 关系表达式
 */
data class RelaExpression(
        val left: Expression,
        val operator: RelaOperator,
        val right: Expression
) : Expression() {
    init {
        Assertions.notNull("left", left)
        Assertions.notNull("operator", operator)
        Assertions.notNull("right", right)
    }

    override fun toString(): String {
        return "fieldName:${left}, value:${right}"
    }
}

/**
 * 多关系表达式
 */
data class MultRelaExpression(
        val left: Expression,
        val operator: MultiRelaOperator,
        val right: Collection<Expression>
) : Expression() {

    init {
        Assertions.notNull("left", left)
        Assertions.notNull("operator", operator)
        Assertions.notNullOrEmpty("right", right)
    }

    override fun toString(): String {
        return "fieldName:${left}, value:${right}"
    }
}

/**
 * 逻辑表达式
 */
data class LogicExpression(
        val operator: LogicOperator,
        val expressions: Collection<Expression>
) : Expression() {
    init {
        Assertions.notNull("logic", expressions)
        Assertions.notNullOrEmpty("expressions", expressions)
    }

    override fun toString(): String {
        return "logic expressions:${this.expressions}"
    }
}

/**
 * 排序表达式
 */
data class OrderExpression(
        val field: Field,
        val order: Order
)

/**
 * 排序方式
 */
enum class Order {
    ASC,
    DESC
}
package com.canyon.store

import com.canyon.core.Assertions
import java.util.*

abstract class Expression {
    fun eq(right: Expression): Expression {
        return RelaExpression(this, RelaOperator.EQ, right)
    }

    fun ne(right: Expression): Expression {
        return RelaExpression(this, RelaOperator.NE, right)
    }

    fun gt(right: Expression): Expression {
        return RelaExpression(this, RelaOperator.GT, right)
    }

    fun lt(right: Expression): Expression {
        return RelaExpression(this, RelaOperator.LT, right)
    }

    fun gte(right: Expression): Expression {
        return RelaExpression(this, RelaOperator.GTE, right)
    }

    fun lte(right: Expression): Expression {
        return RelaExpression(this, RelaOperator.LTE, right)
    }

    fun like(right: Expression): Expression {
        return RelaExpression(this, RelaOperator.LIKE, right)
    }

    fun in0(right: Collection<Expression>): Expression {
        return MultRelaExpression(this, MultiRelaOperator.IN, right)
    }

    fun in0(vararg right: Expression): Expression {
        return this.in0(*right)
    }

    fun notin(right: Collection<Expression>): Expression {
        return MultRelaExpression(this, MultiRelaOperator.NOT_IN, right)
    }

    fun notin(vararg right: Expression): Expression {
        return this.notin(*right)
    }

    fun plus(right: Expression): Expression {
        return ArithExpression(this, ArithOperator.PLUS, right)
    }

    fun sub(right: Expression): Expression {
        return ArithExpression(this, ArithOperator.SUB, right)
    }

    fun mul(right: Expression): Expression {
        return ArithExpression(this, ArithOperator.MUL, right)
    }

    fun div(right: Expression): Expression {
        return ArithExpression(this, ArithOperator.DIV, right)
    }

    fun mod(right: Expression): Expression {
        return ArithExpression(this, ArithOperator.MOD, right)
    }

    fun neg(right: Expression): Expression {
        return ArithExpression(this, ArithOperator.NEG, right)
    }
}

data class Field(
        val fieldName: String
) : Expression()

data class Fields(
        val fieldNames: Array<String>
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
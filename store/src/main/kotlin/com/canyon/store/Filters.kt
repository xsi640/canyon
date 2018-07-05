package com.canyon.store

import com.canyon.core.Assertions

object Filters {
    fun <TValue> eq(value: TValue): Expression = this.eq(StoreContant.ID, value)

    fun <TValue> eq(fieldName: String, value: TValue): Expression {
        return ExpressionUnit(fieldName, RelaOperator.EQ, value)
    }

    fun <TValue> ne(fieldName: String, value: TValue): Expression {
        return ExpressionUnit(fieldName, RelaOperator.NE, value)
    }

    fun <TValue> gt(fieldName: String, value: TValue): Expression {
        return ExpressionUnit(fieldName, RelaOperator.GT, value)
    }

    fun <TValue> lt(fieldName: String, value: TValue): Expression {
        return ExpressionUnit(fieldName, RelaOperator.LT, value)
    }

    fun <TValue> gte(fieldName: String, value: TValue): Expression {
        return ExpressionUnit(fieldName, RelaOperator.GTE, value)
    }

    fun <TValue> lte(fieldName: String, value: TValue): Expression {
        return ExpressionUnit(fieldName, RelaOperator.LTE, value)
    }

    fun <TValue> like(fieldName: String, value: TValue): Expression {
        return ExpressionUnit(fieldName, RelaOperator.LIKE, value)
    }

    fun and(expressions: Iterable<Expression>): Expression {
        return ExpressionLogic(LogicOperator.AND, expressions)
    }

    fun and(vararg values: Expression): Expression {
        return this.and(listOf(*values))
    }

    fun or(expressions: Iterable<Expression>): Expression {
        return ExpressionLogic(LogicOperator.OR, expressions)
    }

    fun or(vararg values: Expression): Expression {
        return this.or(listOf(*values))
    }
}

abstract class Expression

data class ExpressionUnit<T>(
        val fieldName: String,
        val operator: RelaOperator,
        val value: T
) : Expression() {
    init {
        Assertions.notNullOrEmpty("fieldName", fieldName)
        Assertions.notNull("operator", operator)
        Assertions.notNull("value", value)
    }

    override fun toString(): String {
        return "fieldName:${fieldName}, value:${value}"
    }
}

data class ExpressionIterableUnit<T>(
        val fieldName: String,
        val operator: RelaOperator,
        val values: Iterable<T>
) : Expression() {

    init {
        Assertions.notNullOrEmpty("fieldName", fieldName)
        Assertions.notNull("operator", operator)
        Assertions.notNull("value", values)
    }

    override fun toString(): String {
        return "fieldName:${fieldName}, value:${values}"
    }
}

data class ExpressionLogic(
        val operator: LogicOperator,
        val expressions: Iterable<Expression>
) : Expression() {
    init {
        Assertions.notNull("logic", expressions)
    }

    override fun toString(): String {
        return "logic expressions:${this.expressions}"
    }
}

enum class RelaOperator {
    /**
     * 相等
     */
    EQ,
    /**
     * 不相等
     */
    NE,
    /**
     * 大于
     */
    GT,
    /**
     * 小于
     */
    LT,
    /**
     * 大于等于
     */
    GTE,
    /**
     * 小于等于
     */
    LTE,
    LIKE,
}

enum class LogicOperator {
    AND,
    OR,
    IN,
    NOT_IN
}

enum class ArithOperator {
    PLUS,
    SUB,
    MUL,
    DIV,
    MOD,
    NEG,
}
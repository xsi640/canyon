package com.canyon.store

import com.canyon.core.Assertions
import java.util.*

abstract class Expression

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
data class ArithExpression<T>(
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
 * 关系表达式
 */
data class RelaExpression<T>(
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
        val operator: RelaOperator,
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
 * 关系操作
 */
enum class RelaOperator {
    EQ,
    NE,
    GT,
    LT,
    GTE,
    LTE,
    LIKE,
}

/**
 * 多关系表达式
 */
enum class MultiRelaOperator {
    IN,
    NOT_IN
}

/**
 * 逻辑操作
 */
enum class LogicOperator {
    AND,
    OR,
}

/**
 * 算术操作
 */
enum class ArithOperator {
    PLUS,
    SUB,
    MUL,
    DIV,
    MOD,
    NEG,
}
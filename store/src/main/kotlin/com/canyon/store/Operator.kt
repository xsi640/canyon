package com.canyon.store


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
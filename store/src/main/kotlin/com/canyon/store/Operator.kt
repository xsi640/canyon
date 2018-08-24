package com.canyon.store


/**
 * 关系操作
 */
enum class RelaOperator {
    /**
     * 等于
     */
    EQ,
    /**
     * 不等
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
    /**
     * like
     */
    LIKE,
}

/**
 * 多关系表达式
 */
enum class MultiRelaOperator {
    /**
     * in
     */
    IN,
    /**
     * not in
     */
    NOT_IN
}

/**
 * 逻辑操作
 */
enum class LogicOperator {
    /**
     * and
     */
    AND,
    /**
     * or
     */
    OR,
}

/**
 * 算术操作
 */
enum class ArithOperator {
    /**
     * 加
     */
    PLUS,
    /**
     * 减
     */
    SUB,
    /**
     * 乘
     */
    MUL,
    /**
     * 除
     */
    DIV,
    /**
     * 取余
     */
    REM
}
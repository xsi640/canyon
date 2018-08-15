package com.canyon.store

/**
 * 表示查询数据库所需的命令
 * @param sql SQL命令
 * @param parameters SQL参数
 */
data class SqlCommand(
        val sql: String,
        val parameters: List<Any?>
)

/**
 * 数据库命令执行器
 */
interface DatabaseExecutor {
    fun <T> query(sql: String, parameters: List<Any>, clazz: Class<T>): List<T>
    fun <T> queryOne(sql: String, parameters: List<Any>, clazz: Class<T>): T
    fun update(sql: String, parameters: List<Any>): Int
    fun execute(sql: String)
}
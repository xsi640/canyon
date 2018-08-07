package com.canyon.commons.engines

/**
 * 循环引擎
 */
interface CircleEngine {

    /**
     * 是否已经开始
     * @return
     */
    val isStart: Boolean

    /**
     * 检查时间（单位：秒）
     * @return
     */
    /**
     * 设置检查时间（单位：秒）
     * @param detectSpanInSecs
     */
    var detectSpanInSecs: Int

    /**
     * 获取最后异常
     * @return
     */
    val lastException: Exception?

    /**
     * 开始
     */
    fun start()

    /**
     * 停止
     */
    fun stop()
}

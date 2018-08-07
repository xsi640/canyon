package com.canyon.commons

import java.util.concurrent.Semaphore
import java.util.concurrent.TimeUnit

/**
 * 通知正在等待的线程已发生事件。 此类不能被继承。
 */
class AutoResetEvent(
        signalled: Boolean
) {
    private val event: Semaphore = Semaphore(if (signalled) 1 else 0)
    var tag: Any? = null

    /**
     * 将事件状态设置为有信号，从而允许一个或多个等待线程继续执行。
     */
    @Synchronized
    fun set() {
        if (event.availablePermits() == 0) {
            event.release()
        }
    }

    /**
     * 将事件状态设置为非终止，从而导致线程受阻。
     */
    fun reset() {
        event.drainPermits()
    }

    /**
     * 阻止当前线程，直到当前 WaitHandle 收到信号。
     */
    fun waitOne() {
        event.acquire()
    }

    /**
     * 阻止当前线程，直到当前 WaitHandle 收到信号。
     * @param timeout 表示等待的时间
     * @param unit 表示等待的时间单位
     * @return
     */
    fun waitOne(timeout: Int, unit: TimeUnit): Boolean {
        return event.tryAcquire(timeout.toLong(), unit)
    }

    /**
     * 阻止当前线程，直到当前 WaitHandle 收到信号。
     * @param timeout 表示等待的时间（单位：毫秒）
     * @return
     */
    fun waitOne(timeout: Int): Boolean {
        return waitOne(timeout, TimeUnit.MILLISECONDS)
    }

    /**
     * 是否有等待的信号
     * @return
     */
    fun isSignalled(): Boolean {
        return event.availablePermits() > 0
    }
}
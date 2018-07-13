package com.canyon.commons

import java.util.concurrent.Callable
import java.util.concurrent.FutureTask

object ThreadUtils {

    fun run(runnable: Runnable, milliseconds: Long) {
        run(runnable, null, milliseconds)
    }

    fun <T> run(future: FutureTask<T>?) {
        if (future == null)
            return
        val thread = Thread(future)
        thread.start()
    }

    fun <T> run(future: FutureTask<T>, milliseconds: Long) {
        val thread = Thread(Runnable {
            try {
                Thread.sleep(milliseconds)
                future.run()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        })
        thread.start()
    }

    fun <T> run(callable: Callable<T>, ref: Ref<T>) {
        ThreadUtils.run(Runnable {
            try {
                ref.value = callable.call()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })
    }

    fun <T> run(callable: Callable<T>, ref: Ref<T>, milliseconds: Long) {
        ThreadUtils.run(Runnable {
            try {
                Thread.sleep(milliseconds)
                ref.value = callable.call()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })
    }

    fun <T> run(callable: Callable<T>, callback: (t: T) -> Unit, milliseconds: Long) {
        Thread(Runnable {
            try {
                if (milliseconds > 0)
                    Thread.sleep(milliseconds)
                val t = callable.call()
                callback(t)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }).start()
    }

    @JvmOverloads
    fun run(runnable: Runnable, callback: Runnable? = null, milliseconds: Long = 0) {
        Thread(Runnable {
            if (milliseconds > 0) {
                try {
                    Thread.sleep(milliseconds)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

            }
            runnable.run()
            callback?.run()
        }).start()
    }
}

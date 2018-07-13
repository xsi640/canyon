package com.canyon.commons

import java.io.IOException
import java.nio.charset.Charset

object ProcessUtils {

    private val DEFAULT_CHARSET = Charset.forName("UTF-8")

    /**
     * 开始一个进程
     * @param command
     */
    @JvmStatic
    fun start(command: Array<String>) {
        val pb = ProcessBuilder(*command)
        try {
            pb.start()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    /**
     * 开始一个进程，并且等待进程结束
     * @param command
     * @param waitFor
     */
    fun start(command: Array<String>, waitFor: Boolean) {
        var p: Process? = null
        try {
            val pb = ProcessBuilder(*command)
            p = pb.start()
            if (waitFor) {
                p!!.waitFor()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } finally {
            p?.destroy()
        }
    }

    /**
     * 开始一个进程，等待进程结束后，返回结果
     * @param command
     * @param charset
     * @return
     */
    @JvmOverloads
    fun startWithResult(command: Array<String>, charset: Charset = DEFAULT_CHARSET): String {
        val pb = ProcessBuilder(*command)
        val process = pb.start()
        process.waitFor()
        process.inputStream.bufferedReader(charset).use { br ->
            return br.readText()
        }
        process.destroy()
    }
}

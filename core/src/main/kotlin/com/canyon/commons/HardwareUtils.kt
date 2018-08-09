package com.canyon.commons

import java.net.InetAddress

object HardwareUtils {
    /**
     * 获取当前程序的PID
     */
    fun getPID(): Long {
        val processName = java.lang.management.ManagementFactory.getRuntimeMXBean().name
        if (processName != null && processName.isNotEmpty()) {
            return try {
                java.lang.Long.parseLong(processName.split("@")[0])
            } catch (e: Exception) {
                0
            }

        }
        return 0
    }

    /**
     * 获取当前计算机名称
     */
    fun getComputerName(): String {
        return InetAddress.getLocalHost().hostName
    }
}
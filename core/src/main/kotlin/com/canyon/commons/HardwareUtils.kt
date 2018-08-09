package com.canyon.commons

import java.net.InetAddress

object HardwareUtils {
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

    fun getComputerName(): String {
        return InetAddress.getLocalHost().hostName
    }
}
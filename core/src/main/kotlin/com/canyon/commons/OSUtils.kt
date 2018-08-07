package com.canyon.commons

import org.apache.commons.lang3.SystemUtils

object OSUtils {
    val OS_NAME = System.getProperty("os.name")
    val PROCESSOR_BIT = System.getProperty("os.arch")
    val JAVA_VENDOR_NAME = System.getProperty("java.vendor")
    val IBM_JAVA = JAVA_VENDOR_NAME.contains("IBM")

    /**
     * 操作系统是否是64bit
     * @return
     */
    val is64Bit: Boolean
        get() = OSUtils.PROCESSOR_BIT.contains("64")

    /**
     * 是否是Windows系统
     * @return
     */
    val isWindows: Boolean
        get() = SystemUtils.IS_OS_WINDOWS

    /**
     * 是否是Mac系统
     * @return
     */
    val isMacOS: Boolean
        get() = SystemUtils.IS_OS_MAC_OSX

    /**
     * 是否是Linux系统
     * @return
     */
    val isLinux: Boolean
        get() = SystemUtils.IS_OS_LINUX

    /**
     * 是否是AIX系统
     * @return
     */
    val isAIX: Boolean
        get() = OSUtils.OS_NAME == "AIX"
}

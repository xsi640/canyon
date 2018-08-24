package com.canyon.commons;

import org.apache.commons.lang3.SystemUtils;

public class OSUtils {
    public static final String OS_NAME = System.getProperty("os.name");
    public static final String PROCESSOR_BIT = System.getProperty("os.arch");
    public static final String JAVA_VENDOR_NAME = System.getProperty("java.vendor");
    public static final boolean IBM_JAVA = JAVA_VENDOR_NAME.contains("IBM");

    /**
     * 操作系统是否是64bit
     * @return
     */
    public static boolean is64Bit() {
        return OSUtils.PROCESSOR_BIT.contains("64");
    }

    /**
     * 是否是Windows系统
     * @return
     */
    public static boolean isWindows() {
        return  SystemUtils.IS_OS_WINDOWS;
    }

    /**
     * 是否是Mac系统
     * @return
     */
    public static boolean isMacOS() {
        return SystemUtils.IS_OS_MAC_OSX;
    }

    /**
     * 是否是Linux系统
     * @return
     */
    public static boolean isLinux() {
        return SystemUtils.IS_OS_LINUX;
    }

    /**
     * 是否是AIX系统
     * @return
     */
    public static boolean isAIX() {
        return OSUtils.OS_NAME.equals("AIX");
    }
}

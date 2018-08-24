package com.canyon.commons;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class HardwareUtils {
    public static long getPID() {
        String processName = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
        if (StringUtils.isNotEmpty(processName)) {
            return Long.parseLong(processName.split("@")[0]);
        }
        return 0L;
    }

    public static String getComputerName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "";
        }
    }
}

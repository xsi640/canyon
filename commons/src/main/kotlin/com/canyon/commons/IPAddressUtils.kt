package com.canyon.commons

import java.net.InetAddress
import kotlin.experimental.and

object IPAddressUtils {
    fun validIPAddress(strIPAddress: String): Boolean {
        if (strIPAddress.isEmpty()) {
            return false
        }

        val parts = strIPAddress.split(".")
        if (parts.size != 4) {
            return false
        }

        for (s in parts) {
            val i = Integer.parseInt(s)
            if (i < 0 || i > 255) {
                return false
            }
        }
        return !strIPAddress.endsWith(".")
    }

    fun toIPAddress(strIPAddress: String): InetAddress {
        return InetAddress.getByName(strIPAddress)
    }

    fun toLong(strIPAddress: String): Long {
        var result = 0L
        val ip = toIPAddress(strIPAddress)
        if (ip != null) {
            result = toLong(ip)
        }
        return result
    }

    fun toLong(ip: InetAddress): Long {
        var result = 0L
        val octets = ip.address
        for (octet in octets) {
            result = result shl 8
            result = result or (octet and 0xff.toByte()).toLong()
        }
        return result
    }

    fun contains(startIP: String, endIP: String, ip: String): Boolean {
        val ipStart = toLong(startIP)
        val ipEnd = toLong(endIP)
        val ipTest = toLong(ip)

        return ipTest in ipStart..ipEnd
    }
}

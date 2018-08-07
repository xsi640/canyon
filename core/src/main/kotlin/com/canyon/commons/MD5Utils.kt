package com.canyon.commons

import java.io.File
import java.io.FileInputStream

object MD5Utils {
    fun getMD5fromFile(path: String): String {
        var file = File(path)
        if (file.isFile && file.canRead()) {
            FileInputStream(path).use { fos ->
                return org.apache.commons.codec.digest.DigestUtils.md5Hex(fos)
            }
        }
        throw IllegalArgumentException("The file can't read or not exists. path:$path")
    }

    fun getMD5fromString(text: String): String {
        return org.apache.commons.codec.digest.DigestUtils.md5Hex(text)
    }
}

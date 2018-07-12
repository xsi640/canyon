package com.canyon.commons

import java.nio.charset.Charset
import java.util.*

/**
 * Base64编码工具类
 *
 * @author SuYang
 */
object Base64Utils {

    private val DEFAULT_CHARSET = Charset.forName("UTF-8")

    /**
     * 将Base64字符串转换为指定的字符串
     *
     * @param s
     * @param charset
     * @return
     */
    @JvmOverloads
    fun fromBase64(s: String, charset: Charset = DEFAULT_CHARSET): String {
        return if (s.isEmpty()) "" else String(fromBase64ByteArray(s.toByteArray(charset))!!, charset)
    }

    /**
     * 将Base64二进制数组转换为指定的二进制数组
     * @param byteArrays
     * @return
     */
    fun fromBase64ByteArray(byteArrays: ByteArray): ByteArray {
        if (byteArrays.isEmpty())
            return byteArrayOf()
        return Base64.getDecoder().decode(byteArrays)
    }

    /**
     * 将指定的字符串转为Base64字符串
     *
     * @param s
     * @param charset
     * @return
     */
    @JvmOverloads
    fun toBase64(s: String, charset: Charset = DEFAULT_CHARSET): String {
        return if (s.isEmpty()) "" else toBase64(s.toByteArray(charset), charset)
    }

    /**
     * 将指定的字符串转为Base64字符串
     * @param data
     * @return
     */
    fun toBase64(data: ByteArray): String {
        return if (data.isEmpty()) "" else toBase64(data, DEFAULT_CHARSET)
    }

    /**
     * 将指定的字符串转为Base64字符串
     *
     * @param data
     * @param charset
     * @return
     */
    fun toBase64(data: ByteArray, charset: Charset): String {
        return if (data.isEmpty()) "" else String(toBase64ByteArray(data)!!, charset)
    }

    /**
     * 将指定的二进制数组转为Base64二进制数组
     * @param byteArrays
     * @return
     */
    fun toBase64ByteArray(byteArrays: ByteArray): ByteArray {
        if (byteArrays.isEmpty())
            return byteArrayOf()
        return Base64.getEncoder().encode(byteArrays)
    }
}
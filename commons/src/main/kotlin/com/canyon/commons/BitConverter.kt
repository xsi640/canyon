package com.canyon.commons

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.charset.Charset

object BitConverter {

    private val DEFAULT_CHARSET = Charset.forName("UTF-8")

    /**
     * boolean 转换成 byte
     * @param value
     * @return
     */
    fun getBytes(value: Boolean): Byte {
        return (if (value) 1 else 0).toByte()
    }

    /**
     * byte 转换成 boolean
     * @param b
     * @return
     */
    fun toBoolean(b: Byte): Boolean {
        return b.toInt() == 1
    }

    /**
     * short 转换成 byte[]
     * @param value
     * @return
     */
    fun getBytes(value: Short): ByteArray {
        return ByteBuffer.allocate(java.lang.Short.BYTES).putShort(value).array()
    }

    /**
     * byte[] 转换成 short
     * @param bytes
     * @return
     */
    fun toShort(bytes: ByteArray): Short {
        return ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).short
    }

    /**
     * int 转换成 byte[]
     * @param value
     * @return
     */
    fun getBytes(value: Int): ByteArray {
        return ByteBuffer.allocate(java.lang.Integer.BYTES).putInt(value).array()
    }

    /**
     * byte[] 转换成 int
     * @param bytes
     * @return
     */
    fun toInt(bytes: ByteArray): Int {
        return ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).int
    }

    /**
     * long 转换成 byte[]
     * @param value
     * @return
     */
    fun getBytes(value: Long): ByteArray {
        return ByteBuffer.allocate(java.lang.Long.BYTES).putLong(value).array()
    }

    /**
     * byte[] 转换成 long
     * @param bytes
     * @return
     */
    fun toLong(bytes: ByteArray): Long {
        return ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).long
    }

    /**
     * float 转换成 byte[]
     * @param value
     * @return
     */
    fun getBytes(value: Float): ByteArray {
        return ByteBuffer.allocate(java.lang.Float.BYTES).putFloat(value).array()
    }

    /**
     * byte[] 转换成 float
     * @param bytes
     * @return
     */
    fun toFloat(bytes: ByteArray): Float {
        return ByteBuffer.wrap(bytes).float
    }

    /**
     * double 转换成 byte[]
     * @param value
     * @return
     */
    fun getBytes(value: Double): ByteArray {
        return ByteBuffer.allocate(java.lang.Double.BYTES).putDouble(value).array()
    }

    /**
     * byte[] 转换成 double
     * @param bytes
     * @return
     */
    fun toDouble(bytes: ByteArray): Double {
        return ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).double
    }

    /**
     * char 转成 byte[]
     * @param c
     * @return
     */
    fun getBytes(c: Char): ByteArray {
        return ByteBuffer.allocate(2).putChar(c).array()
    }

    /**
     * byte[] 转换成 char
     * @param bytes
     * @return
     */
    fun toChar(bytes: ByteArray): Char {
        return ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).char
    }

    /**
     * char[] 转换成 byte[]
     * @param cArray
     * @return
     */
    fun getBytes(cArray: CharArray): ByteArray {
        return getBytes(String(cArray), DEFAULT_CHARSET)
    }

    /**
     * char[] 转换成 byte[]
     * @param cArray
     * @param charset
     * @return
     */
    fun getBytes(cArray: CharArray, charset: Charset): ByteArray {
        return getBytes(String(cArray), charset)
    }

    /**
     * byte[] 转换成 char[]
     * @param bytes
     * @return
     */
    fun toCharArray(bytes: ByteArray): CharArray {
        return toString(bytes, DEFAULT_CHARSET).toCharArray()
    }

    /**
     * byte[] 转换成 char[]
     * @param bytes
     * @param charset
     * @return
     */
    fun toCharArray(bytes: ByteArray, charset: Charset): CharArray {
        return toString(bytes, charset).toCharArray()
    }

    /**
     * string 转换成 byte[]
     * @param s
     * @param charset
     * @return
     */
    @JvmOverloads
    fun getBytes(s: String, charset: Charset = DEFAULT_CHARSET): ByteArray {
        return s.toByteArray(charset)
    }

    /**
     * byte[] 转换成 string
     * @param bytes
     * @param charset
     * @return
     */
    @JvmOverloads
    fun toString(bytes: ByteArray, charset: Charset = DEFAULT_CHARSET): String {
        return String(bytes, charset)
    }
}
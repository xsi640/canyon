package com.canyon.commons

import java.nio.ByteBuffer
import java.nio.ByteOrder

object CompatibleUtils {
    fun csharpByteArrayToShort(byteArray: ByteArray): Short {
        return ByteBuffer.wrap(byteArray).order(ByteOrder.BIG_ENDIAN).short
    }

    fun csharpByteArrayToInt(byteArray: ByteArray): Int {
        return ByteBuffer.wrap(byteArray).order(ByteOrder.BIG_ENDIAN).int
    }

    fun csharpByteArrayToLong(byteArray: ByteArray): Long {
        return ByteBuffer.wrap(byteArray).order(ByteOrder.BIG_ENDIAN).long
    }

    fun csharpByteArrayToFloat(byteArray: ByteArray): Float {
        return ByteBuffer.wrap(byteArray).order(ByteOrder.BIG_ENDIAN).float
    }

    fun csharpByteArrayToDouble(byteArray: ByteArray): Double {
        return ByteBuffer.wrap(byteArray).order(ByteOrder.BIG_ENDIAN).double
    }

    fun csharpByteArrayToChar(byteArray: ByteArray): Char {
        return ByteBuffer.wrap(byteArray).order(ByteOrder.BIG_ENDIAN).char
    }
}
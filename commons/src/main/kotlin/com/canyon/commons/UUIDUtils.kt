package com.canyon.commons

import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.util.*
import kotlin.experimental.and

object UUIDUtils {

    val UUID_EMPTY = UUID(0, 0)

    fun fromStringWithHyphens(str: String): UUID {
        return UUID.fromString(str)
    }

    fun fromStringWhitoutHyphens(str: String): UUID {
        if (str.length != 32) {
            throw IllegalArgumentException("Invalid UUID string: $str")
        }
        val s1 = "0x" + str.substring(0, 8)
        val s2 = "0x" + str.substring(9, 12)
        val s3 = "0x" + str.substring(13, 16)
        val s4 = "0x" + str.substring(17, 20)
        val s5 = "0x" + str.substring(21, 32)

        var mostSigBits = java.lang.Long.decode(s1).toLong()
        mostSigBits = mostSigBits shl 16
        mostSigBits = mostSigBits or java.lang.Long.decode(s2).toLong()
        mostSigBits = mostSigBits shl 16
        mostSigBits = mostSigBits or java.lang.Long.decode(s3).toLong()

        var leastSigBits = java.lang.Long.decode(s4).toLong()
        leastSigBits = leastSigBits shl 48
        leastSigBits = leastSigBits or java.lang.Long.decode(s5).toLong()

        return UUID(mostSigBits, leastSigBits)
    }

    fun fromString(str: String): UUID {
        return if (str.indexOf('-') > 0) {
            fromStringWithHyphens(str)
        } else {
            fromStringWhitoutHyphens(str)
        }
    }

    fun toString(uuid: UUID): String {
        return uuid.toString()
    }

    fun toStringWhitoutHyphens(uuid: UUID): String {
        val msb = uuid.mostSignificantBits
        val lsb = uuid.leastSignificantBits
        return (digits(msb shr 32, 8) + digits(msb shr 16, 4) + digits(msb, 4)
                + digits(lsb shr 48, 4) + digits(lsb, 12))
    }

    private fun digits(`val`: Long, digits: Int): String {
        val hi = 1L shl digits * 4
        return java.lang.Long.toHexString(hi or (`val` and hi - 1)).substring(1)
    }

    fun fromByte(data: ByteArray): UUID {
        if (data.size != 16) {
            throw IllegalArgumentException("Invalid UUID byte[]")
        }
        var byteBuffer = ByteBuffer.wrap(data)
        var high = byteBuffer.long
        var low = byteBuffer.long
        return UUID(high, low)
    }

    fun toByte(uuid: UUID): ByteArray {
        val ba = ByteArrayOutputStream(16)
        val da = DataOutputStream(ba)
        try {
            da.writeLong(uuid.mostSignificantBits)
            da.writeLong(uuid.leastSignificantBits)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return ba.toByteArray()
    }
}

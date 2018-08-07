package com.canyon.commons

import java.nio.charset.Charset
import java.security.SecureRandom
import javax.crypto.*
import javax.crypto.spec.DESKeySpec

object EncryptUtils {
    private val strKey = "c8cdf47595f94959850d49a703682e8f"
    private var ecipher: Cipher
    private var dcipher: Cipher

    init {
        val sr = SecureRandom()
        val dks = DESKeySpec(strKey.toByteArray())
        val keyFactory = SecretKeyFactory.getInstance("DES")
        val key = keyFactory.generateSecret(dks)
        ecipher = Cipher.getInstance("DES")
        dcipher = Cipher.getInstance("DES")
        ecipher.init(Cipher.ENCRYPT_MODE, key, sr)
        dcipher.init(Cipher.DECRYPT_MODE, key, sr)
    }

    fun encryptToByteArray(data: ByteArray): ByteArray {
        return ecipher.doFinal(data)
    }

    fun decryptFromByteArray(data: ByteArray): ByteArray {
        return dcipher.doFinal(data)
    }

    fun encrypt(text: String): String {
        var result = ""
        var data = EncodingUtils.decode(text, Charset.forName("UTF-8"))
        if (data.isNotEmpty()) {
            data = encryptToByteArray(data)
            result = EncodingUtils.base64Encode(data)
        }
        return result
    }

    fun decrypt(text: String): String {
        var result = ""
        var data = EncodingUtils.base64Decode(text)
        if (data.isNotEmpty()) {
            data = decryptFromByteArray(data)
            result = EncodingUtils.encode(data, Charset.forName("UTF-8"))
        }
        return result
    }
}

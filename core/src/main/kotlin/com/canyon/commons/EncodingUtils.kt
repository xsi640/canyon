package com.canyon.commons

import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.Charset
import java.util.*


object EncodingUtils {
    val DEFAULT_CHARSET_STR = "UTF-8"
    val DEFAULT_CHARSET = Charset.forName(DEFAULT_CHARSET_STR)

    @JvmOverloads
    fun encode(data: ByteArray, charset: Charset = DEFAULT_CHARSET): String {
        return String(data, charset)
    }

    @JvmOverloads
    fun decode(text: String, charset: Charset = DEFAULT_CHARSET): ByteArray {
        return text.toByteArray(charset)
    }

    fun base64Encode(data: ByteArray): String {
        return Base64.getEncoder().encodeToString(data)
    }

    fun base64Decode(data: String): ByteArray {
        return Base64.getDecoder().decode(data)
    }

    fun urlEncode(url: String): String {
        var result = ""
        try {
            result = URLEncoder.encode(url, DEFAULT_CHARSET_STR).replace("\\+", "%20")
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

        return result
    }

    fun urlDecode(url: String): String {
        var result = ""
        try {
            result = URLDecoder.decode(url.replace("%20", "\\+"), DEFAULT_CHARSET_STR)
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

        return result
    }
}

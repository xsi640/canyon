package com.canyon.commons

import java.util.regex.Pattern

object RegexUtils {
    private val FLAGS = Pattern.CASE_INSENSITIVE or Pattern.MULTILINE

    val HTMLCOLOR = "^#?([a-f]|[A-F]|[0-9]){3}(([a-f]|[A-F]|[0-9]){3})?$"
    val NUMBER = "^[0-9]+$"
    val ENGLISH = "^[A-Za-z0-9]+$"
    val EMAIL = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$"
    val IP = "^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$"
    val URL = "^(http|https|ftp|rtsp|mms):(\\/\\/|\\\\\\\\)[A-Za-z0-9%\\-_@]+\\.[A-Za-z0-9%\\-_@]+[A-Za-z0-9\\.\\/=\\?%\\-&_~`@:\\+!;]*$"
    val CHINESE = "^[\\u4e00-\\u9fa5]{2,}$"
    val URLPORT = "^(.*)://([0-9,a-z,A-Z,-,.]+):([0-9]+)?"
    val DOMAIN = "([0-9A-Za-z]{2,}\\.[0-9A-Za-z]{2,3}|[0-9A-Za-z]{2,}\\.[0-9A-Za-z]{2,3})$"

    val HTMLCOLOR_PATTERN = Pattern.compile(HTMLCOLOR, FLAGS)
    val NUMBER_PATTERN = Pattern.compile(NUMBER, FLAGS)
    val ENGLISH_PATTERN = Pattern.compile(ENGLISH, FLAGS)
    val EMAIL_PATTERN = Pattern.compile(EMAIL, FLAGS)
    val IP_PATTERN = Pattern.compile(IP, FLAGS)
    val URL_PATTERN = Pattern.compile(URL, FLAGS)
    val CHINESE_PATTERN = Pattern.compile(CHINESE, FLAGS)
    val URLPORT_PATTERN = Pattern.compile(URLPORT, FLAGS)
    val DOMAIN_PATTERN = Pattern.compile(DOMAIN, FLAGS)

    fun isHtmlColor(str: String?): Boolean {
        if (str == null || str.isEmpty())
            return false

        val m = HTMLCOLOR_PATTERN.matcher(str)
        return m.find()
    }

    fun isNumber(str: String?): Boolean {
        if (str == null || str.isEmpty())
            return false

        val m = NUMBER_PATTERN.matcher(str)
        return m.find()
    }

    fun isEnglish(str: String?): Boolean {
        if (str == null || str.isEmpty())
            return false

        val m = ENGLISH_PATTERN.matcher(str)
        return m.find()
    }

    fun isEmail(str: String?): Boolean {
        if (str == null || str.isEmpty())
            return false

        val m = EMAIL_PATTERN.matcher(str)
        return m.find()
    }

    fun isIP(str: String?): Boolean {
        if (str == null || str.isEmpty())
            return false

        val m = IP_PATTERN.matcher(str)
        return m.find()
    }

    fun isUrl(str: String?): Boolean {
        if (str == null || str.isEmpty())
            return false

        val m = URL_PATTERN.matcher(str)
        return m.find()
    }

    fun isChinese(str: String?): Boolean {
        if (str == null || str.isEmpty())
            return false

        val m = CHINESE_PATTERN.matcher(str)
        return m.find()
    }

    fun isDomain(str: String?): Boolean {
        if (str == null || str.isEmpty())
            return false

        val m = DOMAIN_PATTERN.matcher(str)
        return m.find()
    }

    fun getUrlProctocol(url: String, proc: Ref<String>, ipAddress: Ref<String>, port: Ref<Int>) {
        proc.value = ""
        ipAddress.value = ""
        port.value = 0

        val m = URLPORT_PATTERN.matcher(url)
        if (m.find()) {
            proc.value = m.group(1).toString()
            ipAddress.value = m.group(2).toString()
            port.value = m.group(3).toInt()
        }
    }
}

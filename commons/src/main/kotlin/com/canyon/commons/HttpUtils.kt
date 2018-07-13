package com.canyon.commons

import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.*


class HttpUtils {

    enum class Method {
        GET, POST
    }

    internal inner class UploadFileItem {
        var fileName: String? = null
        var filePath: String? = null
        var data: ByteArray? = null
        var size: Long = 0
    }

    companion object {

        val RESPONSE_STREAM_MAX_LENGTH = 3850
        val MAX_TIME_OUT = 30000
        val DEFAULT_CONTENT_TYPE = "application/x-www-form-urlencoded"
        private val DEFAULT_USER_AGENT = "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.2; SV1; .NET CLR 1.1.4322; .NET CLR 2.0.50727)"

        fun download(url: String, nvc: NameValueCollection, method: String, path: String) {
            var m = Method.GET
            if ("GET".equals(method, ignoreCase = true)) {
                m = Method.GET
            } else if ("POST".equals(method, ignoreCase = true)) {
                m = Method.POST
            } else {
                throw IllegalArgumentException("Http Method Failured.")
            }

            var conn: HttpURLConnection? = null
            var inputStream: InputStream? = null
            var bufferedOutputStream: BufferedOutputStream? = null

            try {
                conn = getConnection(url, nvc, m)
                inputStream = getInputStream(conn, nvc, m)
                bufferedOutputStream = BufferedOutputStream(FileOutputStream(path))
                val buffer = ByteArray(RESPONSE_STREAM_MAX_LENGTH)
                var read: Int = -1
                while ({ read = inputStream.read(buffer);read }() != -1) {
                    bufferedOutputStream.write(buffer, 0, read)
                }
                bufferedOutputStream.flush()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                if (bufferedOutputStream != null) {
                    try {
                        bufferedOutputStream.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
                if (inputStream != null) {
                    try {
                        inputStream.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
                conn?.disconnect()
            }
        }

        fun getContent(url: String, nvc: NameValueCollection, method: String): String {
            var m = Method.GET
            m = when {
                "GET".equals(method, ignoreCase = true) -> Method.GET
                "POST".equals(method, ignoreCase = true) -> Method.POST
                else -> throw IllegalArgumentException("Http Method Failured.")
            }

            var conn: HttpURLConnection? = null
            var inputStream: InputStream? = null
            var bufferedReader: BufferedReader? = null
            var result = ""

            try {
                conn = getConnection(url, nvc, m)
                inputStream = getInputStream(conn, nvc, m)
                bufferedReader = BufferedReader(InputStreamReader(inputStream))
                val sb = StringBuffer()
                var line: String? = null
                while ({ line = bufferedReader.readLine();line }() != null) {
                    sb.append(line)
                    sb.append(System.lineSeparator())
                }
                result = sb.toString()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
                if (inputStream != null) {
                    try {
                        inputStream.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
                conn?.disconnect()
            }
            return result
        }

        private fun getParams(nvc: NameValueCollection?): String {
            if (nvc != null && nvc.size() > 0) {
                val sb = StringBuffer()
                for (key in nvc.keySet()) {
                    sb.append(EncodingUtils.urlEncode(key))
                    sb.append("=")
                    sb.append(EncodingUtils.urlEncode(nvc[key]))
                    sb.append("&")
                }
                return sb.substring(0, sb.length - 1)
            }
            return ""
        }

        private fun getUrl(url: String, nvc: NameValueCollection?): String {
            if (nvc != null && nvc.size() > 0) {
                val sb = StringBuffer(url)
                sb.append("?")
                for (key in nvc.keySet()) {
                    sb.append(EncodingUtils.urlEncode(key))
                    sb.append("=")
                    sb.append(EncodingUtils.urlEncode(nvc[key]))
                    sb.append("&")
                }
                return sb.substring(0, sb.length - 1)
            }
            return url
        }

        @Throws(IOException::class)
        fun getConnection(url: String, nvc: NameValueCollection, method: Method): HttpURLConnection {
            var result: HttpURLConnection? = null
            if (method == Method.POST) {
                val u = URL(url)
                if (u.protocol.equals("https", ignoreCase = true)) {
                    trustHttpsEveryone()
                }
                result = u.openConnection() as HttpURLConnection
                result.requestMethod = "POST"
            } else {
                val u = URL(getUrl(url, nvc))
                result = u.openConnection() as HttpURLConnection
                result.requestMethod = "GET"
            }
            result.setRequestProperty("Content-Type", DEFAULT_CONTENT_TYPE)
            result.setRequestProperty("User-Agent", DEFAULT_USER_AGENT)
            result.readTimeout = MAX_TIME_OUT
            return result
        }

        @Throws(IOException::class)
        fun getInputStream(conn: HttpURLConnection?, nvc: NameValueCollection, method: Method): InputStream {
            if (method == Method.POST) {
                conn!!.doInput = true
                conn.doOutput = true
                val param = getParams(nvc)
                if (!param.isEmpty()) {
                    val data = EncodingUtils.decode(param)
                    conn.setRequestProperty("Content-Lenth", data.size.toString())
                    val outputStream = conn.outputStream as OutputStream
                    outputStream.write(data)
                    outputStream.flush()
                    outputStream.close()
                }
            }
            return conn!!.inputStream
        }

        @Throws(IOException::class)
        fun getRedirectUrl(url: String): String {
            HttpURLConnection.setFollowRedirects(false)
            val u = URL(url)
            val conn = u.openConnection() as HttpURLConnection
            if (u.protocol.equals("https", ignoreCase = true)) {
                trustHttpsEveryone()
            }
            conn.readTimeout = 5000
            conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8")
            conn.addRequestProperty("User-Agent", "Mozilla")
            conn.addRequestProperty("Referer", "google.com")
            var redirect = false
            val status = conn.responseCode
            if (status != HttpURLConnection.HTTP_OK) {
                if (status == HttpURLConnection.HTTP_MOVED_TEMP || status == HttpURLConnection.HTTP_MOVED_PERM
                        || status == HttpURLConnection.HTTP_SEE_OTHER)
                    redirect = true
            }
            if (redirect) {
                val newUrl = conn.getHeaderField("Location")
                if (newUrl.isNotEmpty())
                    return newUrl
            }
            return url
        }
    }
}

fun trustHttpsEveryone() {
    try {
        HttpsURLConnection.setDefaultHostnameVerifier { hostname, session -> true }

        val context = SSLContext.getInstance("TLS")
        context.init(null, arrayOf<X509TrustManager>(object : X509TrustManager {
            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        }), SecureRandom())
        HttpsURLConnection.setDefaultSSLSocketFactory(context.socketFactory)
    } catch (e: Exception) {
    }
}
package com.canyon.commons

import java.io.*
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

object GZipUtils {
    val BUFFER = 4096
    val EXT = ".gz"

    /**
     * 数据压缩
     *
     * @param data
     * @return
     * @throws Exception
     */
    fun compress(data: ByteArray): ByteArray? {
        var output: ByteArray? = null
        ByteArrayInputStream(data).use { bais ->
            ByteArrayOutputStream().use { baos ->
                compress(bais, baos)
                output = baos.toByteArray()
                baos.flush()
            }
        }
        return output
    }

    /**
     * 文件压缩
     *
     * @param file
     * @param delete 是否删除原始文件
     * @throws Exception
     */
    @JvmOverloads
    fun compress(file: File, delete: Boolean = true) {
        FileOutputStream(file.path + EXT).use { fos ->
            FileInputStream(file).use { fis ->
                compress(fis, fos)
                fos.flush()
            }
        }
        if (delete) {
            file.delete()
        }
    }

    /**
     * 数据压缩
     *
     * @param is
     * @param os
     * @throws Exception
     */
    fun compress(inputStream: InputStream, outputStream: OutputStream) {
        GZIPOutputStream(outputStream).use { gos ->
            inputStream.copyTo(gos, BUFFER)
            gos.finish()
            gos.flush()
        }
    }

    /**
     * 文件压缩
     *
     * @param path
     * @param delete 是否删除原始文件
     * @throws Exception
     */
    @JvmOverloads
    fun compress(path: String, delete: Boolean = false) {
        val file = File(path)
        compress(file, delete)
    }

    /**
     * 数据解压缩
     *
     * @param data
     * @return
     * @throws Exception
     */
    fun decompress(data: ByteArray): ByteArray? {
        var result: ByteArray? = null
        ByteArrayInputStream(data).use { bais ->
            ByteArrayOutputStream().use { baos ->
                decompress(bais, baos)
                baos.flush()
                result = baos.toByteArray()
            }
        }
        return result
    }

    /**
     * 文件解压缩
     *
     * @param file
     * @param delete 是否删除原始文件
     * @throws Exception
     */
    @JvmOverloads
    fun decompress(file: File, delete: Boolean = true) {
        FileInputStream(file).use { fis ->
            FileOutputStream(file.path.replace(EXT, "")).use { fos ->
                decompress(fis, fos)
                fos.flush()
            }
        }
        if (delete) {
            file.delete()
        }
    }

    /**
     * 数据解压缩
     *
     * @param is
     * @param os
     * @throws Exception
     */
    fun decompress(inputStream: InputStream, outputStream: OutputStream) {
        GZIPInputStream(inputStream).use { gis ->
            gis.copyTo(outputStream, BUFFER)
        }
    }

    /**
     * 文件解压缩
     *
     * @param path
     * @param delete 是否删除原始文件
     * @throws Exception
     */
    @JvmOverloads
    fun decompress(path: String, delete: Boolean = true) {
        val file = File(path)
        decompress(file, delete)
    }
}
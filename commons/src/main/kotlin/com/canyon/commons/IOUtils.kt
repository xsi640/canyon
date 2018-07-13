package com.canyon.commons

import org.apache.commons.io.FilenameUtils
import java.io.*
import java.nio.charset.Charset
import java.util.*
import java.util.regex.Pattern

object IOUtils {
    /**
     * 判断文件或文件夹是否存在
     *
     * @param path 路径
     * @return
     */
    fun exists(path: String): Boolean {
        val f = File(path)
        return f.exists()
    }

    /**
     * 判断是否是文件
     *
     * @param path 文件路径
     * @return
     */
    fun isFile(path: String): Boolean {
        val f = File(path)
        return f.isFile
    }

    /**
     * 判断是否是文件目录
     *
     * @param path 文件目录路径
     * @return
     */
    fun isDirectory(path: String): Boolean {
        val f = File(path)
        return f.isDirectory
    }

    /**
     * 获取文件大小
     *
     * @param path 文件路径
     * @return
     */
    fun getFileSize(path: String): Long {
        val f = File(path)
        if (f.isFile) {
            return f.length()
        }
        throw IllegalArgumentException("The file path is not file. path:$path")
    }

    /**
     * 链接路径
     *
     * @param paths
     * @return
     */
    fun combine(vararg paths: String): String {
        var result = ""
        for (path in paths) {
            result = FilenameUtils.concat(result, path)
        }
        return result
    }

    /**
     * 获取文件目录
     *
     * @param path
     * @return
     */
    fun getDirectory(path: String): String {
        val file = File(path)
        return file.parent
    }

    /**
     * 删除指定文件
     * @param path 文件路径
     * @param pattern 文件名称匹配的正则表达式
     */
    fun delete(path: String, pattern: String) {
        val f = File(path)
        if (f.exists()) {
            if (f.isFile) {
                if (Pattern.compile(pattern).matcher(f.name).find()) {
                    f.delete()
                }
            } else {
                val files = f.listFiles()
                if (files != null) {
                    for (file in files) {
                        delete(file.path, pattern)
                    }
                }
            }
        }
    }

    /**
     * 删除文件或目录
     *
     * @param path
     */
    fun delete(path: String) {
        val f = File(path)
        if (f.exists()) {
            if (f.isFile) {
                f.delete()
            } else {
                try {
                    org.apache.commons.io.FileUtils.deleteDirectory(f)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    /**
     * 获取文件的扩展名，不带"."
     *
     * @param path
     * @return
     */
    fun getExtension(path: String): String {
        return FilenameUtils.getExtension(path)
    }

    /**
     * 创建路径
     *
     * @param path
     */
    fun createDirectory(path: String) {
        val f = File(path)
        if (!f.exists())
            f.mkdirs()
    }

    fun getAllFiles(path: String): List<File> {
        val result = ArrayList<File>()
        val file = File(path)
        if (file.isFile) {
            result.add(file)
        } else if (file.isDirectory) {
            getAllFiles(file, result)
        }
        return result
    }

    private fun getAllFiles(file: File, fileLists: MutableList<File>) {
        val files = file.listFiles()
        if (files != null) {
            for (f in files) {
                fileLists.add(f)
                if (f.isDirectory) {
                    getAllFiles(f, fileLists)
                }
            }
        }
    }

    /**
     * 读取文件所有文本内容
     *
     * @param path
     * @return
     */
    @JvmOverloads
    fun readFileAllText(path: String, charset: Charset = EncodingUtils.DEFAULT_CHARSET): String {
        val f = File(path)
        if (f.isFile && f.canRead()) {
            FileInputStream(f).use { fis ->
                return org.apache.commons.io.IOUtils.toString(fis, EncodingUtils.DEFAULT_CHARSET)
            }
        }
        throw IllegalArgumentException("The file can't read. paht:$path")
    }

    /**
     * 读取文件所有行
     *
     * @param path
     * @return
     */
    @JvmOverloads
    fun readFileAllLine(path: String, charset: Charset = EncodingUtils.DEFAULT_CHARSET): List<String>? {
        val f = File(path)
        if (f.isFile && f.canRead()) {
            FileInputStream(f).use { fis ->
                return org.apache.commons.io.IOUtils.readLines(fis, EncodingUtils.DEFAULT_CHARSET)
            }
        }
        throw IllegalArgumentException("The file can't read. paht:$path")
    }

    /**
     * 将制定内容写入一个文件，默认UTF-8编码
     *
     * @param path
     * @param text
     * @param overwrite
     * @param charset
     */
    @JvmOverloads
    fun writeFileAllText(path: String, text: String, overwrite: Boolean = true, charset: Charset = EncodingUtils.DEFAULT_CHARSET) {
        val file = File(path)
        if (file.exists() && !overwrite)
            return
        if (!file.parentFile.exists()) {
            file.parentFile.mkdir()
        }
        FileOutputStream(file).use { fos ->
            org.apache.commons.io.IOUtils.write(text, fos, EncodingUtils.DEFAULT_CHARSET)
        }
    }

    /**
     * 将制定内容写入一个文件，默认UTF-8编码
     *
     * @param path
     * @param lines
     * @param overwrite
     */
    @JvmOverloads
    fun writeFileAllLine(path: String, lines: List<String>, overwrite: Boolean = true, charset: Charset = EncodingUtils.DEFAULT_CHARSET) {
        val file = File(path)
        if (file.exists() && !overwrite)
            return
        if (!file.parentFile.exists()) {
            file.parentFile.mkdir()
        }
        FileOutputStream(file).use { fos ->
            org.apache.commons.io.IOUtils.writeLines(lines, System.lineSeparator(), fos, charset)
        }
    }

    /**
     * 将制定内容追加到一个文件，如果文件不存在，创建文件
     *
     * @param path
     * @param lines
     */
    fun appendFile(path: String, vararg lines: String, charset: Charset = EncodingUtils.DEFAULT_CHARSET) {
        val file = File(path)
        val exists = file.exists()
        if (!exists) {
            val directory = getDirectory(path)
            createDirectory(directory)
        }
        FileOutputStream(file, true).use { fos ->
            org.apache.commons.io.IOUtils.writeLines(lines.asList(), System.lineSeparator(), fos, charset)
        }
    }

    /**
     * 读取文件
     *
     * @param path
     * @return
     */
    fun readeFile(path: String): ByteArray? {
        val f = File(path)
        if (f.isFile && f.canRead()) {
            FileInputStream(f).use { fis ->
                return org.apache.commons.io.IOUtils.toByteArray(fis)
            }
        }
        throw IllegalArgumentException("The file can't read or not exists. path:$path")
    }

    /**
     * 写入文件
     *
     * @param path
     * @param data
     * @param overwrite
     */
    @JvmOverloads
    fun writeFile(path: String, data: ByteArray, overwrite: Boolean = true) {
        val file = File(path)
        if (file.exists() && !overwrite)
            return
        if (!file.parentFile.exists()) {
            file.parentFile.mkdir()
        }
        FileOutputStream(file).use { fos ->
            org.apache.commons.io.IOUtils.write(data, fos)
        }
    }

}

package com.canyon.commons

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream

object ZipUtils {

    private val COMPRESS_LEVEL = 8

    fun compress(sourcePath: String, zipPath: String) {
        val file = File(sourcePath)
        if (!file.exists())
            return

        val dir = when {
            file.isFile -> IOUtils.getDirectory(sourcePath)
            file.isDirectory -> file.absolutePath
            else -> throw IllegalArgumentException("The sourcePath not exists. sourcePath:$sourcePath")
        }

        val files = IOUtils.getAllFiles(sourcePath)

        IOUtils.createDirectory(IOUtils.getDirectory(zipPath))
        FileOutputStream(zipPath).use { fos ->
            ZipOutputStream(fos).use { zip ->
                zip.setLevel(COMPRESS_LEVEL)
                for (f in files) {
                    var name = ""
                    if (f.isDirectory) {
                        name += f.absolutePath.substring(dir.length + 1) + File.separator
                    } else if (f.isFile) {
                        name = f.absolutePath.substring(dir.length + 1)
                    }
                    val zipEntry = ZipEntry(name)
                    zip.putNextEntry(zipEntry)
                    if (f.isFile) {
                        FileInputStream(f).use { fis ->
                            org.apache.commons.io.IOUtils.copy(fis, zip)
                        }
                    }
                }
            }
        }

        fun decompress(targetPath: String, zipPath: String) {
            @Suppress("NAME_SHADOWING")
            val file = File(zipPath)
            if (!file.isFile || !file.canRead()) {
                throw IllegalArgumentException("The zipPath is not file or can't read. zipPath:$zipPath")
            }

            IOUtils.createDirectory(IOUtils.getDirectory(targetPath))

            FileInputStream(zipPath).use { fis ->
                ZipInputStream(fis).use { zip ->
                    var ze: ZipEntry? = null
                    while ({ ze = zip.nextEntry; ze }() != null) {
                        val filename = ze!!.name
                        val newFileName = IOUtils.combine(targetPath, filename)
                        if (newFileName.endsWith(File.separator)) {
                            IOUtils.createDirectory(newFileName)
                        } else {
                            val newDir = IOUtils.getDirectory(newFileName)
                            IOUtils.createDirectory(newDir)
                            FileOutputStream(newFileName).use { fos ->
                                org.apache.commons.io.IOUtils.copy(zip, fos)
                            }
                        }
                    }
                }
            }
        }
    }
}
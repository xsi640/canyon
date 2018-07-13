package com.canyon.commons

import java.beans.XMLDecoder
import java.beans.XMLEncoder
import java.io.*

object SerializeUtils {
    fun serializeToFile(path: String, obj: Any) {
        val dir = IOUtils.getDirectory(path)
        IOUtils.createDirectory(dir)
        FileOutputStream(path).use { fos->
            ObjectOutputStream(fos).use { out->
                out.writeObject(obj)
                out.flush()
            }
        }
    }

    fun <T> deserializeFromFile(path: String): T? {
        var result: T? = null
        if (!IOUtils.isFile(path))
            return result

        FileInputStream(path).use { fis->
            ObjectInputStream(fis).use { ois->
                result = ois.readObject() as T
            }
        }
        return result
    }

    fun serializeToBytes(obj: Any?): ByteArray? {
        var result: ByteArray? = null
        if (obj == null)
            return result

        var stream: ByteArrayOutputStream? = null
        var out: ObjectOutputStream? = null
        try {
            stream = ByteArrayOutputStream()
            out = ObjectOutputStream(stream)
            out.writeObject(obj)
            result = stream.toByteArray()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (out != null) {
                try {
                    out.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
            if (stream != null) {
                try {
                    stream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
        return result
    }

    fun <T> deserializeFromBytes(data: ByteArray?): T? {
        var result: T? = null
        if (data == null || data.size == 0)
            return result

        var `in`: ObjectInputStream? = null
        try {
            `in` = ObjectInputStream(ByteArrayInputStream(data))
            result = `in`.readObject() as T
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (`in` != null) {
                try {
                    `in`.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }

        return result
    }

    fun xmlSerializeToFile(path: String, obj: Any?) {
        if (obj == null)
            return

        val dir = IOUtils.getDirectory(path)
        IOUtils.createDirectory(dir)

        var encoder: XMLEncoder? = null
        try {
            encoder = XMLEncoder(FileOutputStream(path))
            encoder.writeObject(obj)
            encoder.flush()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } finally {
            encoder?.close()
        }
    }

    fun <T> xmlDeserializeFromFile(path: String): T? {
        var result: T? = null
        if (!IOUtils.isFile(path))
            return result

        var decoder: XMLDecoder? = null
        try {
            decoder = XMLDecoder(FileInputStream(path))
            result = decoder.readObject() as T
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } finally {
            decoder?.close()
        }
        return result
    }
}

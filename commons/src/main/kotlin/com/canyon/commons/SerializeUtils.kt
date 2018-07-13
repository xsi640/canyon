package com.canyon.commons

import java.beans.XMLDecoder
import java.beans.XMLEncoder
import java.io.*

object SerializeUtils {
    fun serializeToFile(path: String, obj: Any) {
        FileOutputStream(path).use { fos ->
            ObjectOutputStream(fos).use { out ->
                out.writeObject(obj)
                out.flush()
            }
        }
    }

    fun <T> deserializeFromFile(path: String): T {
        FileInputStream(path).use { fis ->
            ObjectInputStream(fis).use { ois ->
                return ois.readObject() as T
            }
        }
    }

    fun serializeToBytes(obj: Any): ByteArray {
        ByteArrayOutputStream().use { baos ->
            ObjectOutputStream(baos).use { out ->
                out.writeObject(obj)
                return baos.toByteArray()
            }
        }
    }

    fun <T> deserializeFromBytes(data: ByteArray): T {
        ByteArrayInputStream(data).use { bais ->
            ObjectInputStream(bais).use { ois ->
                return ois.readObject() as T
            }
        }
    }

    fun xmlSerializeToFile(path: String, obj: Any) {
        FileOutputStream(path).use { fos ->
            XMLEncoder(fos).use { xml ->
                xml.writeObject(obj)
                xml.flush()
            }
        }
    }

    fun <T> xmlDeserializeFromFile(path: String): T {
        FileInputStream(path).use { fis ->
            XMLDecoder(fis).use { xml ->
                return xml.readObject() as T
            }
        }
    }
}

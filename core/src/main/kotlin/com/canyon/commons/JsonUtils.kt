package com.canyon.commons

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONException
import com.alibaba.fastjson.JSONObject
import com.alibaba.fastjson.serializer.SerializerFeature
import java.io.InputStream

object JsonUtils {
    init {
        JSON.DEFAULT_GENERATE_FEATURE = JSON.DEFAULT_GENERATE_FEATURE and SerializerFeature.WriteEnumUsingName.mask.inv()
    }

    fun toString(obj: Any): String {
        return JSON.toJSONString(obj)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> parse(json: String): T {
        return JSON.parse(json) as T
    }

    fun <T> parse(json: String, clazz: Class<T>): T {
        return JSON.parseObject(json, clazz)
    }

    fun <T> parse(inputStream: InputStream, clazz: Class<T>): T {
        return JSON.parseObject(inputStream, clazz)
    }

    fun parseToJSONObject(json: String): JSONObject {
        return JSON.parseObject(json)
    }

    fun parseToJsonArray(json: String): JSONArray {
        return JSON.parseArray(json)
    }

    fun parseToJSON(json: String): Any {
        return try {
            parseToJsonArray(json)
        } catch (e: JSONException) {
            parseToJSONObject(json)
        }
    }
}

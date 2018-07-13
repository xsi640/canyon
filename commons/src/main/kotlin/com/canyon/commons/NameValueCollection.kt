package com.canyon.commons

import java.util.*


/**
 * 减值的集合，主要用于描述http请求的参数
 */
class NameValueCollection {

    private val map = Collections.synchronizedMap(HashMap<String, MutableList<String>>())

    /**
     * 集合是否为空
     * @return
     */
    val isEmpty: Boolean
        get() = map.isEmpty()

    /**
     * 获得集合大小
     * @return
     */
    fun size(): Int {
        return map.size
    }

    /**
     * 集合是否存在某个Key
     * @param key
     * @return
     */
    fun containsKey(key: Any): Boolean {
        return map.containsKey(key)
    }

    /**
     * 集合是否存在某个值
     * @param value
     * @return
     */
    fun containsValue(value: Any): Boolean {
        return map.containsValue(value)
    }

    /**
     * 获取某个key的值
     * @param key
     * @return
     */
    operator fun get(key: Any): String {
        val lists = map[key]
        return if (lists == null || lists.isEmpty()) "" else lists.toSplitString(",")
    }

    /**
     * 设置key-value
     * @param key
     * @param value
     */
    fun put(key: String, value: String) {
        var lists: MutableList<String>? = map[key]
        if (lists == null) {
            lists = ArrayList()
            lists.add(value)
        } else {
            lists.add(value)
        }
        this.map[key] = lists
    }

    /**
     * 移除
     * @param key
     */
    fun remove(key: Any) {
        map.remove(key)
    }

    /**
     * 清空
     */
    fun clear() {
        map.clear()
    }

    /**
     * 获取所有Key
     * @return
     */
    fun keySet(): Set<String> {
        return map.keys
    }

    /**
     * 获取所有值
     * @return
     */
    fun values(): Collection<List<String>> {
        return map.values
    }

    /**
     * 获取所有值（字符串形式）
     * @return
     */
    fun valuesAsString(): Collection<String>? {
        val collection = map.values
        if (collection.isNotEmpty()) {
            val c = ArrayList<String>(collection.size)
            for (lists in collection) {
                if (lists != null)
                    c.add(lists.toSplitString(","))
            }
            return c
        }
        return null
    }
}

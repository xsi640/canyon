package com.canyon.commons.cache

/**
 * 静态缓存接口
 * @param <TKey>
 * @param <TValue>
</TValue></TKey> */
interface StaticCache<TKey, TValue> {
    /**
     * 缓存大小
     * @return
     */
    fun size(): Int

    /**
     * 缓存所有对象
     * @return
     */
    fun all(): List<TValue>

    /**
     * 新增缓存
     * @param key
     * @param value
     */
    fun add(key: TKey, value: TValue)

    /**
     * 删除缓存
     * @param key
     */
    fun remove(key: TKey)

    /**
     * 获取缓存
     * @param key
     * @return
     */
    operator fun get(key: TKey): TValue?

    /**
     * 清除缓存
     */
    fun clear()
}

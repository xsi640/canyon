package com.canyon.commons.cache

/**
 * 热缓存接口
 * @param <TKey>
 * @param <TValue>
</TValue></TKey> */
interface HotCache<TKey, TValue> {

    /**
     * 获取检查缓存的时间
     * @return
     */
    /**
     * 设置检查时间（单位：秒）
     * @param detectSpanInSecs
     */
    var detectSpanInSecs: Int

    /**
     * 获取缓存的过期时间
     * @return
     */
    /**
     * 设置缓存的过期时间（单位：秒）
     * @param maxMuteSpanInSecs
     */
    var maxMuteSpanInSecs: Int

    /**
     * 获取缓存的数量
     * @return
     */
    fun size(): Int

    /**
     * 获取所有缓存对象
     * @return
     */
    fun all(): List<TValue>

    /**
     * 添加一个缓存
     * @param key
     * @param value
     */
    fun add(key: TKey, value: TValue)

    /**
     * 移除一个缓存
     * @param key
     */
    fun remove(key: TKey)

    /**
     * 获取缓存对象
     * @param key
     * @return
     */
    operator fun get(key: TKey): TValue?

    /**
     * 清除缓存
     */
    fun clear()
}

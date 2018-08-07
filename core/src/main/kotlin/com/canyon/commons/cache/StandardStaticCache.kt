package com.canyon.commons.cache

import java.util.concurrent.ConcurrentHashMap

class StandardStaticCache<TKey, TValue> : StaticCache<TKey, TValue> {

    private val cache = ConcurrentHashMap<TKey, TValue>()

    override fun size(): Int {
        return cache.size
    }

    override fun all(): List<TValue> {
        return cache.elements().toList()
    }

    override fun add(key: TKey, value: TValue) {
        cache[key] = value
    }

    override fun remove(key: TKey) {
        cache.remove(key)
    }

    override fun get(key: TKey): TValue? {
        return cache.get(key)
    }

    override fun clear() {
        cache.clear()
    }

}
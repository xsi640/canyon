package com.canyon.commons.cache

import com.canyon.commons.engines.StandardCircleEngine
import java.util.ArrayList
import java.util.HashMap
import java.util.concurrent.locks.ReentrantReadWriteLock

/**
 * 标准热缓存
 *
 * @param <TKey>
 * @param <TValue>
</TValue></TKey> */
class StandardHotCache<TKey, TValue> : HotCache<TKey, TValue>, Runnable {

    private val cache = HashMap<TKey, CachePackage<TKey, TValue>>()

    override var detectSpanInSecs = 10
    override var maxMuteSpanInSecs = 5
    private var standardCircleEngine: StandardCircleEngine? = null
    private val locker = ReentrantReadWriteLock()

    init {
        if (this.detectSpanInSecs > 0) {
            this.standardCircleEngine = StandardCircleEngine(this)
            this.standardCircleEngine!!.detectSpanInSecs = this.detectSpanInSecs
            this.standardCircleEngine!!.start()
        }
    }

    override fun size(): Int {
        locker.readLock().lock()
        try {
            return cache.size
        } finally {
            locker.readLock().unlock()
        }
    }

    override fun all(): List<TValue> {
        locker.readLock().lock()
        val lists: List<CachePackage<TKey, TValue>>?
        try {
            lists = ArrayList(cache.values)
        } finally {
            locker.readLock().unlock()
        }
        if (lists == null)
            return ArrayList()
        val result = ArrayList<TValue>(lists.size)
        for (item in lists)
            result.add(item.value)
        return result
    }

    override fun add(key: TKey, value: TValue) {
        locker.writeLock().lock()
        try {
            this.cache[key] = CachePackage(key, value)
        } finally {
            locker.writeLock().unlock()
        }
    }

    override fun remove(key: TKey) {
        locker.writeLock().lock()
        try {
            this.cache.remove(key)
        } finally {
            locker.writeLock().unlock()
        }
    }

    override fun get(key: TKey): TValue? {
        var result: TValue? = null
        val item: CachePackage<TKey, TValue>?
        locker.writeLock().lock()
        try {
            item = this.cache[key]
        } finally {
            locker.writeLock().unlock()
        }
        if (item != null)
            result = item.value
        return result
    }

    override fun clear() {
        locker.writeLock().lock()
        try {
            cache.clear()
        } finally {
            locker.writeLock().unlock()
        }
    }

    override fun run() {
        val now = System.currentTimeMillis()
        val keyLists = ArrayList<TKey>()
        val values: Collection<CachePackage<TKey, TValue>>?
        locker.readLock().lock()
        try {
            values = cache.values
        } finally {
            locker.readLock().unlock()
        }
        if (values != null) {
            for (item in values) {
                val time = now - item.lastAccessTime
                if (time > this.maxMuteSpanInSecs * 1000) {
                    keyLists.add(item.key)
                }
            }
            for (key in keyLists) {
                this.remove(key)
            }
        }
    }

    protected fun finalize() {
        if (this.standardCircleEngine != null)
            this.standardCircleEngine!!.stop()
    }

}

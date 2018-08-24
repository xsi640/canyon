package com.canyon.commons.cache;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.canyon.commons.engines.StandardCircleEngine;

/**
 * 标准热缓存
 *
 * @param <TKey>
 * @param <TValue>
 */
public class StandardHotCache<TKey, TValue> implements HotCache<TKey, TValue>, Runnable {

    private Map<TKey, CachePackage<TKey, TValue>> cache = new HashMap<>();
    private int detectSpanInSecs = 10;
    private int maxMuteSpanInSecs = 5;
    private StandardCircleEngine standardCircleEngine;
    private final ReentrantReadWriteLock locker = new ReentrantReadWriteLock();

    public void setDetectSpanInSecs(int detectSpanInSecs) {
        this.detectSpanInSecs = detectSpanInSecs;
    }

    public int getDetectSpanInSecs() {
        return detectSpanInSecs;
    }

    public void setMaxMuteSpanInSecs(int maxMuteSpanInSecs) {
        this.maxMuteSpanInSecs = maxMuteSpanInSecs;
    }

    public int getMaxMuteSpanInSecs() {
        return maxMuteSpanInSecs;
    }

    public void initialize() {
        if (this.detectSpanInSecs > 0) {
            this.standardCircleEngine = new StandardCircleEngine(this);
            this.standardCircleEngine.setDetectSpanInSecs(this.detectSpanInSecs);
            this.standardCircleEngine.start();
        }
    }

    public int size() {
        locker.readLock().lock();
        try {
            return cache.size();
        } finally {
            locker.readLock().unlock();
        }
    }

    public List<TValue> all() {
        locker.readLock().lock();
        List<CachePackage<TKey, TValue>> lists = null;
        try {
            lists = new ArrayList<>(cache.values());
        } finally {
            locker.readLock().unlock();
        }
        if (lists == null)
            return null;
        List<TValue> result = new ArrayList<TValue>(lists.size());
        for (CachePackage<TKey, TValue> item : lists)
            result.add(item.getValue());
        return result;
    }

    public void add(TKey key, TValue value) {
        locker.writeLock().lock();
        try {
            this.cache.put(key, new CachePackage<>(key, value));
        } finally {
            locker.writeLock().unlock();
        }
    }

    public void remove(TKey key) {
        locker.writeLock().lock();
        try {
            this.cache.remove(key);
        } finally {
            locker.writeLock().unlock();
        }
    }

    public TValue get(TKey key) {
        TValue result = null;
        CachePackage<TKey, TValue> item = null;
        locker.writeLock().lock();
        try {
            item = this.cache.get(key);
        } finally {
            locker.writeLock().unlock();
        }
        if (item != null)
            result = item.getValue();
        return result;
    }

    public void clear() {
        locker.writeLock().lock();
        try {
            cache.clear();
        } finally {
            locker.writeLock().unlock();
        }
    }

    public void run() {
        long now = System.currentTimeMillis();
        List<TKey> keyLists = new ArrayList<TKey>();
        Collection<CachePackage<TKey, TValue>> values = null;
        locker.readLock().lock();
        try {
            values = cache.values();
        } finally {
            locker.readLock().unlock();
        }
        if (values != null) {
            for (CachePackage<TKey, TValue> item : values) {
                long time = now - item.getLastAccessTime();
                if (time > this.maxMuteSpanInSecs * 1000) {
                    keyLists.add(item.getKey());
                }
            }
            for (TKey key : keyLists) {
                this.remove(key);
            }
        }
    }

    @Override
    protected void finalize() throws Throwable {
        if (this.standardCircleEngine != null)
            this.standardCircleEngine.stop();
        super.finalize();
    }

}

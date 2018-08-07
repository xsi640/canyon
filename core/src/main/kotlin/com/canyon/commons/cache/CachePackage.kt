package com.canyon.commons.cache

/**
 * 缓存封装包
 * @param <TKey> 缓存Key
 * @param <TValue> 缓存Value
</TValue></TKey> */
class CachePackage<TKey, TValue>(var key: TKey, var value: TValue) {
    var lastAccessTime = System.currentTimeMillis()
}

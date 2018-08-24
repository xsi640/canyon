package com.canyon.commons.cache;

import java.util.*;

/**
 * 标准静态缓存
 * @param <TKey>
 * @param <TValue>
 */
public class StandardStaticCache<TKey, TValue> implements StaticCache<TKey, TValue> {

	private Map<TKey, TValue> cache = Collections.synchronizedMap(new HashMap<>());

	public int size() {
		return cache.size();
	}

	public List<TValue> all() {
		return new ArrayList<TValue>(cache.values());
	}

	public void add(TKey key, TValue value) {
		cache.put(key, value);
	}

	public void remove(TKey key) {
		cache.remove(key);
	}

	public TValue get(TKey key) {
		return cache.get(key);
	}

	public void clear(){
		cache.clear();
	}
}

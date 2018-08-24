package com.canyon.commons.cache;

import java.util.List;

/**
 * 静态缓存接口
 * @param <TKey>
 * @param <TValue>
 */
public interface StaticCache<TKey, TValue> {
	/**
	 * 缓存大小
	 * @return
	 */
	int size();

	/**
	 * 缓存所有对象
	 * @return
	 */
	List<TValue> all();

	/**
	 * 新增缓存
	 * @param key
	 * @param value
	 */
	void add(TKey key, TValue value);

	/**
	 * 删除缓存
	 * @param key
	 */
	void remove(TKey key);

	/**
	 * 获取缓存
	 * @param key
	 * @return
	 */
	TValue get(TKey key);

	/**
	 * 清除缓存
	 */
	void clear();
}

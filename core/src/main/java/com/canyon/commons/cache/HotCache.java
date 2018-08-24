package com.canyon.commons.cache;

import java.util.List;

/**
 * 热缓存接口
 * @param <TKey>
 * @param <TValue>
 */
public interface HotCache<TKey, TValue> {

	/**
	 * 设置检查时间（单位：秒）
	 * @param detectSpanInSecs
	 */
	void setDetectSpanInSecs(int detectSpanInSecs);

	/**
	 * 获取检查缓存的时间
	 * @return
	 */
	int getDetectSpanInSecs();

	/**
	 * 设置缓存的过期时间（单位：秒）
	 * @param maxMuteSpanInSecs
	 */
	void setMaxMuteSpanInSecs(int maxMuteSpanInSecs);

	/**
	 * 获取缓存的过期时间
	 * @return
	 */
	int getMaxMuteSpanInSecs();

	/**
	 * 初始化
	 */
	void initialize();

	/**
	 * 获取缓存的数量
	 * @return
	 */
	int size();

	/**
	 * 获取所有缓存对象
	 * @return
	 */
	List<TValue> all();

	/**
	 * 添加一个缓存
	 * @param key
	 * @param value
	 */
	void add(TKey key, TValue value);

	/**
	 * 移除一个缓存
	 * @param key
	 */
	void remove(TKey key);

	/**
	 * 获取缓存对象
	 * @param key
	 * @return
	 */
	TValue get(TKey key);

	/**
	 * 清除缓存
	 */
	void clear();
}

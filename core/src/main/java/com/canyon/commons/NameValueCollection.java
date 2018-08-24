package com.canyon.commons;

import java.util.*;

/**
 * 减值的集合，主要用于描述http请求的参数
 */
public class NameValueCollection {

	private Map<String, List<String>> map = Collections.synchronizedMap(new HashMap<>());

	/**
	 * 获得集合大小
	 * @return
	 */
	public int size() {
		return map.size();
	}

	/**
	 * 集合是否为空
	 * @return
	 */
	public boolean isEmpty() {
		return map.isEmpty();
	}

	/**
	 * 集合是否存在某个Key
	 * @param key
	 * @return
	 */
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	/**
	 * 集合是否存在某个值
	 * @param value
	 * @return
	 */
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	/**
	 * 获取某个key的值
	 * @param key
	 * @return
	 */
	public String get(Object key) {
		List<String> lists = map.get(key);
		if (lists == null || lists.size() == 0)
			return "";
		return CollectionUtils.toString(lists, ",");
	}

	/**
	 * 设置key-value
	 * @param key
	 * @param value
	 */
	public void put(String key, String value) {
		List<String> lists = map.get(key);
		if (lists == null) {
			lists = new ArrayList<String>();
			lists.add(value);
		} else {
			lists.add(value);
		}
		this.map.put(key, lists);
	}

	/**
	 * 移除
	 * @param key
	 */
	public void remove(Object key) {
		map.remove(key);
	}

	/**
	 * 清空
	 */
	public void clear() {
		map.clear();
	}

	/**
	 * 获取所有Key
	 * @return
	 */
	public Set<String> keySet() {
		return map.keySet();
	}

	/**
	 * 获取所有值
	 * @return
	 */
	public Collection<List<String>> values(){
		return map.values();
	}

	/**
	 * 获取所有值（字符串形式）
	 * @return
	 */
	public Collection<String> valuesAsString() {
		Collection<List<String>> collection = map.values();
		if (collection != null && collection.size() > 0) {
			Collection<String> c = new ArrayList<String>(collection.size());
			for (List<String> lists : collection) {
				c.add(CollectionUtils.toString(lists, ","));
			}
			return c;
		}
		return null;
	}
}

package org.chris.cattom.server;

import java.util.HashMap;
import java.util.Map;
/**
 * 
 * @author chris
 *
 */
public class Cache {
	private static Cache cache = new Cache();//初始化单例Cache
	/**
	 * Cache容器
	 */
	private Map<Object, Object> cacheMap;
	/**
	 * 私有Cache构造方法
	 * 初始化Cache容器
	 */
	private Cache(){
		cacheMap = new HashMap<Object, Object>();
	}
	/**
	 * 获得Cache实例
	 * @return
	 */
	protected static Cache getCache(){
		return cache;
	}
	/**
	 * 将对象放入缓存
	 * @param key
	 * @param value
	 */
	public void put(Object key, Object value){
		cacheMap.put(key, value);
	}
	/**
	 * 从缓存取出对象
	 * @param key
	 * @return
	 */
	public Object get(Object key){
		return key == null?null:cacheMap.get(key);
	}
	/**
	 * 清空缓存
	 * @return
	 */
	public void clear(){
		cacheMap.clear();
	}

	

}

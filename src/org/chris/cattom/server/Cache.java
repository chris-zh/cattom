package org.chris.cattom.server;

import java.util.HashMap;
import java.util.Map;
/**
 * 
 * @author chris
 *
 */
public class Cache {
	private static Cache cache = new Cache();//��ʼ������Cache
	/**
	 * Cache����
	 */
	private Map<Object, Object> cacheMap;
	/**
	 * ˽��Cache���췽��
	 * ��ʼ��Cache����
	 */
	private Cache(){
		cacheMap = new HashMap<Object, Object>();
	}
	/**
	 * ���Cacheʵ��
	 * @return
	 */
	protected static Cache getCache(){
		return cache;
	}
	/**
	 * ��������뻺��
	 * @param key
	 * @param value
	 */
	public void put(Object key, Object value){
		cacheMap.put(key, value);
	}
	/**
	 * �ӻ���ȡ������
	 * @param key
	 * @return
	 */
	public Object get(Object key){
		return key == null?null:cacheMap.get(key);
	}
	/**
	 * ��ջ���
	 * @return
	 */
	public void clear(){
		cacheMap.clear();
	}

	

}

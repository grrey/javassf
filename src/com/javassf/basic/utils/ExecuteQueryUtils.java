package com.javassf.basic.utils;

import java.util.HashMap;
import java.util.Map;
/**
 * 
 * query查询工具类
 *
 */
public class ExecuteQueryUtils {
	private StringBuilder hqlb;//hql
	private Map<String, Object> maps;//存储对应属性的值

	private ExecuteQueryUtils(String hql) {
		this.hqlb = new StringBuilder(hql);
	}

	public static ExecuteQueryUtils create(String hql) {
		return new ExecuteQueryUtils(hql);
	}

	/**
	 * 追加hql
	 * @param hql
	 * @return
	 */
	public ExecuteQueryUtils append(String hql) {
		this.hqlb.append(hql);
		return this;
	}

	/**
	 * 将对应属性的值存到map中
	 * @param name
	 * @param value
	 * @return
	 */
	public ExecuteQueryUtils setParameter(String name, Object value) {
		if (this.maps == null) {
			this.maps = new HashMap();
		}
		this.maps.put(name, value);
		return this;
	}

	public String getHql() {
		return this.hqlb.toString();
	}

	public Map<String, Object> getMaps() {
		return this.maps;
	}
}

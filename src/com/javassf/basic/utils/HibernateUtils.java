package com.javassf.basic.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projection;
import org.hibernate.internal.CriteriaImpl;
import org.springframework.util.Assert;

/**
 * Hibernate工具类
 * @author wxj
 *
 */
public class HibernateUtils {
	/**
	 * 获取Projection
	 * @param criteria
	 * @return
	 */
	public static Projection getProjection(Criteria criteria) {
		CriteriaImpl impl = (CriteriaImpl) criteria;
		return impl.getProjection();
	}

	/**
	 * 通过反射获取排序实体
	 * @param criteria
	 * @return
	 */
	public static CriteriaImpl.OrderEntry[] getOrders(Criteria criteria) {
		CriteriaImpl impl = (CriteriaImpl) criteria;
		Field field = getOrderEntriesField(criteria);
		try {
			return (CriteriaImpl.OrderEntry[]) ((List) field.get(impl)).toArray(new CriteriaImpl.OrderEntry[0]);
		} catch (Exception e) {
			throw new InternalError("异常");
		}
	}
    
	/**
	 * 移除排序字段
	 * @param criteria
	 * @return
	 */
	public static Criteria removeOrders(Criteria criteria) {
		CriteriaImpl impl = (CriteriaImpl) criteria;
		try {
			Field field = getOrderEntriesField(criteria);
			field.set(impl, new ArrayList());
			return impl;
		} catch (Exception e) {
			throw new InternalError("查询异常");
		}
	}

	public static Criteria addOrders(Criteria criteria, CriteriaImpl.OrderEntry[] orderEntries) {
		CriteriaImpl impl = (CriteriaImpl) criteria;
		try {
			Field field = getOrderEntriesField(criteria);
			for (int i = 0; i < orderEntries.length; i++) {
				List innerOrderEntries = (List) field.get(criteria);
				innerOrderEntries.add(orderEntries[i]);
			}
			return impl;
		} catch (Exception e) {
			throw new InternalError("异常");
		}
	}

	/**
	 * 暴力反射获取 orderEntries 字段
	 * @param criteria
	 * @return
	 */
	private static Field getOrderEntriesField(Criteria criteria) {
		//断言，判断criteria是否为空
		Assert.notNull(criteria, "criteria不存在");
		try {
			//暴力反射获取 orderEntries 字段
			Field field = CriteriaImpl.class.getDeclaredField("orderEntries");
			field.setAccessible(true);
			return field;
		} catch (Exception e) {
			throw new InternalError();
		}
	}

	/**
	 * 通过反射，获取对应字段的值
	 */
	public static Object getSimpleProperty(Object bean, String propName) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		return bean.getClass().getMethod(getReadMethod(propName), new Class[0]).invoke(bean, new Object[0]);
	}

	private static String getReadMethod(String name) {
		return "get" + name.substring(0, 1).toUpperCase(Locale.ENGLISH) + name.substring(1);
	}
}

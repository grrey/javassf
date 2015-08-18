package com.javassf.basic.comparator;

import java.io.Serializable;
import java.util.Comparator;
/**
 * 
 * 自定义比较器
 *
 */
public class BeanComparator implements Comparator<BeanInterface>, Serializable {
	private static final long serialVersionUID = 1L;
	public static final BeanComparator INSTANCE = new BeanComparator();

	/**
	 * 按排序顺序升序
	 */
	public int compare(BeanInterface o1, BeanInterface o2) {
		Number v1 = o1.getPriority();//获取排序顺序
		Number v2 = o2.getPriority();
		if (v1 == null)
			return 1;
		if (v2 == null)
			return -1;
		if (v1.longValue() > v2.longValue())
			return 1;
		if (v1.longValue() < v2.longValue()) {
			return -1;
		}
		return 0;
	}
}

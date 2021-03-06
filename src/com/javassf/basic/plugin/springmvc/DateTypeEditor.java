package com.javassf.basic.plugin.springmvc;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.springframework.util.StringUtils;
/**
 * 
 * PropertyEditorSupport 用来转化数据格式
 * a)setValue(Object value) 直接设置一个对象
 * b)setAsText(String text) 通过一个字符串来构造对象，一般在此方法中解析字符串，
 *将构造一个类对象，调用setValue(Object)来完成属性对象设置操作。
 *
 */
public class DateTypeEditor extends PropertyEditorSupport {
	public static final DateFormat DF_LONG = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static final DateFormat DF_SHORT = new SimpleDateFormat("yyyy-MM-dd");
	public static final int SHORT_DATE = 10;

	public void setAsText(String text) throws IllegalArgumentException {
		text = text.trim();
		
		//如果为空
		if (!StringUtils.hasText(text)) {
			setValue(null);
			return;
		}
		try {
			if (text.length() <= 10)
				setValue(new java.sql.Date(DF_SHORT.parse(text).getTime()));
			else
				setValue(new Timestamp(DF_LONG.parse(text).getTime()));
		} catch (ParseException ex) {
			IllegalArgumentException iae = new IllegalArgumentException("Could not parse date: " + ex.getMessage());
			iae.initCause(ex);
			throw iae;
		}
	}

	public String getAsText() {
		java.util.Date value = (java.util.Date) getValue();
		return value != null ? DF_LONG.format(value) : "";
	}
}

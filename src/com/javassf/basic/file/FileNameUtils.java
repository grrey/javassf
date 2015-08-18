package com.javassf.basic.file;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.RandomStringUtils;

import com.javassf.basic.utils.NumBeanUtils;

public class FileNameUtils {
	public static final DateFormat pathDf = new SimpleDateFormat("yyyyMM");

	public static final DateFormat nameDf = new SimpleDateFormat("ddHHmmss");

	/**
	 * 以当前时间生成文件名
	 * @return
	 */
	public static String genPathName() {
		return pathDf.format(new Date());
	}

	/**
	 * 时间+4个随机字符串
	 * @return
	 */
	public static String genFileName() {
		return nameDf.format(new Date()) + RandomStringUtils.random(4, NumBeanUtils.N36_CHARS);
	}
    /**
     * 加后缀名
     * @param ext
     * @return
     */
	public static String genFileName(String ext) {
		return genFileName() + "." + ext;
	}

	public static void main(String[] args) {
		System.out.println(genPathName());
		System.out.println(genFileName());
	}
}

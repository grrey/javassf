package com.javassf.basic.utils;

import java.io.File;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

public class UploadUtils {
	public static final DateFormat MONTH_FORMAT = new SimpleDateFormat("/yyyyMM/ddHHmmss");

	protected static final Pattern ILLEGAL_CURRENT_FOLDER_PATTERN = Pattern.compile("^[^/]|[^/]$|/\\.{1,2}|\\\\|\\||:|\\?|\\*|\"|<|>|\\p{Cntrl}");

	public static String generateFilename(String path, String ext) {
		return path + MONTH_FORMAT.format(new Date()) + RandomStringUtils.random(4, NumBeanUtils.N36_CHARS) + "." + ext;
	}

	public static String sanitizeFileName(String filename) {
		if (StringUtils.isBlank(filename)) {
			return filename;
		}
		String name = forceSingleExtension(filename);

		return name.replaceAll("\\\\|/|\\||:|\\?|\\*|\"|<|>|\\p{Cntrl}", "_");
	}

	public static String sanitizeFolderName(String folderName) {
		if (StringUtils.isBlank(folderName)) {
			return folderName;
		}

		return folderName.replaceAll("\\.|\\\\|/|\\||:|\\?|\\*|\"|<|>|\\p{Cntrl}", "_");
	}

	public static boolean isValidPath(String path) {
		if (StringUtils.isBlank(path)) {
			return false;
		}

		return !ILLEGAL_CURRENT_FOLDER_PATTERN.matcher(path).find();
	}

	public static String forceSingleExtension(String filename) {
		return filename.replaceAll("\\.(?![^.]+$)", "_");
	}

	public static boolean isSingleExtension(String filename) {
		return filename.matches("[^\\.]+\\.[^\\.]+");
	}

	public static void checkDirAndCreate(File dir) {
		if (!dir.exists())
			dir.mkdirs();
	}

	public static File getUniqueFile(File file) {
		if (!file.exists()) {
			return file;
		}
		File tmpFile = new File(file.getAbsolutePath());
		File parentDir = tmpFile.getParentFile();
		int count = 1;
		String extension = FilenameUtils.getExtension(tmpFile.getName());
		String baseName = FilenameUtils.getBaseName(tmpFile.getName());
		do
			tmpFile = new File(parentDir, baseName + "(" + count++ + ")." + extension);
		while (tmpFile.exists());
		return tmpFile;
	}

	public static void main(String[] args) {
		System.out.println(generateFilename("/base", "gif"));
	}
}

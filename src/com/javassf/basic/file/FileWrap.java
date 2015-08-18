package com.javassf.basic.file;

import java.io.File;
import java.io.FileFilter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.apache.commons.io.FilenameUtils;

/**
 * 静态文件工具类
 * @author wxj
 *
 */
public class FileWrap {
	public static final String[] EDITABLE_EXT = { "html", "htm", "css", "js", "txt" };
	private File file;
	private String rootPath;
	private FileFilter filter;
	private List<FileWrap> child;
	private String filename;

	public FileWrap(File file) {
		this(file, null);
	}

	public FileWrap(File file, String rootPath) {
		this(file, rootPath, null);
	}

	public FileWrap(File file, String rootPath, FileFilter filter) {
		this.file = file;
		this.rootPath = rootPath;
		this.filter = filter;
	}

	/**
	 * 判断文件是否是 html, htm", css, js, txt
	 * @param ext
	 * @return
	 */
	public static boolean editableExt(String ext) {
		ext = ext.toLowerCase(Locale.ENGLISH);
		for (String s : EDITABLE_EXT) {
			if (s.equals(ext)) {
				return true;
			}
		}
		return false;
	}

	public String getName() {
		String path = this.file.getAbsolutePath();
		String relPath = path.substring(this.rootPath.length());
		return relPath.replace(File.separator, "/");
	}

	public String getPath() {
		String name = getName();
		return name.substring(0, name.lastIndexOf('/'));
	}

	public String getFilename() {
		return this.filename != null ? this.filename : this.file.getName();
	}

	//获取后缀
	public String getExtension() {
		return FilenameUtils.getExtension(this.file.getName());
	}

	public long getLastModified() {
		return this.file.lastModified();
	}

	public Date getLastModifiedDate() {
		return new Timestamp(this.file.lastModified());
	}

	public long getSize() {
		return this.file.length() / 1024L + 1L;
	}

	//获取后缀
	public String getIco() {
		if (this.file.isDirectory()) {
			return "folder";
		}
		String ext = getExtension().toLowerCase();
		if ((ext.equals("jpg")) || (ext.equals("jpeg")))
			return "jpg";
		if (ext.equals("png"))
			return "png";
		if (ext.equals("gif"))
			return "gif";
		if ((ext.equals("html")) || (ext.equals("htm")))
			return "html";
		if (ext.equals("swf"))
			return "swf";
		if (ext.equals("txt")) {
			return "txt";
		}
		return "unknow";
	}

	public List<FileWrap> getChild() {
		if (this.child != null)
			return this.child;
		File[] files;
		// File[] files;
		if (this.filter == null)
			files = getFile().listFiles();
		else {
			files = getFile().listFiles(this.filter);
		}
		List list = new ArrayList();
		if (files != null) {
			Arrays.sort(files, new FileComparator());
			for (File f : files) {
				FileWrap fw = new FileWrap(f, this.rootPath, this.filter);
				list.add(fw);
			}
		}
		return list;
	}

	public File getFile() {
		return this.file;
	}

	public boolean isEditable() {
		if (isDirectory()) {
			return false;
		}
		String ext = getExtension().toLowerCase();
		for (String s : EDITABLE_EXT) {
			if (s.equals(ext)) {
				return true;
			}
		}
		return false;
	}

	public boolean isDirectory() {
		return this.file.isDirectory();
	}

	public boolean isFile() {
		return this.file.isFile();
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setChild(List<FileWrap> child) {
		this.child = child;
	}

	public static class FileComparator implements Comparator<File> {
		public int compare(File o1, File o2) {
			if ((o1.isDirectory()) && (!o2.isDirectory()))
				return -1;
			if ((!o1.isDirectory()) && (o2.isDirectory())) {
				return 1;
			}
			return o1.compareTo(o2);
		}
	}
}
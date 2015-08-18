package com.javassf.basic.upload;

import com.javassf.basic.utils.UploadUtils;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import javax.servlet.ServletContext;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

public class FileRepository implements ServletContextAware {
	private Logger log = LoggerFactory.getLogger(FileRepository.class);
	private ServletContext ctx;

	public String uploadFile(MultipartFile file, String uploadPath) {
		String origName = file.getOriginalFilename();
		String ext = FilenameUtils.getExtension(origName).toLowerCase(Locale.ENGLISH);
		String fileUrl = storeByExt(uploadPath, ext, file);
		return fileUrl;
	}

	private String storeByExt(String path, String ext, MultipartFile file) {
		String filename = UploadUtils.generateFilename(path, ext);
		File dest = new File(this.ctx.getRealPath(filename));
		dest = UploadUtils.getUniqueFile(dest);
		try {
			store(file, dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return filename;
	}

	public String storeByFilename(String filename, MultipartFile file) throws IOException {
		File dest = new File(this.ctx.getRealPath(filename));
		store(file, dest);
		return filename;
	}

	public String storeByExt(String path, String ext, File file) throws IOException {
		String filename = UploadUtils.generateFilename(path, ext);
		File dest = new File(this.ctx.getRealPath(filename));
		dest = UploadUtils.getUniqueFile(dest);
		store(file, dest);
		return filename;
	}

	public String storeByFilename(String filename, File file) throws IOException {
		File dest = new File(this.ctx.getRealPath(filename));
		store(file, dest);
		return filename;
	}

	private void store(MultipartFile file, File dest) throws IOException {
		try {
			UploadUtils.checkDirAndCreate(dest.getParentFile());
			file.transferTo(dest);
		} catch (IOException e) {
			this.log.error("Transfer file error when upload file", e);
			throw e;
		}
	}

	private void store(File file, File dest) throws IOException {
		try {
			UploadUtils.checkDirAndCreate(dest.getParentFile());
			FileUtils.copyFile(file, dest);
		} catch (IOException e) {
			this.log.error("Transfer file error when upload file", e);
			throw e;
		}
	}

	public File retrieve(String name) {
		return new File(this.ctx.getRealPath(name));
	}

	public void setServletContext(ServletContext servletContext) {
		this.ctx = servletContext;
	}
}

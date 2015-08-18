package com.javassf.basic.filters;

import com.ckfinder.connector.FileUploadFilter;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 
 * 继承上传文件过滤器
 *
 */
public class UserFileUploadFilter extends FileUploadFilter {
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		super.doFilter(request, response, chain);
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		super.init(fConfig);
	}
}
package com.javassf.basic.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * 该过滤器用统计执行一个jsp所耗的时间
 *
 */
public class ExecuteTimeFilter implements Filter {
	protected final Logger log = LoggerFactory.getLogger(ExecuteTimeFilter.class);
	public static final String START_TIME = "_start_time";

	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		//获取当前时间
		long time = System.currentTimeMillis();
		//设置开始时间
		request.setAttribute("_start_time", Long.valueOf(time));
		chain.doFilter(request, response);
		//计算执行时间
		time = System.currentTimeMillis() - time;
		//写入日志
		this.log.debug("process in {} ms: {}", Long.valueOf(time), request.getRequestURI());
	}

	public void init(FilterConfig arg0) throws ServletException {
	}
}

package com.javassf.basic.plugin.springmvc;

import java.util.Date;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;
/**
 * 自定义WebBindingInitializer属性编辑器
 * @author wxj
 *
 */
public class BindingInitializer implements WebBindingInitializer {
	public void initBinder(WebDataBinder binder, WebRequest request) {
		binder.registerCustomEditor(Date.class, new DateTypeEditor());
	}
}

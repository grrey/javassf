package com.javassf.basic.plugin.springmvc;

import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
/**
 * 对模型视图名称的解析，即在模型视图名称添加前后缀 通用解析器
 */
public class RichFreeMarkerViewResolver extends AbstractTemplateViewResolver {
	public RichFreeMarkerViewResolver() {
		setViewClass(RichFreeMarkerView.class);
	}

	protected AbstractUrlBasedView buildView(String viewName) throws Exception {
		AbstractUrlBasedView view = super.buildView(viewName);

		if (viewName.startsWith("/")) {
			view.setUrl(viewName + getSuffix());
		}
		return view;
	}
}

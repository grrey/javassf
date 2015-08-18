package com.javassf.basic.plugin.springmvc;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

public class RichFreeMarkerView extends FreeMarkerView {
	public static final String CONTEXT_PATH = "base";

	protected void exposeHelpers(Map model, HttpServletRequest request) throws Exception {
		super.exposeHelpers(model, request);
		model.put("base", request.getContextPath());
	}
}

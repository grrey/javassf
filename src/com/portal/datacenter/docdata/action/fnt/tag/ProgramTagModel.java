package com.portal.datacenter.docdata.action.fnt.tag;

import com.portal.datacenter.docdata.entity.ProgramDownload;
import com.portal.datacenter.docdata.service.ProgramDownloadService;
import freemarker.core.Environment;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import java.io.IOException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

public class ProgramTagModel implements TemplateDirectiveModel {

	@Autowired
	private ProgramDownloadService programDownloadService;

	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		ProgramDownload pd = this.programDownloadService.findUnique();
		Integer count = Integer.valueOf(0);
		if (pd != null) {
			count = pd.getCount();
		}
		env.setVariable("count", ObjectWrapper.DEFAULT_WRAPPER.wrap(count));
		body.render(env.getOut());
	}
}

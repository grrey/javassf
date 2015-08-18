package com.portal.datacenter.commdata.action.tag;

import com.portal.datacenter.commdata.service.MetierService;
import com.portal.sysmgr.entity.Site;
import com.portal.sysmgr.utils.TagModelTools;
import com.portal.sysmgr.utils.ViewTools;
import freemarker.core.Environment;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

public class MetierTagModel implements TemplateDirectiveModel {
	public static final String PARAM_PARENT_ID = "parentId";

	@Autowired
	private MetierService metierService;

	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Site site = ViewTools.getSite(env);
		Integer parentId = TagModelTools.getInt("parentId", params);
		List metierList = new ArrayList();
		if (parentId != null)
			metierList = this.metierService.getMetierChild(parentId);
		else {
			metierList = this.metierService.getMetierList(null);
		}
		env.setVariable("list", ObjectWrapper.DEFAULT_WRAPPER.wrap(metierList));
		body.render(env.getOut());
		ViewTools.includePagination(site, params, env);
	}
}

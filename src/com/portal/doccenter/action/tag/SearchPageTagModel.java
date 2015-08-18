package com.portal.doccenter.action.tag;

import com.portal.datacenter.lucene.LuceneDocPageService;
import com.portal.doccenter.action.tag.base.BaseSearchTagModel;
import com.portal.doccenter.entity.Article;
import com.portal.sysmgr.entity.Site;
import com.portal.sysmgr.utils.TagModelTools;
import com.portal.sysmgr.utils.ViewTools;
import freemarker.core.Environment;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

public class SearchPageTagModel extends BaseSearchTagModel {
	public static final String TPL_NAME = "tplName";

	@Autowired
	private LuceneDocPageService luceneDocPageService;

	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Site site = ViewTools.getSite(env);
		int pageNo = ViewTools.getPageNo(env);
		int count = ViewTools.getCount(params);
		String tplName = TagModelTools.getString("tplName", params);
		String query = getQuery(params);
		Integer channelId = getChannelId(params);
		Integer modelId = getModelId(params);
		Date startDate = getStartDate(params);
		Date endDate = getEndDate(params);
		Page<Article> page = this.luceneDocPageService.searchArticle(query, query, site.getId(), modelId, channelId, startDate, endDate, pageNo, count);
		env.setVariable("page", ObjectWrapper.DEFAULT_WRAPPER.wrap(page));
		if (!StringUtils.isBlank(tplName)) {
			ViewTools.includeTpl(tplName, site, env);
		} else if (body != null)
			body.render(env.getOut());
	}
}

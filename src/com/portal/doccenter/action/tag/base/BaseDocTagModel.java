package com.portal.doccenter.action.tag.base;

import com.portal.doccenter.service.ArticleService;
import com.portal.doccenter.service.ChannelService;
import com.portal.sysmgr.entity.Site;
import com.portal.sysmgr.service.SiteService;
import com.portal.sysmgr.utils.ContextTools;
import com.portal.sysmgr.utils.TagModelTools;
import com.portal.sysmgr.utils.ViewTools;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

public abstract class BaseDocTagModel implements TemplateDirectiveModel {
	public static final String PARAM_CHANNEL_ID = "cId";
	public static final String PARAM_MODEL_ID = "mId";
	public static final String PARAM_DEPART_ID = "dId";
	public static final String PARAM_USER_ID = "uId";
	public static final String PARAM_CALL_LEVEL = "callLevel";
	public static final String PARAM_TYPE_ID = "tId";
	public static final String PARAM_RECOMMEND = "recommend";
	public static final String PARAM_DATE = "date";
	public static final String PARAM_ORDER_BY = "orderBy";

	@Autowired
	protected ChannelService channelService;

	@Autowired
	protected SiteService siteService;

	@Autowired
	protected ArticleService articleService;

	protected Integer[] getChannelIds(Map<String, TemplateModel> params) throws TemplateException {
		Integer[] ids = TagModelTools.getIntArray("cId", params);
		if ((ids != null) && (ids.length > 0)) {
			return ids;
		}
		return null;
	}

	protected Integer getDepartId(Map<String, TemplateModel> params) throws TemplateException {
		return TagModelTools.getInt("dId", params);
	}

	protected Integer getUserId(Map<String, TemplateModel> params) throws TemplateException {
		return TagModelTools.getInt("uId", params);
	}

	protected Date getDate(Map<String, TemplateModel> params) throws TemplateException {
		return TagModelTools.getDate("date", params);
	}

	protected Integer[] getModelIds(Map<String, TemplateModel> params) throws TemplateException {
		Integer[] ids = TagModelTools.getIntArray("mId", params);
		if ((ids != null) && (ids.length > 0)) {
			return ids;
		}
		return null;
	}

	protected int getCallLevel(Map<String, TemplateModel> params) throws TemplateException {
		Integer callLevel = TagModelTools.getInt("callLevel", params);
		if ((callLevel == null) || (callLevel.intValue() < 0)) {
			return 0;
		}
		return callLevel.intValue();
	}

	protected Integer[] getTypeIds(Map<String, TemplateModel> params) throws TemplateException {
		Integer[] typeIds = TagModelTools.getIntArray("tId", params);
		return typeIds;
	}

	protected Boolean getRecommend(Map<String, TemplateModel> params) throws TemplateException {
		String recommend = TagModelTools.getString("recommend", params);
		if ("1".equals(recommend)) {
			return Boolean.valueOf(true);
		}
		return null;
	}

	protected int getOrderBy(Map<String, TemplateModel> params) throws TemplateException {
		Integer orderBy = TagModelTools.getInt("orderBy", params);
		if (orderBy == null) {
			return 0;
		}
		return orderBy.intValue();
	}

	protected Object getData(Map<String, TemplateModel> params, Environment env) throws TemplateException {
		int orderBy = getOrderBy(params);
		Boolean recommend = getRecommend(params);
		Integer siteId = ViewTools.getSite(env).getId();
		Integer departId = getDepartId(params);
		Integer userId = getUserId(params);
		Date date = getDate(params);
		Integer[] typeIds = getTypeIds(params);
		int count = ViewTools.getCount(params);

		Integer[] channelIds = getChannelIds(params);
		if (channelIds != null) {
			Integer[] modelIds = getModelIds(params);
			int callLevel = getCallLevel(params);
			if (isPage()) {
				int pageNo = ViewTools.getPageNo(env);
				Page p = this.articleService.getPageTagByChannelIds(channelIds, siteId, modelIds, typeIds, departId, userId, recommend, date, orderBy, callLevel, pageNo, count);
				ContextTools.setTotalPages(Integer.valueOf(p.getTotalPages()));
				return p;
			}
			int first = ViewTools.getFirst(params);
			return this.articleService.getListTagByChannelIds(channelIds, siteId, modelIds, typeIds, departId, userId, recommend, date, orderBy, callLevel, Integer.valueOf(first), Integer.valueOf(count));
		}

		Integer[] modelIds = getModelIds(params);
		if (modelIds != null) {
			if (isPage()) {
				int pageNo = ViewTools.getPageNo(env);
				Page p = this.articleService.getPageTagByModelIds(modelIds, typeIds, siteId, recommend, orderBy, pageNo, count);
				ContextTools.setTotalPages(Integer.valueOf(p.getTotalPages()));
				return p;
			}
			int first = ViewTools.getFirst(params);
			return this.articleService.getListTagByModelIds(modelIds, typeIds, siteId, recommend, orderBy, Integer.valueOf(first), Integer.valueOf(count));
		}

		if (isPage()) {
			int pageNo = ViewTools.getPageNo(env);
			Page p = this.articleService.getPageTagByChannelIds(null, siteId, null, typeIds, departId, userId, recommend, date, orderBy, 0, pageNo, count);
			ContextTools.setTotalPages(Integer.valueOf(p.getTotalPages()));
			return p;
		}
		int first = ViewTools.getFirst(params);
		return this.articleService.getListTagByChannelIds(null, siteId, null, typeIds, departId, userId, recommend, date, orderBy, 0, Integer.valueOf(first), Integer.valueOf(count));
	}

	protected abstract boolean isPage();
}

package com.portal.doccenter.action.tag.base;

import com.portal.sysmgr.utils.TagModelTools;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import java.util.Date;
import java.util.Map;

public abstract class BaseSearchTagModel implements TemplateDirectiveModel {
	public static final String PARAM_QUERY = "q";
	public static final String PARAM_MODEL_ID = "mId";
	public static final String PARAM_CHANNEL_ID = "cId";
	public static final String PARAM_START_DATE = "startDate";
	public static final String PARAM_END_DATE = "endDate";

	protected String getQuery(Map<String, TemplateModel> params) throws TemplateException {
		return TagModelTools.getString("q", params);
	}

	protected Integer getModelId(Map<String, TemplateModel> params) throws TemplateException {
		return TagModelTools.getInt("mId", params);
	}

	protected Integer getChannelId(Map<String, TemplateModel> params) throws TemplateException {
		return TagModelTools.getInt("cId", params);
	}

	protected Date getStartDate(Map<String, TemplateModel> params) throws TemplateException {
		return TagModelTools.getDate("startDate", params);
	}

	protected Date getEndDate(Map<String, TemplateModel> params) throws TemplateException {
		return TagModelTools.getDate("endDate", params);
	}
}

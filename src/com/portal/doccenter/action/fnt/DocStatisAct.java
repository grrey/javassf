package com.portal.doccenter.action.fnt;

import com.portal.doccenter.action.fnt.cache.DocViewsCountCache;
import com.portal.extrafunc.action.cache.CommentUpCache;
import org.springframework.beans.factory.annotation.Autowired;

public class DocStatisAct {

	@Autowired
	private DocViewsCountCache viewsCountCache;

	@Autowired
	private CommentUpCache commentUpCache;

	public void docViewsCount() {
		this.viewsCountCache.viewsToDB();
	}

	public void upsCount() {
		this.commentUpCache.upsToDB();
	}
}

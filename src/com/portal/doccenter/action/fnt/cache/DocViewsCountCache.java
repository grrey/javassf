package com.portal.doccenter.action.fnt.cache;

public abstract interface DocViewsCountCache {
	public abstract Integer viewsCount(Integer paramInteger);

	public abstract void viewsToDB();
}

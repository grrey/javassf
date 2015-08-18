package com.portal.datacenter.lucene;

import com.portal.doccenter.entity.Article;
import java.io.IOException;
import java.util.Date;

public abstract interface LuceneDocService {
	public abstract Integer createSearchIndex(Integer paramInteger1, Integer paramInteger2, Date paramDate1, Date paramDate2, Integer paramInteger3, Integer paramInteger4, boolean paramBoolean) throws IOException;

	public abstract void deleteSearchIndex(Integer paramInteger1, Integer paramInteger2);

	public abstract void updateChannel(Integer paramInteger1, Integer paramInteger2);

	public abstract void addDoc(Article paramArticle);

	public abstract void updateDoc(Article paramArticle);

	public abstract void deleteDoc(Integer paramInteger);
}
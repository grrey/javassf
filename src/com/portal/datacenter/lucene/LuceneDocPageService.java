package com.portal.datacenter.lucene;

import com.portal.doccenter.entity.Article;
import java.util.Date;
import org.springframework.data.domain.Page;

public abstract interface LuceneDocPageService {
	public abstract Page<Article> searchArticle(String paramString1, String paramString2, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Date paramDate1, Date paramDate2, int paramInt1, int paramInt2);
}

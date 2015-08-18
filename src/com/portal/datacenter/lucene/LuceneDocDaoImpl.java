package com.portal.datacenter.lucene;

import com.javassf.basic.hibernate4.QueryDaoImpl;
import com.portal.doccenter.entity.Article;
import java.io.IOException;
import java.util.Date;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.index.CorruptIndexException;
import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class LuceneDocDaoImpl extends QueryDaoImpl<Article, Integer> implements LuceneDocDao {
	public Integer index(NRTManagerService service, Integer siteId, String number, Date startDate, Date endDate, Integer startId, Integer max) throws CorruptIndexException, IOException {
		Criteria crit = createCriteria();
		crit.createAlias("channel", "c");
		if (!StringUtils.isBlank(number))
			crit.add(Restrictions.like("c.number", number + "%"));
		else if (siteId != null) {
			crit.add(Restrictions.eq("site.id", siteId));
		}
		if (startId != null) {
			crit.add(Restrictions.gt("id", startId));
		}
		if (startDate != null) {
			crit.add(Restrictions.ge("releaseDate", startDate));
		}
		if (endDate != null) {
			crit.add(Restrictions.le("releaseDate", endDate));
		}
		crit.add(Restrictions.eq("status", Byte.valueOf((byte) 2)));
		crit.addOrder(Order.asc("id"));
		if (max != null) {
			crit.setMaxResults(max.intValue());
		}
		Session session = getSession();
		ScrollableResults articles = crit.setCacheMode(CacheMode.IGNORE).scroll(ScrollMode.FORWARD_ONLY);
		int count = 0;
		Article doc = null;
		while (articles.next()) {
			doc = (Article) articles.get(0);
			service.addDoc(LuceneCommon.createDoc(doc));
			count++;
			if (count % 20 == 0) {
				session.clear();
			}
		}
		if ((count < max.intValue()) || (doc == null)) {
			return null;
		}
		return doc.getId();
	}

	protected Class<Article> getEntityClass() {
		return Article.class;
	}
}
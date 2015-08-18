 package com.portal.doccenter.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.doccenter.dao.ArticleQueryDao;
 import com.portal.doccenter.entity.Article;
 import org.apache.commons.lang.StringUtils;
 import org.hibernate.CacheMode;
 import org.hibernate.Criteria;
 import org.hibernate.ScrollMode;
 import org.hibernate.ScrollableResults;
 import org.hibernate.Session;
 import org.hibernate.criterion.Restrictions;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class ArticleQueryDaoImpl extends QueryDaoImpl<Article, Integer>
   implements ArticleQueryDao
 {
   public int emptyArticlePage(String treeNumber)
   {
     Criteria crit = createCriteria();
     if (!StringUtils.isBlank(treeNumber)) {
       crit.createAlias("channel", "c");
       crit.add(Restrictions.like("c.number", treeNumber + "%"));
     }
     Session session = getSession();
     ScrollableResults articles = crit.setCacheMode(CacheMode.IGNORE)
       .scroll(ScrollMode.FORWARD_ONLY);
 
     int count = 0;
     while (articles.next()) {
       Article article = (Article)articles.get(0);
       session.delete(article);
 
       count++; if (count % 20 == 0) {
         session.clear();
       }
     }
     return count;
   }
 
   protected Class<Article> getEntityClass()
   {
     return Article.class;
   }
 }


 
 
 
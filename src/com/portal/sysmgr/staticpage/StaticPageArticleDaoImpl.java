 package com.portal.sysmgr.staticpage;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.datacenter.docdata.service.KeywordService;
 import com.portal.doccenter.entity.Article;
 import freemarker.template.Configuration;
 import freemarker.template.TemplateException;
 import java.io.IOException;
 import javax.servlet.ServletContext;
 import org.apache.commons.lang.StringUtils;
 import org.hibernate.CacheMode;
 import org.hibernate.Criteria;
 import org.hibernate.ScrollMode;
 import org.hibernate.ScrollableResults;
 import org.hibernate.Session;
 import org.hibernate.criterion.Restrictions;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class StaticPageArticleDaoImpl extends QueryDaoImpl<Article, Integer>
   implements StaticPageArticleDao
 {
   public String staticArticlePage(String treeNumber, Configuration config, ServletContext ctx, KeywordService keywordService)
     throws IOException, TemplateException
   {
     Criteria crit = createCriteria();
     if (!StringUtils.isBlank(treeNumber)) {
       crit.createAlias("channel", "c");
       crit.add(Restrictions.like("c.number", treeNumber + "%"));
     }
     crit.add(Restrictions.eq("status", Byte.valueOf((byte) 2)));
     Session session = getSession();
     ScrollableResults articles = crit.setCacheMode(CacheMode.IGNORE)
       .scroll(ScrollMode.FORWARD_ONLY);
 
     int count = 0; int counts = 0; int countn = 0; int countf = 0;
     while (articles.next()) {
       Article article = (Article)articles.get(0);
       Integer i = StaticArticle.staticArticle(article, config, ctx, 
         keywordService);
       if (i == null)
         counts++;
       else if (i.intValue() == 0)
         countn++;
       else {
         countf++;
       }
       count++; if (count % 20 == 0) {
         session.clear();
       }
     }
     StringBuffer sb = new StringBuffer();
     sb.append("成功生成静态页信息");
     sb.append(counts).append("篇,");
     sb.append("生成失败").append(countf).append("篇,");
     sb.append("未生成静态页").append(countn).append("篇!");
     return sb.toString();
   }
 
   protected Class<Article> getEntityClass() {
     return Article.class;
   }
 }


 
 
 
 package com.portal.doccenter.service.impl;
 
 import com.portal.doccenter.dao.ArticleExtDao;
 import com.portal.doccenter.entity.Article;
 import com.portal.doccenter.entity.ArticleExt;
 import com.portal.doccenter.service.ArticleExtService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class ArticleExtServiceImpl
   implements ArticleExtService
 {
   private ArticleExtDao dao;
 
   public ArticleExt save(ArticleExt ext, Article article)
   {
     article.setArticleExt(ext);
     ext.setArticle(article);
     ext.init();
     this.dao.save(ext);
     article.setArticleExt(ext);
     return ext;
   }
 
   public ArticleExt update(ArticleExt bean) {
     bean = (ArticleExt)this.dao.update(bean);
     return bean;
   }
 
   public int delDocByInputUser(Integer userId) {
     return this.dao.delDocByInputUser(userId);
   }
 
   public int delDocBySite(Integer siteId) {
     return this.dao.delDocBySite(siteId);
   }
 
   @Autowired
   public void setDao(ArticleExtDao dao)
   {
     this.dao = dao;
   }
 }


 
 
 
 package com.portal.doccenter.service.impl;
 
 import com.portal.doccenter.dao.ArticleTxtDao;
 import com.portal.doccenter.entity.Article;
 import com.portal.doccenter.entity.ArticleTxt;
 import com.portal.doccenter.service.ArticleTxtService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class ArticleTxtServiceImpl
   implements ArticleTxtService
 {
   private ArticleTxtDao dao;
 
   public ArticleTxt save(ArticleTxt txt, Article article)
   {
     if (txt.isAllBlank()) {
       return null;
     }
     txt.setArticle(article);
     txt.init();
     this.dao.save(txt);
     article.setArticleTxt(txt);
     return txt;
   }
 
   public ArticleTxt update(ArticleTxt txt, Article article)
   {
     ArticleTxt entity = (ArticleTxt)this.dao.findById(article.getId());
     if (entity == null) {
       entity = save(txt, article);
       return entity;
     }
     entity = (ArticleTxt)this.dao.update(txt);
     entity.blankToNull();
     return entity;
   }
 
   public int delDocByInputUser(Integer userId)
   {
     return this.dao.delDocByInputUser(userId);
   }
 
   public int delDocBySite(Integer siteId) {
     return this.dao.delDocBySite(siteId);
   }
 
   @Autowired
   public void setDao(ArticleTxtDao dao)
   {
     this.dao = dao;
   }
 }


 
 
 
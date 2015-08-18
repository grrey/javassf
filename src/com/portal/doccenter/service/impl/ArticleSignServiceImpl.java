 package com.portal.doccenter.service.impl;
 
 import com.portal.doccenter.dao.ArticleSignDao;
 import com.portal.doccenter.entity.Article;
 import com.portal.doccenter.entity.ArticleSign;
 import com.portal.doccenter.service.ArticleService;
 import com.portal.doccenter.service.ArticleSignService;
 import com.portal.sysmgr.entity.Site;
 import com.portal.usermgr.entity.Admin;
 import com.portal.usermgr.entity.Depart;
 import com.portal.usermgr.entity.User;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class ArticleSignServiceImpl
   implements ArticleSignService
 {
   private ArticleSignDao dao;
   private ArticleService articleService;
 
   @Transactional(readOnly=true)
   public Page<ArticleSign> getPage(int pageNo, int pageSize)
   {
     return this.dao.getPage(pageNo, pageSize);
   }
   @Transactional(readOnly=true)
   public ArticleSign findById(Integer id) {
     ArticleSign entity = (ArticleSign)this.dao.findById(id);
     return entity;
   }
 
   public ArticleSign save(Integer articleId, User user) {
     ArticleSign sign = new ArticleSign();
     Article a = this.articleService.findById(articleId);
     if (a != null) {
       Admin admin = user.getAdmin();
       if ((admin != null) && 
         (!a.getSign(user))) {
         Depart d = admin.getDepart(a.getSite().getId());
         sign.setAdmin(admin);
         sign.setArticle(a);
         sign.setDepart(d);
         sign.init();
         this.dao.save(sign);
         a.addToSigns(sign);
         return sign;
       }
     }
 
     return null;
   }
 
   public ArticleSign update(ArticleSign bean) {
     bean = (ArticleSign)this.dao.update(bean);
     return bean;
   }
 
   public ArticleSign deleteById(Integer id) {
     ArticleSign bean = (ArticleSign)this.dao.deleteById(id);
     return bean;
   }
 
   public ArticleSign[] deleteByIds(Integer[] ids) {
     ArticleSign[] beans = new ArticleSign[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   @Autowired
   public void setDao(ArticleSignDao dao)
   {
     this.dao = dao;
   }
   @Autowired
   public void setArticleService(ArticleService articleService) {
     this.articleService = articleService;
   }
 }


 
 
 
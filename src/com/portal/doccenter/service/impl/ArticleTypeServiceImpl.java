 package com.portal.doccenter.service.impl;
 
 import com.portal.doccenter.dao.ArticleTypeDao;
 import com.portal.doccenter.entity.ArticleType;
 import com.portal.doccenter.service.ArticleTypeService;
 import java.util.List;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class ArticleTypeServiceImpl
   implements ArticleTypeService
 {
   private ArticleTypeDao dao;
 
   @Transactional(readOnly=true)
   public List<ArticleType> getList(boolean containDisabled, String sortname, String sortorder)
   {
     return this.dao.getList(containDisabled, sortname, sortorder);
   }
   @Transactional(readOnly=true)
   public ArticleType getDef() {
     return this.dao.getDef();
   }
   @Transactional(readOnly=true)
   public ArticleType findById(Integer id) {
     ArticleType entity = (ArticleType)this.dao.findById(id);
     return entity;
   }
 
   public ArticleType save(ArticleType bean) {
     this.dao.save(bean);
     return bean;
   }
 
   public ArticleType update(ArticleType bean) {
     ArticleType entity = (ArticleType)this.dao.update(bean);
     return entity;
   }
 
   public ArticleType deleteById(Integer id) {
     ArticleType bean = (ArticleType)this.dao.deleteById(id);
     return bean;
   }
 
   public ArticleType[] deleteByIds(Integer[] ids) {
     ArticleType[] beans = new ArticleType[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   @Autowired
   public void setDao(ArticleTypeDao dao)
   {
     this.dao = dao;
   }
 }


 
 
 
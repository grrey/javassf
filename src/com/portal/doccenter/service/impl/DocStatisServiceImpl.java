 package com.portal.doccenter.service.impl;
 
 import com.portal.doccenter.dao.DocStatisDao;
 import com.portal.doccenter.entity.Article;
 import com.portal.doccenter.entity.DocStatis;
 import com.portal.doccenter.service.DocStatisService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class DocStatisServiceImpl
   implements DocStatisService
 {
   private DocStatisDao dao;
 
   public DocStatis save(Article article)
   {
     DocStatis statis = new DocStatis();
     statis.setDoc(article);
     statis.init();
     this.dao.save(statis);
     article.setDocStatis(statis);
     return statis;
   }
 
   public DocStatis save(Article article, DocStatis statis) {
     statis.setDoc(article);
     statis.init();
     this.dao.save(statis);
     article.setDocStatis(statis);
     return statis;
   }
 
   public DocStatis update(Integer statisId, Integer viewsCount) {
     DocStatis entity = (DocStatis)this.dao.findById(statisId);
     entity.setViewsCount(viewsCount);
     return entity;
   }
   @Transactional(readOnly=true)
   public DocStatis findById(Integer id) {
     DocStatis entity = (DocStatis)this.dao.findById(id);
     return entity;
   }
 
   public DocStatis ups(Integer id) {
     DocStatis entity = (DocStatis)this.dao.findById(id);
     if (entity.getUps() != null)
       entity.setUps(Integer.valueOf(entity.getUps().intValue() + 1));
     else {
       entity.setUps(Integer.valueOf(1));
     }
     return entity;
   }
 
   public DocStatis treads(Integer id) {
     DocStatis entity = (DocStatis)this.dao.findById(id);
     if (entity.getTreads() != null)
       entity.setTreads(Integer.valueOf(entity.getTreads().intValue() + 1));
     else {
       entity.setTreads(Integer.valueOf(1));
     }
     return entity;
   }
 
   @Autowired
   public void setDao(DocStatisDao dao)
   {
     this.dao = dao;
   }
 }


 
 
 
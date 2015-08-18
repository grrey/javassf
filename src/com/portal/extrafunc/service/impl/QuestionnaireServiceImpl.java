 package com.portal.extrafunc.service.impl;
 
 import com.portal.extrafunc.dao.QuestionnaireDao;
 import com.portal.extrafunc.entity.Questionnaire;
 import com.portal.extrafunc.service.QuestionnaireService;
 import com.portal.sysmgr.entity.Site;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class QuestionnaireServiceImpl
   implements QuestionnaireService
 {
   private QuestionnaireDao dao;
 
   @Transactional(readOnly=true)
   public Page<Questionnaire> getPage(Integer siteId, boolean enable, int pageNo, int pageSize)
   {
     return this.dao.getPage(siteId, enable, pageNo, pageSize);
   }
   @Transactional(readOnly=true)
   public Questionnaire findById(Integer id) {
     Questionnaire entity = (Questionnaire)this.dao.findById(id);
     return entity;
   }
 
   public Questionnaire save(Questionnaire bean, Site site) {
     bean.setSite(site);
     bean.init();
     this.dao.save(bean);
     return bean;
   }
 
   public Questionnaire update(Questionnaire bean) {
     bean.updateinit();
     bean = (Questionnaire)this.dao.update(bean);
     return bean;
   }
 
   public Questionnaire deleteById(Integer id) {
     Questionnaire bean = (Questionnaire)this.dao.deleteById(id);
     return bean;
   }
 
   public Questionnaire[] deleteByIds(Integer[] ids) {
     Questionnaire[] beans = new Questionnaire[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   @Autowired
   public void setDao(QuestionnaireDao dao)
   {
     this.dao = dao;
   }
 }


 
 
 
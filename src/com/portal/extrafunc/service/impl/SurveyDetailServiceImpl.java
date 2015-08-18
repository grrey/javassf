 package com.portal.extrafunc.service.impl;
 
 import com.portal.extrafunc.dao.SurveyDetailDao;
 import com.portal.extrafunc.entity.SurveyDetail;
 import com.portal.extrafunc.entity.SurveyTheme;
 import com.portal.extrafunc.service.SurveyDetailService;
 import com.portal.usermgr.entity.User;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class SurveyDetailServiceImpl
   implements SurveyDetailService
 {
   private SurveyDetailDao dao;
 
   @Transactional(readOnly=true)
   public Page<SurveyDetail> getPage(Integer surveyId, int pageNo, int pageSize)
   {
     return this.dao.getPage(surveyId, pageNo, pageSize);
   }
   @Transactional(readOnly=true)
   public SurveyDetail findById(Integer id) {
     SurveyDetail entity = (SurveyDetail)this.dao.findById(id);
     return entity;
   }
 
   public SurveyDetail save(String content, SurveyTheme st, User user) {
     SurveyDetail bean = new SurveyDetail();
     bean.setSurvey(st);
     bean.setUser(user);
     bean.setContent(content);
     bean.init();
     this.dao.save(bean);
     return bean;
   }
 
   public SurveyDetail update(SurveyDetail bean) {
     bean = (SurveyDetail)this.dao.update(bean);
     return bean;
   }
 
   public SurveyDetail deleteById(Integer id) {
     SurveyDetail bean = (SurveyDetail)this.dao.deleteById(id);
     return bean;
   }
 
   public SurveyDetail[] deleteByIds(Integer[] ids) {
     SurveyDetail[] beans = new SurveyDetail[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   @Autowired
   public void setDao(SurveyDetailDao dao)
   {
     this.dao = dao;
   }
 }


 
 
 
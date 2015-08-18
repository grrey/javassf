 package com.portal.extrafunc.service.impl;
 
 import com.portal.extrafunc.dao.QuestionDetailDao;
 import com.portal.extrafunc.entity.QuestionDetail;
 import com.portal.extrafunc.entity.Questionnaire;
 import com.portal.extrafunc.service.QuestionDetailService;
 import com.portal.usermgr.entity.User;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class QuestionDetailServiceImpl
   implements QuestionDetailService
 {
   private QuestionDetailDao dao;
 
   @Transactional(readOnly=true)
   public Page<QuestionDetail> getPage(Integer questionId, int pageNo, int pageSize)
   {
     return this.dao.getPage(questionId, pageNo, pageSize);
   }
 
   @Transactional(readOnly=true)
   public QuestionDetail getDetail(Integer questionId, Integer userId, String ip) {
     return this.dao.getDetail(questionId, userId, ip);
   }
   @Transactional(readOnly=true)
   public long getCountDetail(Integer questionId) {
     return this.dao.getCountDetail(questionId);
   }
   @Transactional(readOnly=true)
   public QuestionDetail findById(Integer id) {
     QuestionDetail entity = (QuestionDetail)this.dao.findById(id);
     return entity;
   }
 
   public QuestionDetail save(Questionnaire question, User user, String ip) {
     QuestionDetail bean = new QuestionDetail();
     bean.setQuestion(question);
     bean.setUser(user);
     bean.setIp(ip);
     bean.init();
     this.dao.save(bean);
     return bean;
   }
 
   public QuestionDetail update(QuestionDetail bean) {
     bean = (QuestionDetail)this.dao.update(bean);
     return bean;
   }
 
   public QuestionDetail deleteById(Integer id) {
     QuestionDetail bean = (QuestionDetail)this.dao.deleteById(id);
     return bean;
   }
 
   public QuestionDetail[] deleteByIds(Integer[] ids) {
     QuestionDetail[] beans = new QuestionDetail[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   @Autowired
   public void setDao(QuestionDetailDao dao)
   {
     this.dao = dao;
   }
 }


 
 
 
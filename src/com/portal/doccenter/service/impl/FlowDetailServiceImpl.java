 package com.portal.doccenter.service.impl;
 
 import com.portal.doccenter.dao.FlowDetailDao;
 import com.portal.doccenter.entity.Article;
 import com.portal.doccenter.entity.FlowDetail;
 import com.portal.doccenter.service.FlowDetailService;
 import com.portal.usermgr.entity.Role;
 import com.portal.usermgr.entity.User;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class FlowDetailServiceImpl
   implements FlowDetailService
 {
   private FlowDetailDao dao;
 
   @Transactional(readOnly=true)
   public Page<FlowDetail> getPage(int pageNo, int pageSize)
   {
     return this.dao.getPage(pageNo, pageSize);
   }
   @Transactional(readOnly=true)
   public FlowDetail getLastFlowDetail(Integer docId) {
     return this.dao.getLastFlowDetail(docId);
   }
   @Transactional(readOnly=true)
   public FlowDetail findById(Integer id) {
     FlowDetail entity = (FlowDetail)this.dao.findById(id);
     return entity;
   }
 
   public FlowDetail saveCheck(Article doc, User user, Role role) {
     FlowDetail bean = new FlowDetail();
     bean.setChecked(Boolean.valueOf(true));
     bean.setDoc(doc);
     bean.setUser(user);
     bean.setRole(role);
     FlowDetail fd = getLastFlowDetail(doc.getId());
     if (fd != null) {
       bean.setPriority(Integer.valueOf(fd.getPriority().intValue() + 1));
     }
     bean.init();
     this.dao.save(bean);
     return bean;
   }
 
   public FlowDetail saveReturn(Article doc, User user, Role role, String reason, boolean initial)
   {
     FlowDetail bean = new FlowDetail();
     bean.setChecked(Boolean.valueOf(false));
     bean.setBackInitial(Boolean.valueOf(initial));
     bean.setReason(reason);
     bean.setDoc(doc);
     bean.setUser(user);
     bean.setRole(role);
     FlowDetail fd = getLastFlowDetail(doc.getId());
     if (fd != null) {
       bean.setPriority(Integer.valueOf(fd.getPriority().intValue() + 1));
     }
     bean.init();
     this.dao.save(bean);
     return bean;
   }
 
   public FlowDetail update(FlowDetail bean) {
     bean = (FlowDetail)this.dao.update(bean);
     return bean;
   }
 
   public FlowDetail deleteById(Integer id) {
     FlowDetail bean = (FlowDetail)this.dao.deleteById(id);
     return bean;
   }
 
   public FlowDetail[] deleteByIds(Integer[] ids) {
     FlowDetail[] beans = new FlowDetail[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   @Autowired
   public void setDao(FlowDetailDao dao)
   {
     this.dao = dao;
   }
 }


 
 
 
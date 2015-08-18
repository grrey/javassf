 package com.portal.doccenter.service.impl;
 
 import com.portal.doccenter.dao.FlowStepDao;
 import com.portal.doccenter.entity.FlowStep;
 import com.portal.doccenter.entity.WorkFlow;
 import com.portal.doccenter.service.FlowStepService;
 import com.portal.usermgr.service.RoleService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class FlowStepServiceImpl
   implements FlowStepService
 {
   private FlowStepDao dao;
   private RoleService roleService;
 
   @Transactional(readOnly=true)
   public Page<FlowStep> getPage(int pageNo, int pageSize)
   {
     return this.dao.getPage(pageNo, pageSize);
   }
   @Transactional(readOnly=true)
   public FlowStep findById(Integer id) {
     FlowStep entity = (FlowStep)this.dao.findById(id);
     return entity;
   }
 
   public void save(WorkFlow flow, Integer[] step, Integer[] roleIds) {
     for (int i = 0; i < step.length; i++) {
       FlowStep fl = new FlowStep();
       fl.setFlow(flow);
       fl.setRole(this.roleService.findById(roleIds[i]));
       fl.setStep(Integer.valueOf(i + 1));
       this.dao.save(fl);
     }
   }
 
   public void update(WorkFlow flow, Integer[] step, Integer[] roleIds) {
     for (FlowStep fl : flow.getFlowSteps()) {
       deleteById(fl.getId());
     }
     for (int i = 0; i < step.length; i++) {
       FlowStep fl = new FlowStep();
       fl.setFlow(flow);
       fl.setRole(this.roleService.findById(roleIds[i]));
       fl.setStep(Integer.valueOf(i + 1));
       this.dao.save(fl);
     }
   }
 
   public FlowStep deleteById(Integer id) {
     FlowStep bean = (FlowStep)this.dao.deleteById(id);
     return bean;
   }
 
   public FlowStep[] deleteByIds(Integer[] ids) {
     FlowStep[] beans = new FlowStep[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   @Autowired
   public void setDao(FlowStepDao dao)
   {
     this.dao = dao;
   }
   @Autowired
   public void setRoleService(RoleService roleService) {
     this.roleService = roleService;
   }
 }


 
 
 
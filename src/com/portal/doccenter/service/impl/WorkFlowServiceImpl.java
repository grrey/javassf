 package com.portal.doccenter.service.impl;
 
 import com.portal.doccenter.dao.WorkFlowDao;
 import com.portal.doccenter.entity.WorkFlow;
 import com.portal.doccenter.service.FlowStepService;
 import com.portal.doccenter.service.WorkFlowService;
 import com.portal.sysmgr.entity.Site;
 import java.util.List;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class WorkFlowServiceImpl
   implements WorkFlowService
 {
   private WorkFlowDao dao;
   private FlowStepService flowStepService;
 
   @Transactional(readOnly=true)
   public Page<WorkFlow> getPage(Integer siteId, String sortname, String sortorder, int pageNo, int pageSize)
   {
     return this.dao.getPage(siteId, sortname, sortorder, pageNo, 
       pageSize);
   }
   @Transactional(readOnly=true)
   public List<WorkFlow> findByList(Integer siteId) {
     return this.dao.findByList(siteId);
   }
   @Transactional(readOnly=true)
   public WorkFlow findById(Integer id) {
     WorkFlow entity = (WorkFlow)this.dao.findById(id);
     return entity;
   }
 
   public WorkFlow save(WorkFlow bean, Site site, Integer[] step, Integer[] roleIds)
   {
     bean.init();
     bean.setSite(site);
     bean.setStepCount(Integer.valueOf(step.length));
     this.dao.save(bean);
     this.flowStepService.save(bean, step, roleIds);
     return bean;
   }
 
   public WorkFlow update(WorkFlow bean, Integer[] step, Integer[] roleIds) {
     bean.setStepCount(Integer.valueOf(step.length));
     bean = (WorkFlow)this.dao.update(bean);
     this.flowStepService.update(bean, step, roleIds);
     return bean;
   }
 
   public WorkFlow deleteById(Integer id) {
     WorkFlow bean = (WorkFlow)this.dao.deleteById(id);
     return bean;
   }
 
   public WorkFlow[] deleteByIds(Integer[] ids) {
     WorkFlow[] beans = new WorkFlow[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   @Autowired
   public void setDao(WorkFlowDao dao)
   {
     this.dao = dao;
   }
   @Autowired
   public void setFlowStepService(FlowStepService flowStepService) {
     this.flowStepService = flowStepService;
   }
 }


 
 
 
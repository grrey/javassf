 package com.portal.usermgr.service.impl;
 
 import com.portal.doccenter.service.ChannelService;
 import com.portal.sysmgr.entity.Site;
 import com.portal.usermgr.dao.AdminCheckDao;
 import com.portal.usermgr.entity.Admin;
 import com.portal.usermgr.entity.AdminCheck;
 import com.portal.usermgr.service.AdminCheckService;
 import java.util.Set;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class AdminCheckServiceImpl
   implements AdminCheckService
 {
   private AdminCheckDao dao;
   private ChannelService channelService;
 
   @Transactional(readOnly=true)
   public AdminCheck findById(Integer id)
   {
     AdminCheck entity = (AdminCheck)this.dao.findById(id);
     return entity;
   }
 
   public AdminCheck save(Site site, Admin admin, Byte manageStatus, Boolean takeDepart, Integer[] channelIds)
   {
     AdminCheck bean = new AdminCheck();
     bean.setSite(site);
     bean.setAdmin(admin);
     bean.setManageStatus(manageStatus);
     if ((!takeDepart.booleanValue()) && 
       (channelIds != null)) {
       for (Integer cid : channelIds) {
         bean.addToChannels(this.channelService.findById(cid));
       }
     }
 
     bean.setTakeDepart(takeDepart);
     this.dao.save(bean);
     admin.addToAdminChecks(bean);
     return bean;
   }
 
   public AdminCheck update(AdminCheck bean) {
     bean = (AdminCheck)this.dao.update(bean);
     return bean;
   }
 
   public void updateByAdmin(Admin admin, Site site, Byte manageStatus, Boolean takeDepart, Integer[] channelIds)
   {
     Set<AdminCheck> ass = admin.getAdminChecks();
     if ((site == null) || (manageStatus == null)) {
       return;
     }
     if (ass == null)
       save(site, admin, manageStatus, takeDepart, channelIds);
     else
       for (AdminCheck as : ass)
         if (site.getId().equals(as.getSite().getId())) {
           as.setManageStatus(manageStatus);
           as.setTakeDepart(takeDepart);
           as.getChannels().clear();
           if ((takeDepart.booleanValue()) || 
             (channelIds == null)) continue;
           for (Integer cid : channelIds)
             as.addToChannels(this.channelService.findById(cid));
         }
   }
 
   public int deleteBySiteId(Integer siteId)
   {
     return this.dao.deleteBySiteId(siteId);
   }
 
   public AdminCheck deleteById(Integer id) {
     AdminCheck bean = (AdminCheck)this.dao.deleteById(id);
     bean.getChannels().clear();
     return bean;
   }
 
   public AdminCheck[] deleteByIds(Integer[] ids) {
     AdminCheck[] beans = new AdminCheck[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   @Autowired
   public void setDao(AdminCheckDao dao)
   {
     this.dao = dao;
   }
   @Autowired
   public void setChannelService(ChannelService channelService) {
     this.channelService = channelService;
   }
 }


 
 
 
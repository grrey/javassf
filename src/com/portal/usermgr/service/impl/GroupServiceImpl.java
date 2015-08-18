 package com.portal.usermgr.service.impl;
 
 import com.portal.sysmgr.entity.Site;
 import com.portal.usermgr.dao.GroupDao;
 import com.portal.usermgr.entity.Group;
 import com.portal.usermgr.entity.GroupPerm;
 import com.portal.usermgr.service.GroupPermService;
 import com.portal.usermgr.service.GroupService;
 import java.util.List;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class GroupServiceImpl
   implements GroupService
 {
   private GroupDao dao;
   private GroupPermService groupPermService;
 
   @Transactional(readOnly=true)
   public List<Group> getList(Integer siteId, String sortname, String sortorder)
   {
     return this.dao.getList(siteId, sortname, sortorder);
   }
   @Transactional(readOnly=true)
   public Group findById(Integer id) {
     Group entity = (Group)this.dao.findById(id);
     return entity;
   }
 
   public Group saveGroup(Group group, GroupPerm groupPerm, Site site) {
     group.setSite(site);
     save(group);
     this.groupPermService.save(group, groupPerm);
     return group;
   }
 
   public Group updateGroup(Group group, GroupPerm groupPerm) {
     update(group);
     this.groupPermService.update(groupPerm);
     return group;
   }
 
   public Group save(Group bean) {
     bean.init();
     this.dao.save(bean);
     return bean;
   }
 
   public Group update(Group bean) {
     bean = (Group)this.dao.update(bean);
     return bean;
   }
 
   public Group deleteById(Integer id) {
     Group bean = (Group)this.dao.deleteById(id);
     return bean;
   }
 
   public Group[] deleteByIds(Integer[] ids) {
     Group[] beans = new Group[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   public Group[] updatePriority(Integer[] ids, Integer[] priority) {
     int len = ids.length;
     Group[] beans = new Group[len];
     for (int i = 0; i < len; i++) {
       beans[i] = findById(ids[i]);
       beans[i].setPriority(priority[i]);
     }
     return beans;
   }
 
   @Autowired
   public void setDao(GroupDao dao)
   {
     this.dao = dao;
   }
   @Autowired
   public void setGroupPermService(GroupPermService groupPermService) {
     this.groupPermService = groupPermService;
   }
 }


 
 
 
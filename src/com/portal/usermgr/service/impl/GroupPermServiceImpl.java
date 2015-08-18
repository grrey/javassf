 package com.portal.usermgr.service.impl;
 
 import com.portal.usermgr.dao.GroupPermDao;
 import com.portal.usermgr.entity.Group;
 import com.portal.usermgr.entity.GroupPerm;
 import com.portal.usermgr.service.GroupPermService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class GroupPermServiceImpl
   implements GroupPermService
 {
   private GroupPermDao dao;
 
   @Transactional(readOnly=true)
   public Page<GroupPerm> getPage(int pageNo, int pageSize)
   {
     return this.dao.getPage(pageNo, pageSize);
   }
   @Transactional(readOnly=true)
   public GroupPerm findById(Integer id) {
     GroupPerm entity = (GroupPerm)this.dao.findById(id);
     return entity;
   }
 
   public GroupPerm save(Group group, GroupPerm perm) {
     perm.setGroup(group);
     save(perm);
     group.setGroupPerm(perm);
     return perm;
   }
 
   public GroupPerm save(GroupPerm bean) {
     this.dao.save(bean);
     return bean;
   }
 
   public GroupPerm update(GroupPerm bean) {
     bean = (GroupPerm)this.dao.update(bean);
     return bean;
   }
 
   public GroupPerm deleteById(Integer id) {
     GroupPerm bean = (GroupPerm)this.dao.deleteById(id);
     return bean;
   }
 
   public GroupPerm[] deleteByIds(Integer[] ids) {
     GroupPerm[] beans = new GroupPerm[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   @Autowired
   public void setDao(GroupPermDao dao)
   {
     this.dao = dao;
   }
 }


 
 
 
 package com.portal.usermgr.service.impl;
 
 import com.portal.usermgr.dao.RolePermDao;
 import com.portal.usermgr.entity.Role;
 import com.portal.usermgr.entity.RolePerm;
 import com.portal.usermgr.service.RolePermService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class RolePermServiceImpl
   implements RolePermService
 {
   private RolePermDao dao;
 
   @Transactional(readOnly=true)
   public Page<RolePerm> getPage(int pageNo, int pageSize)
   {
     return this.dao.getPage(pageNo, pageSize);
   }
   @Transactional(readOnly=true)
   public RolePerm findById(Integer id) {
     RolePerm entity = (RolePerm)this.dao.findById(id);
     return entity;
   }
 
   public RolePerm save(Role role, RolePerm perm) {
     perm.setRole(role);
     save(perm);
     role.setRolePerm(perm);
     return perm;
   }
 
   public RolePerm save(RolePerm bean) {
     this.dao.save(bean);
     return bean;
   }
 
   public RolePerm update(RolePerm bean) {
     bean = (RolePerm)this.dao.update(bean);
     return bean;
   }
 
   public int deleteBySiteId(Integer siteId) {
     return this.dao.deleteBySiteId(siteId);
   }
 
   public RolePerm deleteById(Integer id) {
     RolePerm bean = (RolePerm)this.dao.deleteById(id);
     return bean;
   }
 
   public RolePerm[] deleteByIds(Integer[] ids) {
     RolePerm[] beans = new RolePerm[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   @Autowired
   public void setDao(RolePermDao dao)
   {
     this.dao = dao;
   }
 }


 
 
 
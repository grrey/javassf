 package com.portal.usermgr.service.impl;
 
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.event.DelRoleEvent;
 import com.portal.usermgr.dao.RoleDao;
 import com.portal.usermgr.entity.Role;
 import com.portal.usermgr.entity.RolePerm;
 import com.portal.usermgr.service.RolePermService;
 import com.portal.usermgr.service.RoleService;
 import java.util.List;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.context.ApplicationContext;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class RoleServiceImpl
   implements RoleService
 {
   private RoleDao dao;
   private RolePermService rolePermService;
   private ApplicationContext applicationContext;
 
   @Transactional(readOnly=true)
   public Page<Role> getPage(String name, Integer siteId, String sortname, String sortorder, int pageNo, int pageSize)
   {
     return this.dao.getPage(name, siteId, sortname, sortorder, pageNo, pageSize);
   }
   @Transactional(readOnly=true)
   public List<Role> getListBySite(Integer siteId) {
     return this.dao.getListBySite(siteId);
   }
   @Transactional(readOnly=true)
   public Role findById(Integer id) {
     Role entity = (Role)this.dao.findById(id);
     return entity;
   }
 
   public Role saveRole(Role role, RolePerm rolePerm, Site site) {
     role.setSite(site);
     save(role);
     this.rolePermService.save(role, rolePerm);
     return role;
   }
 
   public Role updateRole(Role role, RolePerm rolePerm) {
     role = update(role);
     this.rolePermService.update(rolePerm);
     return role;
   }
 
   public Role save(Role bean) {
     bean.init();
     this.dao.save(bean);
     return bean;
   }
 
   public Role update(Role bean) {
     bean = (Role)this.dao.update(bean);
     return bean;
   }
 
   public int deleteBySiteId(Integer siteId) {
     this.rolePermService.deleteBySiteId(siteId);
     return this.dao.deleteBySiteId(siteId);
   }
 
   public Role deleteById(Integer id) {
     Role bean = (Role)this.dao.deleteById(id);
     this.applicationContext.publishEvent(new DelRoleEvent(bean));
     return bean;
   }
 
   public Role[] deleteByIds(Integer[] ids) {
     Role[] beans = new Role[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   @Autowired
   public void setDao(RoleDao dao)
   {
     this.dao = dao;
   }
   @Autowired
   public void setRolePermService(RolePermService rolePermService) {
     this.rolePermService = rolePermService;
   }
   @Autowired
   public void setApplicationContext(ApplicationContext applicationContext) {
     this.applicationContext = applicationContext;
   }
 }


 
 
 
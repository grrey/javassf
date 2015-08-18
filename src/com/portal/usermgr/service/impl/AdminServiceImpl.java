 package com.portal.usermgr.service.impl;
 
 import com.portal.sysmgr.entity.Site;
 import com.portal.usermgr.dao.AdminDao;
 import com.portal.usermgr.entity.Admin;
 import com.portal.usermgr.entity.Depart;
 import com.portal.usermgr.entity.Role;
 import com.portal.usermgr.entity.User;
 import com.portal.usermgr.service.AdminCheckService;
 import com.portal.usermgr.service.AdminService;
 import com.portal.usermgr.service.DepartService;
 import com.portal.usermgr.service.RoleService;
 import com.portal.usermgr.service.UserService;
 import java.sql.Timestamp;
 import java.util.List;
 import java.util.Set;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class AdminServiceImpl
   implements AdminService
 {
   private AdminDao dao;
   private UserService userService;
   private AdminCheckService adminCheckService;
   private RoleService roleService;
   private DepartService departService;
 
   @Transactional(readOnly=true)
   public Page<Admin> getPage(int pageNo, int pageSize)
   {
     return this.dao.getPage(pageNo, pageSize);
   }
 
   @Transactional(readOnly=true)
   public Page<Admin> getPage(String key, Integer siteId, Integer departId, Integer roleId, String sortname, String sortorder, int pageNo, int pageSize)
   {
     return this.dao.getPage(key, siteId, departId, roleId, sortname, sortorder, 
       pageNo, pageSize);
   }
   @Transactional(readOnly=true)
   public List<Admin> getListByDepart(Integer departId) {
     return this.dao.getListByDepart(departId);
   }
   @Transactional(readOnly=true)
   public List<Admin> getListByRole(Integer roleId) {
     return this.dao.getListByRole(roleId);
   }
   @Transactional(readOnly=true)
   public Admin findById(Integer id) {
     Admin entity = (Admin)this.dao.findById(id);
     return entity;
   }
 
   public Admin saveAdmin(User user, Admin admin, String ip, Integer roleId, Integer departId, Site site, Byte manageStatus, Boolean takeDepart, Integer[] channelIds)
   {
     admin.setRegisterIp(ip);
     if (roleId != null) {
       admin.addToRoles(this.roleService.findById(roleId));
     }
     if (departId != null) {
       admin.addToDeparts(this.departService.findById(departId));
     }
     this.userService.save(user);
     admin.setUser(user);
     admin = save(admin);
     user.setAdmin(admin);
     this.adminCheckService.save(site, admin, manageStatus, takeDepart, 
       channelIds);
     return admin;
   }
 
   public Admin updateAdmin(User user, Admin admin, Integer roleId, Integer departId, Site site, Byte manageStatus, Boolean takeDepart, Integer[] channelIds)
   {
     admin = update(admin);
     this.adminCheckService.updateByAdmin(admin, site, manageStatus, takeDepart, 
       channelIds);
     this.userService.update(user);
     if ((roleId != null) && 
       (!roleId.equals(admin.getRole(site.getId()).getId()))) {
       admin.getRoles().remove(admin.getRole(site.getId()));
       admin.addToRoles(this.roleService.findById(roleId));
     }
 
     if (departId != null) {
       if (admin.getDepart(site.getId()) != null) {
         if (!departId.equals(admin.getDepart(site.getId()).getId())) {
           admin.getDeparts().remove(admin.getDepart(site.getId()));
           admin.addToDeparts(this.departService.findById(departId));
         }
       }
       else admin.addToDeparts(this.departService.findById(departId));
     }
 
     return admin;
   }
 
   public void updateLoginInfo(User user, String lastLoginIp) {
     Admin admin = findById(user.getId());
     if (admin != null) {
       admin.setLastLoginIp(lastLoginIp);
       admin.setLastLoginTime(new Timestamp(System.currentTimeMillis()));
       if (admin.getRegisterIp() == null) {
         admin.setRegisterIp(lastLoginIp);
       }
       admin.setLoginCount(Integer.valueOf(admin.getLoginCount().intValue() + 1));
     }
   }
 
   public Admin save(Admin bean) {
     bean.init();
     this.dao.save(bean);
     return bean;
   }
 
   public Admin update(Admin bean) {
     bean = (Admin)this.dao.update(bean);
     return bean;
   }
 
   public Admin updatePass(Integer adminId, String password) {
     User user = this.userService.updatePass(adminId, password);
     return user.getAdmin();
   }
 
   public Admin deleteById(Integer id) {
     Admin bean = (Admin)this.dao.deleteById(id);
     bean.getAdminChecks().clear();
     this.userService.deleteById(id);
     return bean;
   }
 
   public Admin[] deleteByIds(Integer[] ids) {
     Admin[] beans = new Admin[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   @Autowired
   public void setDao(AdminDao dao)
   {
     this.dao = dao;
   }
   @Autowired
   public void setUserService(UserService userService) {
     this.userService = userService;
   }
   @Autowired
   public void setAdminCheckService(AdminCheckService adminCheckService) {
     this.adminCheckService = adminCheckService;
   }
   @Autowired
   public void setRoleService(RoleService roleService) {
     this.roleService = roleService;
   }
   @Autowired
   public void setDepartService(DepartService departService) {
     this.departService = departService;
   }
 }


 
 
 
 package com.portal.usermgr.service.impl;
 
 import com.portal.usermgr.dao.ThirdpartyBindDao;
 import com.portal.usermgr.entity.ThirdpartyBind;
 import com.portal.usermgr.service.ThirdpartyBindService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class ThirdpartyBindServiceImpl
   implements ThirdpartyBindService
 {
   private ThirdpartyBindDao dao;
 
   @Transactional(readOnly=true)
   public Page<ThirdpartyBind> getPage(int pageNo, int pageSize)
   {
     return this.dao.getPage(pageNo, pageSize);
   }
   @Transactional(readOnly=true)
   public ThirdpartyBind findByOpenId(String openid, String bindType) {
     return this.dao.findByOpenId(openid, bindType);
   }
   @Transactional(readOnly=true)
   public ThirdpartyBind findById(Integer id) {
     ThirdpartyBind entity = (ThirdpartyBind)this.dao.findById(id);
     return entity;
   }
 
   public ThirdpartyBind save(String username, String openid, String openkey, String bindType)
   {
     ThirdpartyBind bean = new ThirdpartyBind();
     bean.setUsername(username);
     bean.setOpenid(openid);
     bean.setOpenkey(openkey);
     bean.setBindType(bindType);
     bean.init();
     this.dao.save(bean);
     return bean;
   }
 
   public ThirdpartyBind update(ThirdpartyBind bean) {
     bean = (ThirdpartyBind)this.dao.update(bean);
     return bean;
   }
 
   public ThirdpartyBind deleteById(Integer id) {
     ThirdpartyBind bean = (ThirdpartyBind)this.dao.deleteById(id);
     return bean;
   }
 
   public ThirdpartyBind[] deleteByIds(Integer[] ids) {
     ThirdpartyBind[] beans = new ThirdpartyBind[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   @Autowired
   public void setDao(ThirdpartyBindDao dao)
   {
     this.dao = dao;
   }
 }


 
 
 
 package com.portal.extrafunc.service.impl;
 
 import com.portal.extrafunc.dao.CategoryDao;
 import com.portal.extrafunc.entity.Category;
 import com.portal.extrafunc.service.CategoryService;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.event.DelCategoryEvent;
 import java.util.List;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.context.ApplicationContext;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class CategoryServiceImpl
   implements CategoryService
 {
   private CategoryDao dao;
   private ApplicationContext applicationContext;
 
   @Transactional(readOnly=true)
   public Page<Category> getPage(String sortname, String sortorder, int pageNo, int pageSize)
   {
     return this.dao.getPage(sortname, sortorder, pageNo, pageSize);
   }
 
   @Transactional(readOnly=true)
   public List<Category> getList(Integer siteId, String sortname, String sortorder) {
     return this.dao.getList(siteId, sortname, sortorder);
   }
   @Transactional(readOnly=true)
   public Category findById(Integer id) {
     Category entity = (Category)this.dao.findById(id);
     return entity;
   }
 
   public Category save(Category bean, Site site) {
     bean.setSite(site);
     this.dao.save(bean);
     return bean;
   }
 
   public Category update(Category bean) {
     bean = (Category)this.dao.update(bean);
     return bean;
   }
 
   public int deleteBySiteId(Integer siteId) {
     return this.dao.deleteBySiteId(siteId);
   }
 
   public Category deleteById(Integer id) {
     Category bean = (Category)this.dao.deleteById(id);
     this.applicationContext.publishEvent(new DelCategoryEvent(bean));
     return bean;
   }
 
   public Category[] deleteByIds(Integer[] ids) {
     Category[] beans = new Category[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   @Autowired
   public void setDao(CategoryDao dao)
   {
     this.dao = dao;
   }
   @Autowired
   public void setApplicationContext(ApplicationContext applicationContext) {
     this.applicationContext = applicationContext;
   }
 }


 
 
 
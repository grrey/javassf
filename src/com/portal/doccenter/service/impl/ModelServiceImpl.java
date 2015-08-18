 package com.portal.doccenter.service.impl;
 
 import com.portal.doccenter.dao.ModelDao;
 import com.portal.doccenter.entity.Model;
 import com.portal.doccenter.service.ModelService;
 import java.util.ArrayList;
 import java.util.List;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class ModelServiceImpl
   implements ModelService
 {
   private ModelDao dao;
 
   @Transactional(readOnly=true)
   public List<Model> getList(boolean containDisabled, String sortname, String sortorder)
   {
     return this.dao.getList(containDisabled, sortname, sortorder);
   }
 
   public Model getDefModel() {
     return this.dao.getDefModel();
   }
   @Transactional(readOnly=true)
   public Model findById(Integer id) {
     Model entity = (Model)this.dao.findById(id);
     return entity;
   }
 
   public Model save(Model bean) {
     bean.init();
     this.dao.save(bean);
     return bean;
   }
 
   public Model update(Model bean) {
     Model entity = (Model)this.dao.update(bean);
     return entity;
   }
 
   public Model deleteById(Integer id) {
     Model bean = (Model)this.dao.deleteById(id);
     return bean;
   }
 
   public Model[] deleteByIds(Integer[] ids) {
     Model[] beans = new Model[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   public List<Model> getModelList(List<Integer> modelIdList) {
     List modelList = new ArrayList();
     if ((modelIdList != null) && (modelIdList.size() > 0)) {
       for (Integer modelId : modelIdList) {
         modelList.add(findById(modelId));
       }
     }
     return modelList;
   }
 
   public Model[] updatePriority(Integer[] ids, Integer[] priority, Boolean[] disabled, Integer defId)
   {
     int len = ids.length;
     Model[] beans = new Model[len];
     for (int i = 0; i < len; i++) {
       beans[i] = findById(ids[i]);
       beans[i].setPriority(priority[i]);
       beans[i].setDisabled(disabled[i]);
     }
     return beans;
   }
 
   @Autowired
   public void setDao(ModelDao dao)
   {
     this.dao = dao;
   }
 }


 
 
 
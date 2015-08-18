 package com.portal.doccenter.service.impl;
 
 import com.portal.doccenter.dao.ModelFieldDao;
 import com.portal.doccenter.entity.Model;
 import com.portal.doccenter.entity.ModelField;
 import com.portal.doccenter.service.ModelFieldService;
 import com.portal.doccenter.service.ModelService;
 import java.util.List;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class ModelFieldServiceImpl
   implements ModelFieldService
 {
   private ModelService modelService;
   private ModelFieldDao dao;
 
   @Transactional(readOnly=true)
   public List<ModelField> getList(Integer modelId, boolean hasDisabled, String sortname, String sortorder)
   {
     return this.dao.getList(modelId, hasDisabled, sortname, sortorder);
   }
   @Transactional(readOnly=true)
   public ModelField findById(Integer id) {
     ModelField entity = (ModelField)this.dao.findById(id);
     return entity;
   }
 
   public ModelField save(ModelField bean, Integer modelId) {
     bean.setModel(this.modelService.findById(modelId));
     int maxpri = this.dao.getMaxPriority(modelId);
     bean.setPriority(Integer.valueOf(maxpri + 1));
     return save(bean);
   }
 
   public ModelField save(ModelField bean) {
     bean.init();
     this.dao.save(bean);
     return bean;
   }
 
   public void saveList(List<ModelField> list) {
     for (ModelField item : list)
       save(item);
   }
 
   public void updatePriority(Integer id, Integer priority)
   {
     ModelField item = findById(id);
     if (priority.intValue() > item.getPriority().intValue()) {
       List<ModelField> list = this.dao.getListByPriority(item.getModel()
         .getId(), Integer.valueOf(item.getPriority().intValue() + 1), Integer.valueOf(priority.intValue() + 1), true);
       for (ModelField itemi : list)
         itemi.setPriority(Integer.valueOf(itemi.getPriority().intValue() - 1));
     }
     else {
       List<ModelField> list = this.dao.getListByPriority(item.getModel()
         .getId(), priority, item.getPriority(), true);
       for (ModelField itemi : list) {
         itemi.setPriority(Integer.valueOf(itemi.getPriority().intValue() + 1));
       }
     }
     item.setPriority(priority);
   }
 
   public ModelField update(ModelField bean) {
     return (ModelField)this.dao.update(bean);
   }
 
   public ModelField deleteById(Integer id) {
     ModelField bean = (ModelField)this.dao.deleteById(id);
     return bean;
   }
 
   public ModelField[] deleteByIds(Integer[] ids) {
     ModelField[] beans = new ModelField[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   @Autowired
   public void setModelService(ModelService modelService)
   {
     this.modelService = modelService;
   }
   @Autowired
   public void setDao(ModelFieldDao dao) {
     this.dao = dao;
   }
 }


 
 
 
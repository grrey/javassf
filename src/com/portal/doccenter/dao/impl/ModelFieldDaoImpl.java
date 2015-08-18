 package com.portal.doccenter.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.doccenter.dao.ModelFieldDao;
 import com.portal.doccenter.entity.ModelField;
 import java.util.List;
 import org.apache.commons.lang.StringUtils;
 import org.hibernate.Criteria;
 import org.hibernate.criterion.Order;
 import org.hibernate.criterion.Projections;
 import org.hibernate.criterion.Restrictions;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class ModelFieldDaoImpl extends QueryDaoImpl<ModelField, Integer>
   implements ModelFieldDao
 {
   public List<ModelField> getList(Integer modelId, boolean hasDisabled, String sortname, String sortorder)
   {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("model.id", modelId));
     if (!hasDisabled) {
       crit.add(Restrictions.eq("show", Boolean.valueOf(true)));
     }
     if (!StringUtils.isBlank(sortname)) {
       if ("asc".equals(sortorder))
         crit.addOrder(Order.asc(sortname));
       else
         crit.addOrder(Order.desc(sortname));
     }
     else {
       crit.addOrder(Order.asc("priority"));
       crit.addOrder(Order.asc("id"));
     }
     return findByCriteria(crit);
   }
 
   public List<ModelField> getListByPriority(Integer modelId, Integer priority, Integer priority1, boolean hasDisabled)
   {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("model.id", modelId));
     crit.add(Restrictions.ge("priority", priority));
     crit.add(Restrictions.lt("priority", priority1));
     if (!hasDisabled) {
       crit.add(Restrictions.eq("show", Boolean.valueOf(true)));
     }
     crit.addOrder(Order.asc("priority"));
     crit.addOrder(Order.asc("id"));
     return findByCriteria(crit);
   }
 
   public int getMaxPriority(Integer modelId) {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("model.id", modelId));
     crit.setProjection(Projections.max("priority"));
     return ((Integer)crit.uniqueResult()).intValue();
   }
 
   protected Class<ModelField> getEntityClass()
   {
     return ModelField.class;
   }
 }


 
 
 
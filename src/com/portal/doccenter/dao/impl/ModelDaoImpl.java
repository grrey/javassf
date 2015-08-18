 package com.portal.doccenter.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.doccenter.dao.ModelDao;
 import com.portal.doccenter.entity.Model;
 import java.util.List;
 import org.apache.commons.lang.StringUtils;
 import org.hibernate.Criteria;
 import org.hibernate.criterion.Order;
 import org.hibernate.criterion.Restrictions;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class ModelDaoImpl extends QueryDaoImpl<Model, Integer>
   implements ModelDao
 {
   public List<Model> getList(boolean containDisabled, String sortname, String sortorder)
   {
     Criteria crit = createCriteria();
     if (!containDisabled) {
       crit.add(Restrictions.eq("disabled", Boolean.valueOf(false)));
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
 
   public Model getDefModel() {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("def", Boolean.valueOf(true)));
     return (Model)findUnique(crit);
   }
 
   protected Class<Model> getEntityClass()
   {
     return Model.class;
   }
 }


 
 
 
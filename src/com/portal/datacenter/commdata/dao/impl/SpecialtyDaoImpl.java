 package com.portal.datacenter.commdata.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.datacenter.commdata.dao.SpecialtyDao;
 import com.portal.datacenter.commdata.entity.Specialty;
 import java.util.List;
 import org.apache.commons.lang.StringUtils;
 import org.hibernate.Criteria;
 import org.hibernate.criterion.Order;
 import org.hibernate.criterion.Restrictions;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class SpecialtyDaoImpl extends QueryDaoImpl<Specialty, Integer>
   implements SpecialtyDao
 {
   public Page<Specialty> getPage(String key, int pageNo, int pageSize)
   {
     Criteria crit = createCriteria();
     if (!StringUtils.isBlank(key)) {
       crit.add(Restrictions.or(
         Restrictions.like("code", "%" + key + "%"), 
         Restrictions.like("name", "%" + key + "%")));
     }
     crit.addOrder(Order.asc("code"));
     return findByCriteria(crit, pageNo, pageSize);
   }
 
   public List<Specialty> getSpecialtyList(Integer id) {
     Criteria crit = createCriteria();
     crit.add(Restrictions.isNull("parent"));
     if (id != null) {
       crit.add(Restrictions.ne("id", id));
     }
     crit.addOrder(Order.asc("code"));
     return findByCriteria(crit);
   }
 
   public List<Specialty> getSpecialtyChild(Integer id) {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("parent.id", id));
     crit.addOrder(Order.asc("code"));
     return findByCriteria(crit);
   }
 
   public Specialty findByCode(String code) {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("code", code));
     return (Specialty)findUnique(crit);
   }
 
   protected Class<Specialty> getEntityClass()
   {
     return Specialty.class;
   }
 }

 package com.portal.extrafunc.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.extrafunc.dao.LinksTypeDao;
 import com.portal.extrafunc.entity.LinksType;
 import java.util.List;
 import org.apache.commons.lang.StringUtils;
 import org.hibernate.Criteria;
 import org.hibernate.criterion.Order;
 import org.hibernate.criterion.Restrictions;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class LinksTypeDaoImpl extends QueryDaoImpl<LinksType, Integer>
   implements LinksTypeDao
 {
   public List<LinksType> getList(Integer siteId, String sortname, String sortorder)
   {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("site.id", siteId));
     if (!StringUtils.isBlank(sortname)) {
       if ("asc".equals(sortorder))
         crit.addOrder(Order.asc(sortname));
       else
         crit.addOrder(Order.desc(sortname));
     }
     else {
       crit.addOrder(Order.asc("priority"));
     }
     return findByCriteria(crit);
   }
 
   protected Class<LinksType> getEntityClass()
   {
     return LinksType.class;
   }
 }


 
 
 
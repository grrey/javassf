 package com.portal.extrafunc.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.extrafunc.dao.LinksDao;
 import com.portal.extrafunc.entity.Links;
 import java.util.List;
 import org.apache.commons.lang.StringUtils;
 import org.hibernate.Criteria;
 import org.hibernate.criterion.Order;
 import org.hibernate.criterion.Restrictions;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class LinksDaoImpl extends QueryDaoImpl<Links, Integer>
   implements LinksDao
 {
   public Page<Links> getPage(Integer siteId, Integer typeId, String sortname, String sortorder, int pageNo, int pageSize)
   {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("site.id", siteId));
     if (typeId != null) {
       crit.add(Restrictions.eq("type.id", typeId));
     }
     if (!StringUtils.isBlank(sortname)) {
       if ("asc".equals(sortorder))
         crit.addOrder(Order.asc(sortname));
       else
         crit.addOrder(Order.desc(sortname));
     }
     else {
       crit.addOrder(Order.asc("priority"));
     }
     return findByCriteria(crit, pageNo, pageSize);
   }
 
   public List<Links> getListByTag(Integer siteId, Integer typeId, Integer count)
   {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("site.id", siteId));
     if (typeId != null) {
       crit.add(Restrictions.eq("type.id", typeId));
     }
     crit.add(Restrictions.eq("show", Boolean.valueOf(true)));
     crit.addOrder(Order.asc("priority"));
     if (count != null) {
       crit.setMaxResults(count.intValue());
     }
     return findByCriteria(crit);
   }
 
   protected Class<Links> getEntityClass()
   {
     return Links.class;
   }
 }


 
 
 
 package com.portal.extrafunc.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.extrafunc.dao.AdvertSlotDao;
 import com.portal.extrafunc.entity.AdvertSlot;
 import java.util.List;
 import org.apache.commons.lang.StringUtils;
 import org.hibernate.Criteria;
 import org.hibernate.criterion.Order;
 import org.hibernate.criterion.Restrictions;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class AdvertSlotDaoImpl extends QueryDaoImpl<AdvertSlot, Integer>
   implements AdvertSlotDao
 {
   public Page<AdvertSlot> getPage(Integer siteId, String sortname, String sortorder, int pageNo, int pageSize)
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
       crit.addOrder(Order.asc("id"));
     }
     return findByCriteria(crit, pageNo, pageSize);
   }
 
   public List<AdvertSlot> getList(Integer siteId) {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("site.id", siteId));
     return findByCriteria(crit);
   }
 
   public int deleteBySiteId(Integer siteId) {
     String hql = "delete from AdvertSlot bean where bean.site.id=?";
     return executeQuery(hql, new Object[] { siteId });
   }
 
   protected Class<AdvertSlot> getEntityClass()
   {
     return AdvertSlot.class;
   }
 }


 
 
 
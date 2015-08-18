 package com.portal.extrafunc.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.extrafunc.dao.AdvertDao;
 import com.portal.extrafunc.entity.Advert;
 import java.util.Date;
 import java.util.List;
 import org.apache.commons.lang.StringUtils;
 import org.hibernate.Criteria;
 import org.hibernate.criterion.Order;
 import org.hibernate.criterion.Restrictions;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class AdvertDaoImpl extends QueryDaoImpl<Advert, Integer>
   implements AdvertDao
 {
   public Page<Advert> getPage(Integer siteId, Integer slotId, String sortname, String sortorder, int pageNo, int pageSize)
   {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("site.id", siteId));
     if (slotId != null) {
       crit.add(Restrictions.eq("slot.id", slotId));
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
 
   public List<Advert> getListByTag(Integer siteId, Integer slotId) {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("site.id", siteId));
     if (slotId != null) {
       crit.add(Restrictions.eq("slot.id", slotId));
     }
     crit.add(Restrictions.eq("enable", Boolean.valueOf(true)));
     crit.add(Restrictions.le("startTime", new Date()));
     crit.add(Restrictions.or(Restrictions.isNull("endTime"), 
       Restrictions.ge("endTime", new Date())));
     crit.addOrder(Order.asc("priority"));
     return findByCriteria(crit);
   }
 
   public int deleteBySlotId(Integer slotId) {
     String hql = "delete from Advert bean where bean.slot.id=?";
     return executeQuery(hql, new Object[] { slotId });
   }
 
   public int deleteBySiteId(Integer siteId) {
     String hql = "delete from Advert bean where bean.site.id=?";
     return executeQuery(hql, new Object[] { siteId });
   }
 
   protected Class<Advert> getEntityClass()
   {
     return Advert.class;
   }
 }


 
 
 
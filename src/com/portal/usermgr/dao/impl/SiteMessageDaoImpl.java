 package com.portal.usermgr.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.usermgr.dao.SiteMessageDao;
 import com.portal.usermgr.entity.SiteMessage;
 import org.hibernate.Criteria;
 import org.hibernate.criterion.Order;
 import org.hibernate.criterion.Restrictions;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class SiteMessageDaoImpl extends QueryDaoImpl<SiteMessage, Integer>
   implements SiteMessageDao
 {
   public Page<SiteMessage> getPage(int pageNo, int pageSize)
   {
     Criteria crit = createCriteria();
     return findByCriteria(crit, pageNo, pageSize);
   }
 
   public Page<SiteMessage> getPageByTag(Integer sendId, Integer status, int pageNo, int pageSize)
   {
     Criteria crit = createCriteria();
     if (sendId != null) {
       crit.add(Restrictions.eq("send.id", sendId));
     }
     if (status != null)
       crit.add(Restrictions.eq("status", status));
     else {
       crit.add(Restrictions.eq("status", SiteMessage.NORMAL));
     }
     crit.addOrder(Order.desc("createTime"));
     return findByCriteria(crit, pageNo, pageSize);
   }
 
   protected Class<SiteMessage> getEntityClass()
   {
     return SiteMessage.class;
   }
 }


 
 
 
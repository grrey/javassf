 package com.portal.govcenter.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.govcenter.dao.MailboxTypeDao;
 import com.portal.govcenter.entity.MailboxType;
 import java.util.List;
 import org.hibernate.Criteria;
 import org.hibernate.criterion.Order;
 import org.hibernate.criterion.Restrictions;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class MailboxTypeDaoImpl extends QueryDaoImpl<MailboxType, Integer>
   implements MailboxTypeDao
 {
   public List<MailboxType> getList(Integer siteId)
   {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("site.id", siteId));
     crit.addOrder(Order.asc("priority"));
     return findByCriteria(crit);
   }
 
   protected Class<MailboxType> getEntityClass()
   {
     return MailboxType.class;
   }
 }


 
 
 
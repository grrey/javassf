 package com.portal.extrafunc.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.extrafunc.dao.MessageTypeDao;
 import com.portal.extrafunc.entity.MessageType;
 import java.util.List;
 import org.apache.commons.lang.StringUtils;
 import org.hibernate.Criteria;
 import org.hibernate.criterion.Order;
 import org.hibernate.criterion.Restrictions;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class MessageTypeDaoImpl extends QueryDaoImpl<MessageType, Integer>
   implements MessageTypeDao
 {
   public Page<MessageType> getPage(int pageNo, int pageSize)
   {
     Criteria crit = createCriteria();
     return findByCriteria(crit, pageNo, pageSize);
   }
 
   public List<MessageType> getList(Integer siteId, String sortname, String sortorder)
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
 
   public MessageType getUniqueType(Integer siteId) {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("site.id", siteId));
     crit.addOrder(Order.asc("priority"));
     return (MessageType)findUnique(crit);
   }
 
   protected Class<MessageType> getEntityClass()
   {
     return MessageType.class;
   }
 }


 
 
 
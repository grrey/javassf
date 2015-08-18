 package com.portal.extrafunc.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.extrafunc.dao.MessageBoardDao;
 import com.portal.extrafunc.entity.MessageBoard;
 import org.apache.commons.lang.StringUtils;
 import org.hibernate.Criteria;
 import org.hibernate.criterion.Order;
 import org.hibernate.criterion.Projections;
 import org.hibernate.criterion.Restrictions;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class MessageBoardDaoImpl extends QueryDaoImpl<MessageBoard, Integer>
   implements MessageBoardDao
 {
   public Page<MessageBoard> getPage(String name, Integer siteId, Boolean show, String sortname, String sortorder, int pageNo, int pageSize)
   {
     Criteria crit = createCriteria();
     if (!StringUtils.isBlank(name)) {
       crit.add(Restrictions.like("title", "%" + name + "%"));
     }
     if (show != null) {
       crit.add(Restrictions.eq("show", show));
     }
     crit.add(Restrictions.eq("site.id", siteId));
     if (!StringUtils.isBlank(sortname)) {
       if ("asc".equals(sortorder))
         crit.addOrder(Order.asc(sortname));
       else
         crit.addOrder(Order.desc(sortname));
     }
     else {
       crit.addOrder(Order.desc("createTime"));
     }
     return findByCriteria(crit, pageNo, pageSize);
   }
 
   public Integer getAllMessageCount(Integer siteId) {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("site.id", siteId));
     crit.setProjection(Projections.count("id"));
     return Integer.valueOf(((Number)crit.uniqueResult()).intValue());
   }
 
   public Integer getNoRepMessageCount(Integer siteId) {
     Criteria crit = createCriteria();
     crit.createAlias("ext", "ext");
     crit.add(Restrictions.eq("site.id", siteId));
     crit.add(Restrictions.isNull("ext.reply"));
     crit.setProjection(Projections.count("id"));
     return Integer.valueOf(((Number)crit.uniqueResult()).intValue());
   }
 
   protected Class<MessageBoard> getEntityClass()
   {
     return MessageBoard.class;
   }
 }


 
 
 
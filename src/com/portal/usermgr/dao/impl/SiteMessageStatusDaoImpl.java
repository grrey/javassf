 package com.portal.usermgr.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.usermgr.dao.SiteMessageStatusDao;
 import com.portal.usermgr.entity.SiteMessageStatus;
 import org.hibernate.Criteria;
 import org.hibernate.criterion.Restrictions;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class SiteMessageStatusDaoImpl extends QueryDaoImpl<SiteMessageStatus, Integer>
   implements SiteMessageStatusDao
 {
   public Page<SiteMessageStatus> getPage(int pageNo, int pageSize)
   {
     Criteria crit = createCriteria();
     return findByCriteria(crit, pageNo, pageSize);
   }
 
   public SiteMessageStatus findByRecive(Integer receiveId, Integer messageId) {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("receive.id", receiveId));
     crit.add(Restrictions.eq("message.id", receiveId));
     return (SiteMessageStatus)findUnique(crit);
   }
 
   protected Class<SiteMessageStatus> getEntityClass()
   {
     return SiteMessageStatus.class;
   }
 }


 
 
 
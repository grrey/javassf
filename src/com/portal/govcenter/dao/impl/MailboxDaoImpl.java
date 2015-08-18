 package com.portal.govcenter.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.govcenter.dao.MailboxDao;
 import com.portal.govcenter.entity.Mailbox;
 import org.apache.commons.lang.StringUtils;
 import org.hibernate.Criteria;
 import org.hibernate.criterion.Restrictions;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class MailboxDaoImpl extends QueryDaoImpl<Mailbox, Integer>
   implements MailboxDao
 {
   public Page<Mailbox> getPage(String name, Integer siteId, Integer departId, Integer typeId, int pageNo, int pageSize)
   {
     Criteria crit = createCriteria();
     if (!StringUtils.isBlank(name)) {
       crit.add(Restrictions.like("title", "%" + name + "%"));
     }
     crit.add(Restrictions.eq("site.id", siteId));
     if (typeId != null) {
       crit.add(Restrictions.eq("type.id", typeId));
     }
     if (departId != null) {
       crit.add(Restrictions.eq("depart.id", departId));
       crit.add(Restrictions.ne("status", Mailbox.DELETED));
     } else {
       crit.add(Restrictions.ne("status", Mailbox.DELETED));
       crit.add(Restrictions.ne("status", Mailbox.FORWARD));
     }
     return findByCriteria(crit, pageNo, pageSize);
   }
 
   public Page<Mailbox> getPageByTag(String name, Integer siteId, Integer departId, Integer typeId, int pageNo, int pageSize)
   {
     Criteria crit = createCriteria();
     if (!StringUtils.isBlank(name)) {
       crit.add(Restrictions.like("title", "%" + name + "%"));
     }
     crit.add(Restrictions.eq("site.id", siteId));
     if (typeId != null) {
       crit.add(Restrictions.eq("type.id", typeId));
     }
     if (departId != null) {
       crit.add(Restrictions.eq("depart.id", departId));
     }
     crit.add(Restrictions.ne("status", Mailbox.DELETED));
     crit.add(Restrictions.eq("show", Boolean.valueOf(true)));
     return findByCriteria(crit, pageNo, pageSize);
   }
 
   protected Class<Mailbox> getEntityClass()
   {
     return Mailbox.class;
   }
 }


 
 
 
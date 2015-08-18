 package com.portal.usermgr.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.usermgr.dao.MemberDao;
 import com.portal.usermgr.entity.Member;
 import org.apache.commons.lang.StringUtils;
 import org.hibernate.Criteria;
 import org.hibernate.criterion.Order;
 import org.hibernate.criterion.Projections;
 import org.hibernate.criterion.Restrictions;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class MemberDaoImpl extends QueryDaoImpl<Member, Integer>
   implements MemberDao
 {
   public Page<Member> getPage(int pageNo, int pageSize)
   {
     Criteria crit = createCriteria();
     Page page = findByCriteria(crit, pageNo, pageSize);
     return page;
   }
 
   public Page<Member> getPage(String key, Integer siteId, Integer groupId, String sortname, String sortorder, int pageNo, int pageSize)
   {
     Criteria crit = createCriteria();
     if (groupId != null) {
       crit.createAlias("groups", "g");
       crit.add(Restrictions.eq("g.id", groupId));
     } else if (siteId != null) {
       crit.createAlias("groups", "g");
       crit.add(Restrictions.eq("g.site.id", siteId));
     }
     if (!StringUtils.isBlank(key)) {
       crit.add(Restrictions.or(
         Restrictions.like("user.username", "%" + key + "%"), 
         Restrictions.like("user.realName", "%" + key + "%")));
     }
     if (!StringUtils.isBlank(sortname)) {
       crit.createAlias("user", "u");
       if ("asc".equals(sortorder))
         crit.addOrder(Order.asc(sortname));
       else
         crit.addOrder(Order.desc(sortname));
     }
     else {
       crit.addOrder(Order.desc("registerTime"));
     }
     return findByCriteria(crit, pageNo, pageSize);
   }
 
   public int getNoCheckMemberCount() {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("status", Byte.valueOf((byte) -2)));
     crit.setProjection(Projections.count("id"));
     return ((Number)crit.uniqueResult()).intValue();
   }
 
   protected Class<Member> getEntityClass()
   {
     return Member.class;
   }
 }


 
 
 
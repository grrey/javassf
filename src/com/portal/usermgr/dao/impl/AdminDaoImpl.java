 package com.portal.usermgr.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.usermgr.dao.AdminDao;
 import com.portal.usermgr.entity.Admin;
 import java.util.List;
 import org.apache.commons.lang.StringUtils;
 import org.hibernate.Criteria;
 import org.hibernate.criterion.Order;
 import org.hibernate.criterion.Restrictions;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class AdminDaoImpl extends QueryDaoImpl<Admin, Integer>
   implements AdminDao
 {
   public Page<Admin> getPage(String key, Integer siteId, Integer departId, Integer roleId, String sortname, String sortorder, int pageNo, int pageSize)
   {
     Criteria crit = createCriteria();
     crit.createAlias("user", "u");
     if (roleId != null) {
       crit.createAlias("roles", "role");
       crit.add(Restrictions.eq("role.id", roleId));
     } else if (departId != null) {
       crit.createAlias("departs", "depart");
       crit.add(Restrictions.eq("depart.id", departId));
     } else if (siteId != null) {
       crit.createAlias("roles", "role");
       crit.add(Restrictions.eq("role.site.id", siteId));
     }
     if (!StringUtils.isBlank(key)) {
       crit.add(Restrictions.or(
         Restrictions.like("u.username", "%" + key + "%"), 
         Restrictions.like("u.realName", "%" + key + "%")));
     }
     if (!StringUtils.isBlank(sortname)) {
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
 
   public List<Admin> getListByDepart(Integer departId) {
     Criteria crit = createCriteria();
     crit.createAlias("departs", "depart");
     crit.add(Restrictions.eq("depart.id", departId));
     crit.addOrder(Order.desc("registerTime"));
     return findByCriteria(crit);
   }
 
   public List<Admin> getListByRole(Integer roleId) {
     Criteria crit = createCriteria();
     crit.createAlias("roles", "role");
     crit.add(Restrictions.eq("role.id", roleId));
     crit.addOrder(Order.desc("registerTime"));
     return findByCriteria(crit);
   }
 
   protected Class<Admin> getEntityClass()
   {
     return Admin.class;
   }
 }


 
 
 
 package com.portal.usermgr.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.usermgr.dao.RoleDao;
 import com.portal.usermgr.entity.Role;
 import java.util.List;
 import org.apache.commons.lang.StringUtils;
 import org.hibernate.Criteria;
 import org.hibernate.criterion.Order;
 import org.hibernate.criterion.Restrictions;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class RoleDaoImpl extends QueryDaoImpl<Role, Integer>
   implements RoleDao
 {
   public Page<Role> getPage(String name, Integer siteId, String sortname, String sortorder, int pageNo, int pageSize)
   {
     Criteria crit = createCriteria();
     if (!StringUtils.isBlank(name)) {
       crit.add(Restrictions.like("name", "%" + name + "%"));
     }
     if (siteId != null) {
       crit.add(Restrictions.eq("site.id", siteId));
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
 
   public List<Role> getListBySite(Integer siteId) {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("site.id", siteId));
     crit.addOrder(Order.asc("priority"));
     return findByCriteria(crit);
   }
 
   public int deleteBySiteId(Integer siteId) {
     String hql = "delete from Role bean where bean.site.id=?";
     return executeQuery(hql, new Object[] { siteId });
   }
 
   protected Class<Role> getEntityClass()
   {
     return Role.class;
   }
 }


 
 
 
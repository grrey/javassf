 package com.portal.usermgr.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.usermgr.dao.DepartDao;
 import com.portal.usermgr.entity.Depart;
 import java.util.List;
 import org.apache.commons.lang.StringUtils;
 import org.hibernate.Criteria;
 import org.hibernate.criterion.Order;
 import org.hibernate.criterion.Restrictions;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class DepartDaoImpl extends QueryDaoImpl<Depart, Integer>
   implements DepartDao
 {
   public Page<Depart> getPage(String key, Integer siteId, String sortname, String sortorder, int pageNo, int pageSize)
   {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("site.id", siteId));
     if (!StringUtils.isBlank(key)) {
       crit.add(Restrictions.like("name", "%" + key + "%"));
     }
     if (!StringUtils.isBlank(sortname)) {
       if ("asc".equals(sortorder))
         crit.addOrder(Order.asc(sortname));
       else
         crit.addOrder(Order.desc(sortname));
     }
     else {
       crit.addOrder(Order.asc("priority"));
       crit.addOrder(Order.asc("id"));
     }
     return findByCriteria(crit, pageNo, pageSize);
   }
 
   public List<Depart> getListByTag(Integer siteId, Integer parentId, Integer orderBy)
   {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("site.id", siteId));
     crit.add(Restrictions.eq("show", Boolean.valueOf(true)));
     if (parentId != null) {
       crit.add(Restrictions.eq("parent.id", parentId));
     }
     if (orderBy != null) {
       if (orderBy.intValue() == 1) {
         crit.addOrder(Order.desc("signCount"));
         crit.addOrder(Order.asc("priority"));
       } else {
         crit.addOrder(Order.desc("useCount"));
         crit.addOrder(Order.asc("priority"));
       }
     } else {
       crit.addOrder(Order.asc("priority"));
       crit.addOrder(Order.asc("id"));
     }
     return findByCriteria(crit);
   }
 
   public List<Depart> getAllList(Integer siteId) {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("site.id", siteId));
     crit.addOrder(Order.asc("priority"));
     crit.addOrder(Order.asc("id"));
     return findByCriteria(crit);
   }
 
   public List<Depart> getListNoParent(Integer siteId) {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("site.id", siteId));
     crit.add(Restrictions.isNull("parent"));
     crit.addOrder(Order.asc("priority"));
     crit.addOrder(Order.asc("id"));
     return findByCriteria(crit);
   }
 
   public List<Depart> getListByParent(Integer parentId) {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("parent.id", parentId));
     crit.addOrder(Order.asc("priority"));
     crit.addOrder(Order.asc("id"));
     return findByCriteria(crit);
   }
 
   public Depart getDepartByPath(String path, Integer siteId) {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("site.id", siteId));
     crit.add(Restrictions.eq("visitPath", path));
     return (Depart)findUnique(crit);
   }
 
   public Depart getDepartByName(String name, Integer siteId) {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("site.id", siteId));
     crit.add(Restrictions.eq("name", name));
     return (Depart)findUnique(crit);
   }
 
   public int deleteBySiteId(Integer siteId) {
     String hql = "delete from Depart bean where bean.site.id=?";
     return executeQuery(hql, new Object[] { siteId });
   }
 
   protected Class<Depart> getEntityClass()
   {
     return Depart.class;
   }
 }


 
 
 
 package com.portal.usermgr.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.usermgr.dao.GroupDao;
 import com.portal.usermgr.entity.Group;
 import java.util.List;
 import org.apache.commons.lang.StringUtils;
 import org.hibernate.Criteria;
 import org.hibernate.Session;
 import org.hibernate.criterion.Order;
 import org.hibernate.criterion.Restrictions;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class GroupDaoImpl extends QueryDaoImpl<Group, Integer>
   implements GroupDao
 {
   public List<Group> getList(Integer siteId, String sortname, String sortorder)
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
       crit.addOrder(Order.asc("id"));
     }
     return findByCriteria(crit);
   }
 
   public Group findById(Integer id) {
     Group entity = (Group)get(id);
     return entity;
   }
 
   public Group save(Group bean) {
     getSession().save(bean);
     return bean;
   }
 
   public Group deleteById(Integer id) {
     Group entity = (Group)super.get(id);
     if (entity != null) {
       getSession().delete(entity);
     }
     return entity;
   }
 
   protected Class<Group> getEntityClass()
   {
     return Group.class;
   }
 }


 
 
 
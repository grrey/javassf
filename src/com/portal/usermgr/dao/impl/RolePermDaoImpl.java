 package com.portal.usermgr.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.usermgr.dao.RolePermDao;
 import com.portal.usermgr.entity.RolePerm;
 import org.hibernate.Criteria;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class RolePermDaoImpl extends QueryDaoImpl<RolePerm, Integer>
   implements RolePermDao
 {
   public Page<RolePerm> getPage(int pageNo, int pageSize)
   {
     Criteria crit = createCriteria();
     return findByCriteria(crit, pageNo, pageSize);
   }
 
   public int deleteBySiteId(Integer siteId) {
     String hql = "delete from RolePerm bean where bean.id in (select r.id from Role r inner join r.site s where s.id=?)";
 
     return executeQuery(hql, new Object[] { siteId });
   }
 
   protected Class<RolePerm> getEntityClass()
   {
     return RolePerm.class;
   }
 }


 
 
 
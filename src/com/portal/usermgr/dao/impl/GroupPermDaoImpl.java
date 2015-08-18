 package com.portal.usermgr.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.usermgr.dao.GroupPermDao;
 import com.portal.usermgr.entity.GroupPerm;
 import org.hibernate.Criteria;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class GroupPermDaoImpl extends QueryDaoImpl<GroupPerm, Integer>
   implements GroupPermDao
 {
   public Page<GroupPerm> getPage(int pageNo, int pageSize)
   {
     Criteria crit = createCriteria();
     return findByCriteria(crit, pageNo, pageSize);
   }
 
   protected Class<GroupPerm> getEntityClass()
   {
     return GroupPerm.class;
   }
 }


 
 
 
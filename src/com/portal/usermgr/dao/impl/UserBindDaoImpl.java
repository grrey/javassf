 package com.portal.usermgr.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.usermgr.dao.UserBindDao;
 import com.portal.usermgr.entity.UserBind;
 import org.hibernate.Criteria;
 import org.hibernate.criterion.Restrictions;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class UserBindDaoImpl extends QueryDaoImpl<UserBind, Integer>
   implements UserBindDao
 {
   public UserBind getBindByUser(Integer userId, Integer status)
   {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("user.id", userId));
     crit.add(Restrictions.eq("status", status));
     return (UserBind)findUnique(crit);
   }
 
   protected Class<UserBind> getEntityClass()
   {
     return UserBind.class;
   }
 }


 
 
 
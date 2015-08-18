 package com.portal.usermgr.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.usermgr.dao.UserDao;
 import com.portal.usermgr.entity.User;
 import org.hibernate.Criteria;
 import org.hibernate.criterion.Projections;
 import org.hibernate.criterion.Restrictions;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class UserDaoImpl extends QueryDaoImpl<User, Integer>
   implements UserDao
 {
   public int getAllUserCount()
   {
     Criteria crit = createCriteria();
     crit.setProjection(Projections.count("id"));
     return ((Number)crit.uniqueResult()).intValue();
   }
 
   public User findByUsername(String username) {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("username", username));
     return (User)findUnique(crit);
   }
 
   protected Class<User> getEntityClass()
   {
     return User.class;
   }
 }


 
 
 
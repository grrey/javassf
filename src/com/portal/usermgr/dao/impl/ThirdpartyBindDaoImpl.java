 package com.portal.usermgr.dao.impl;
 
 import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.javassf.basic.hibernate4.QueryDaoImpl;
import com.portal.usermgr.dao.ThirdpartyBindDao;
import com.portal.usermgr.entity.ThirdpartyBind;
 
 @Repository
 public class ThirdpartyBindDaoImpl extends QueryDaoImpl<ThirdpartyBind, Integer>
   implements ThirdpartyBindDao
 {
   public ThirdpartyBind findByOpenId(String openid, String bindType)
   {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("openid", openid));
     crit.add(Restrictions.eq("bindType", bindType));
     return (ThirdpartyBind)findUnique(crit);
   }
 
   protected Class<ThirdpartyBind> getEntityClass()
   {
     return ThirdpartyBind.class;
   }
 }


 
 
 
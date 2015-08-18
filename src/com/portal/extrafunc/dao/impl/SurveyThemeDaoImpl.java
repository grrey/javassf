 package com.portal.extrafunc.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.extrafunc.dao.SurveyThemeDao;
 import com.portal.extrafunc.entity.SurveyTheme;
 import java.util.List;
 import org.hibernate.Criteria;
 import org.hibernate.criterion.Order;
 import org.hibernate.criterion.Restrictions;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class SurveyThemeDaoImpl extends QueryDaoImpl<SurveyTheme, Integer>
   implements SurveyThemeDao
 {
   public Page<SurveyTheme> getPage(Integer naireId, int pageNo, int pageSize)
   {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("naire.id", naireId));
     crit.addOrder(Order.asc("priority"));
     return findByCriteria(crit, pageNo, pageSize);
   }
 
   public List<SurveyTheme> getList(Integer naireId) {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("naire.id", naireId));
     crit.addOrder(Order.asc("priority"));
     return findByCriteria(crit);
   }
 
   protected Class<SurveyTheme> getEntityClass()
   {
     return SurveyTheme.class;
   }
 }


 
 
 
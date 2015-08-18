 package com.portal.extrafunc.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.extrafunc.dao.QuestionnaireDao;
 import com.portal.extrafunc.entity.Questionnaire;
 import org.hibernate.Criteria;
 import org.hibernate.criterion.Restrictions;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class QuestionnaireDaoImpl extends QueryDaoImpl<Questionnaire, Integer>
   implements QuestionnaireDao
 {
   public Page<Questionnaire> getPage(Integer siteId, boolean enable, int pageNo, int pageSize)
   {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("site.id", siteId));
     if (enable) {
       crit.add(Restrictions.eq("enable", Boolean.valueOf(true)));
     }
     return findByCriteria(crit, pageNo, pageSize);
   }
 
   protected Class<Questionnaire> getEntityClass()
   {
     return Questionnaire.class;
   }
 }


 
 
 
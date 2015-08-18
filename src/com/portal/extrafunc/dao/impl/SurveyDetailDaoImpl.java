 package com.portal.extrafunc.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.extrafunc.dao.SurveyDetailDao;
 import com.portal.extrafunc.entity.SurveyDetail;
 import org.hibernate.Criteria;
 import org.hibernate.criterion.Order;
 import org.hibernate.criterion.Restrictions;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class SurveyDetailDaoImpl extends QueryDaoImpl<SurveyDetail, Integer>
   implements SurveyDetailDao
 {
   public Page<SurveyDetail> getPage(Integer surveyId, int pageNo, int pageSize)
   {
     Criteria crit = createCriteria();
     if (surveyId != null) {
       crit.add(Restrictions.eq("survey.id", surveyId));
     }
     crit.addOrder(Order.desc("createTime"));
     return findByCriteria(crit, pageNo, pageSize);
   }
 
   public SurveyDetail getDetailByUser(Integer surveyId, Integer userId) {
     Criteria crit = createCriteria();
     if (surveyId != null) {
       crit.add(Restrictions.eq("survey.id", surveyId));
     }
     if (userId != null) {
       crit.add(Restrictions.eq("user.id", surveyId));
     }
     return (SurveyDetail)findUnique(crit);
   }
 
   protected Class<SurveyDetail> getEntityClass()
   {
     return SurveyDetail.class;
   }
 }


 
 
 
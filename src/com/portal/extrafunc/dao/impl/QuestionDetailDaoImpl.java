 package com.portal.extrafunc.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.extrafunc.dao.QuestionDetailDao;
 import com.portal.extrafunc.entity.QuestionDetail;
 import org.apache.commons.lang.StringUtils;
 import org.hibernate.Criteria;
 import org.hibernate.criterion.Order;
 import org.hibernate.criterion.Projections;
 import org.hibernate.criterion.Restrictions;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class QuestionDetailDaoImpl extends QueryDaoImpl<QuestionDetail, Integer>
   implements QuestionDetailDao
 {
   public Page<QuestionDetail> getPage(Integer questionId, int pageNo, int pageSize)
   {
     Criteria crit = createCriteria();
     if (questionId != null) {
       crit.add(Restrictions.eq("question.id", questionId));
     }
     return findByCriteria(crit, pageNo, pageSize);
   }
 
   public QuestionDetail getDetail(Integer questionId, Integer userId, String ip)
   {
     Criteria crit = createCriteria();
     if (questionId != null) {
       crit.add(Restrictions.eq("question.id", questionId));
     }
     if (userId != null) {
       crit.add(Restrictions.eq("user.id", userId));
     }
     if (!StringUtils.isBlank(ip)) {
       crit.add(Restrictions.eq("ip", ip));
     }
     crit.addOrder(Order.desc("createTime"));
     return (QuestionDetail)findUnique(crit);
   }
 
   public long getCountDetail(Integer questionId) {
     Criteria crit = createCriteria();
     if (questionId != null) {
       crit.add(Restrictions.eq("question.id", questionId));
     }
     crit.setProjection(Projections.count("id"));
     return ((Long)crit.uniqueResult()).longValue();
   }
 
   protected Class<QuestionDetail> getEntityClass()
   {
     return QuestionDetail.class;
   }
 }


 
 
 
 package com.portal.datacenter.commdata.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.datacenter.commdata.dao.ProfessPostDao;
 import com.portal.datacenter.commdata.entity.ProfessPost;
 import java.util.List;
 import org.hibernate.Criteria;
 import org.hibernate.criterion.Order;
 import org.hibernate.criterion.Restrictions;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class ProfessPostDaoImpl extends QueryDaoImpl<ProfessPost, Integer>
   implements ProfessPostDao
 {
   public Page<ProfessPost> getPage(int pageNo, int pageSize)
   {
     Criteria crit = createCriteria();
     crit.addOrder(Order.asc("code"));
     return findByCriteria(crit, pageNo, pageSize);
   }
 
   public List<ProfessPost> getProfessPostList(Integer id) {
     Criteria crit = createCriteria();
     if (id != null) {
       crit.add(Restrictions.ne("id", id));
     }
     crit.addOrder(Order.asc("code"));
     return findByCriteria(crit);
   }
 
   protected Class<ProfessPost> getEntityClass()
   {
     return ProfessPost.class;
   }
 }

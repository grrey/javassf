 package com.portal.doccenter.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.doccenter.dao.ArticleTypeDao;
 import com.portal.doccenter.entity.ArticleType;
 import java.util.List;
 import org.apache.commons.lang.StringUtils;
 import org.hibernate.Criteria;
 import org.hibernate.criterion.Order;
 import org.hibernate.criterion.Restrictions;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class ArticleTypeDaoImpl extends QueryDaoImpl<ArticleType, Integer>
   implements ArticleTypeDao
 {
   public List<ArticleType> getList(boolean containDisabled, String sortname, String sortorder)
   {
     Criteria crit = createCriteria();
     if (!containDisabled) {
       crit.add(Restrictions.eq("disabled", Boolean.valueOf(false)));
     }
     if (!StringUtils.isBlank(sortname)) {
       if ("asc".equals(sortorder))
         crit.addOrder(Order.asc(sortname));
       else
         crit.addOrder(Order.desc(sortname));
     }
     else {
       crit.addOrder(Order.asc("id"));
     }
     return findByCriteria(crit);
   }
 
   public ArticleType getDef() {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("disabled", Boolean.valueOf(false)));
     crit.addOrder(Order.asc("id"));
     return (ArticleType)findUnique(crit);
   }
 
   protected Class<ArticleType> getEntityClass()
   {
     return ArticleType.class;
   }
 }


 
 
 
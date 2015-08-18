 package com.portal.extrafunc.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.extrafunc.dao.ForumDao;
 import com.portal.extrafunc.entity.Forum;
 import java.util.List;
 import org.apache.commons.lang.StringUtils;
 import org.hibernate.Criteria;
 import org.hibernate.criterion.Order;
 import org.hibernate.criterion.Restrictions;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class ForumDaoImpl extends QueryDaoImpl<Forum, Integer>
   implements ForumDao
 {
   public Page<Forum> getPage(Integer siteId, Integer categoryId, String sortname, String sortorder, int pageNo, int pageSize)
   {
     Criteria crit = createCriteria();
     if (siteId != null) {
       crit.add(Restrictions.eq("site.id", siteId));
     }
     if (categoryId != null) {
       crit.add(Restrictions.eq("category.id", categoryId));
     }
     if (!StringUtils.isBlank(sortname)) {
       if ("asc".equals(sortorder))
         crit.addOrder(Order.asc(sortname));
       else
         crit.addOrder(Order.desc(sortname));
     }
     else {
       crit.addOrder(Order.asc("priority"));
     }
     return findByCriteria(crit, pageNo, pageSize);
   }
 
   public List<Forum> getList(Integer categoryId) {
     Criteria crit = createCriteria();
     if (categoryId != null) {
       crit.add(Restrictions.eq("category.id", categoryId));
     }
     crit.addOrder(Order.asc("priority"));
     return findByCriteria(crit);
   }
 
   public List<Forum> getList() {
     Criteria crit = createCriteria();
     crit.addOrder(Order.asc("priority"));
     return findByCriteria(crit);
   }
 
   public int deleteByCategoryId(Integer categoryId) {
     String hql = "delete from Forum bean where bean.category.id=?";
     return executeQuery(hql, new Object[] { categoryId });
   }
 
   public int deleteBySiteId(Integer siteId) {
     String hql = "delete from Forum bean where bean.site.id=?";
     return executeQuery(hql, new Object[] { siteId });
   }
 
   protected Class<Forum> getEntityClass()
   {
     return Forum.class;
   }
 }


 
 
 
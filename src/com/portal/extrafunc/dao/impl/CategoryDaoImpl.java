 package com.portal.extrafunc.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.extrafunc.dao.CategoryDao;
 import com.portal.extrafunc.entity.Category;
 import java.util.List;
 import org.apache.commons.lang.StringUtils;
 import org.hibernate.Criteria;
 import org.hibernate.criterion.Order;
 import org.hibernate.criterion.Restrictions;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class CategoryDaoImpl extends QueryDaoImpl<Category, Integer>
   implements CategoryDao
 {
   public Page<Category> getPage(String sortname, String sortorder, int pageNo, int pageSize)
   {
     Criteria crit = createCriteria();
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
 
   public List<Category> getList(Integer siteId, String sortname, String sortorder)
   {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("site.id", siteId));
     if (!StringUtils.isBlank(sortname)) {
       if ("asc".equals(sortorder))
         crit.addOrder(Order.asc(sortname));
       else
         crit.addOrder(Order.desc(sortname));
     }
     else {
       crit.addOrder(Order.asc("priority"));
     }
     return findByCriteria(crit);
   }
 
   public int deleteBySiteId(Integer siteId) {
     String hql = "delete from Category bean where bean.site.id=?";
     return executeQuery(hql, new Object[] { siteId });
   }
 
   protected Class<Category> getEntityClass()
   {
     return Category.class;
   }
 }


 
 
 
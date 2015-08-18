 package com.portal.doccenter.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.doccenter.dao.WorkFlowDao;
 import com.portal.doccenter.entity.WorkFlow;
 import java.util.List;
 import org.apache.commons.lang.StringUtils;
 import org.hibernate.Criteria;
 import org.hibernate.criterion.Order;
 import org.hibernate.criterion.Restrictions;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class WorkFlowDaoImpl extends QueryDaoImpl<WorkFlow, Integer>
   implements WorkFlowDao
 {
   public Page<WorkFlow> getPage(Integer siteId, String sortname, String sortorder, int pageNo, int pageSize)
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
       crit.addOrder(Order.asc("id"));
     }
     return findByCriteria(crit, pageNo, pageSize);
   }
 
   public List<WorkFlow> findByList(Integer siteId) {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("site.id", siteId));
     crit.addOrder(Order.desc("createTime"));
     return findByCriteria(crit);
   }
 
   protected Class<WorkFlow> getEntityClass()
   {
     return WorkFlow.class;
   }
 }


 
 
 
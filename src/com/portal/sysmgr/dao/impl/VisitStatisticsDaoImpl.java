 package com.portal.sysmgr.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.javassf.basic.utils.DateUtils;
 import com.portal.sysmgr.dao.VisitStatisticsDao;
 import com.portal.sysmgr.entity.VisitStatistics;
 import java.util.Date;
 import org.hibernate.Criteria;
 import org.hibernate.criterion.Projections;
 import org.hibernate.criterion.Restrictions;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class VisitStatisticsDaoImpl extends QueryDaoImpl<VisitStatistics, Integer>
   implements VisitStatisticsDao
 {
   public Page<VisitStatistics> getStatisticsByDate(Integer siteId, Date start, Date end, int pageNo, int pageSize)
   {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("site.id", siteId));
     crit.add(Restrictions.between("visitTime", start, end));
     return findByCriteria(crit, pageNo, pageSize);
   }
 
   public Page<VisitStatistics> getStatisticsByToday(Integer siteId, int pageNo, int pageSize)
   {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("site.id", siteId));
     crit.add(Restrictions.between("visitTime", DateUtils.getToday(), 
       DateUtils.getEndOfDay()));
     return findByCriteria(crit, pageNo, pageSize);
   }
 
   public Page<VisitStatistics> getStatisticsByHour(Integer siteId, Integer hour, int pageNo, int pageSize)
   {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("site.id", siteId));
     crit.add(Restrictions.between("visitTime", DateUtils.getToday(), 
       DateUtils.getEndOfDay()));
     return findByCriteria(crit, pageNo, pageSize);
   }
 
   public long getStatisticsByDate(Integer siteId, Date start, Date end) {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("site.id", siteId));
     crit.add(Restrictions.between("visitTime", start, end));
     crit.setProjection(Projections.count("id"));
     return ((Long)crit.uniqueResult()).longValue();
   }
 
   public long getStatisticsByToday(Integer siteId) {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("site.id", siteId));
     crit.add(Restrictions.between("visitTime", DateUtils.getToday(), 
       DateUtils.getEndOfDay()));
     crit.setProjection(Projections.count("id"));
     return ((Long)crit.uniqueResult()).longValue();
   }
 
   public long getStatisticsByHour(Integer siteId, Integer hour) {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("site.id", siteId));
     crit.add(Restrictions.between("visitTime", DateUtils.getToday(), 
       DateUtils.getEndOfDay()));
     crit.add(Restrictions.eq("visitHour", hour));
     crit.setProjection(Projections.count("id"));
     return ((Long)crit.uniqueResult()).longValue();
   }
 
   public long getStatisticsByMin(Integer siteId, Integer hour, Integer min) {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("site.id", siteId));
     crit.add(Restrictions.between("visitTime", DateUtils.getToday(), 
       DateUtils.getEndOfDay()));
     crit.add(Restrictions.eq("visitHour", hour));
     crit.add(Restrictions.eq("visitMin", min));
     crit.setProjection(Projections.count("id"));
     return ((Long)crit.uniqueResult()).longValue();
   }
 
   protected Class<VisitStatistics> getEntityClass()
   {
     return VisitStatistics.class;
   }
 }


 
 
 
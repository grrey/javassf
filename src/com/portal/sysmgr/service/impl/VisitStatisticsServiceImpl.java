 package com.portal.sysmgr.service.impl;
 
 import com.portal.sysmgr.dao.VisitStatisticsDao;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.entity.VisitStatistics;
 import com.portal.sysmgr.service.VisitStatisticsService;
 import java.util.Date;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class VisitStatisticsServiceImpl
   implements VisitStatisticsService
 {
   private VisitStatisticsDao dao;
 
   @Transactional(readOnly=true)
   public Page<VisitStatistics> getPage(int pageNo, int pageSize)
   {
     return this.dao.getPage(pageNo, pageSize);
   }
 
   @Transactional(readOnly=true)
   public Page<VisitStatistics> getStatisticsByDate(Integer siteId, Date start, Date end, int pageNo, int pageSize) {
     return this.dao.getStatisticsByDate(siteId, start, end, pageNo, pageSize);
   }
 
   @Transactional(readOnly=true)
   public Page<VisitStatistics> getStatisticsByToday(Integer siteId, int pageNo, int pageSize) {
     return this.dao.getStatisticsByToday(siteId, pageNo, pageSize);
   }
 
   @Transactional(readOnly=true)
   public Page<VisitStatistics> getStatisticsByHour(Integer siteId, Integer hour, int pageNo, int pageSize) {
     return this.dao.getStatisticsByHour(siteId, hour, pageNo, pageSize);
   }
   @Transactional(readOnly=true)
   public long getStatisticsByDate(Integer siteId, Date start, Date end) {
     return this.dao.getStatisticsByDate(siteId, start, end);
   }
   @Transactional(readOnly=true)
   public long getStatisticsByToday(Integer siteId) {
     return this.dao.getStatisticsByToday(siteId);
   }
   @Transactional(readOnly=true)
   public long getStatisticsByHour(Integer siteId, Integer hour) {
     return this.dao.getStatisticsByHour(siteId, hour);
   }
   @Transactional(readOnly=true)
   public long getStatisticsByMin(Integer siteId, Integer hour, Integer min) {
     return this.dao.getStatisticsByMin(siteId, hour, min);
   }
   @Transactional(readOnly=true)
   public VisitStatistics findById(Integer id) {
     VisitStatistics entity = (VisitStatistics)this.dao.findById(id);
     return entity;
   }
 
   public VisitStatistics save(VisitStatistics bean) {
     this.dao.save(bean);
     return bean;
   }
 
   public VisitStatistics save(Site site, String url, String ip, String cookie) {
     VisitStatistics bean = new VisitStatistics();
     bean.setIp(ip);
     bean.setUrl(url);
     bean.setCookie(cookie);
     bean.setSite(site);
     bean.init();
     this.dao.save(bean);
     return bean;
   }
 
   public VisitStatistics update(VisitStatistics bean) {
     bean = (VisitStatistics)this.dao.update(bean);
     return bean;
   }
 
   public VisitStatistics deleteById(Integer id) {
     VisitStatistics bean = (VisitStatistics)this.dao.deleteById(id);
     return bean;
   }
 
   public VisitStatistics[] deleteByIds(Integer[] ids) {
     VisitStatistics[] beans = new VisitStatistics[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   @Autowired
   public void setDao(VisitStatisticsDao dao)
   {
     this.dao = dao;
   }
 }


 
 
 
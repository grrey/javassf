package com.portal.sysmgr.service;

import com.portal.sysmgr.entity.Site;
import com.portal.sysmgr.entity.VisitStatistics;
import java.util.Date;
import org.springframework.data.domain.Page;

public abstract interface VisitStatisticsService
{
  public abstract Page<VisitStatistics> getPage(int paramInt1, int paramInt2);

  public abstract Page<VisitStatistics> getStatisticsByDate(Integer paramInteger, Date paramDate1, Date paramDate2, int paramInt1, int paramInt2);

  public abstract Page<VisitStatistics> getStatisticsByToday(Integer paramInteger, int paramInt1, int paramInt2);

  public abstract Page<VisitStatistics> getStatisticsByHour(Integer paramInteger1, Integer paramInteger2, int paramInt1, int paramInt2);

  public abstract long getStatisticsByDate(Integer paramInteger, Date paramDate1, Date paramDate2);

  public abstract long getStatisticsByToday(Integer paramInteger);

  public abstract long getStatisticsByHour(Integer paramInteger1, Integer paramInteger2);

  public abstract long getStatisticsByMin(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3);

  public abstract VisitStatistics findById(Integer paramInteger);

  public abstract VisitStatistics save(VisitStatistics paramVisitStatistics);

  public abstract VisitStatistics save(Site paramSite, String paramString1, String paramString2, String paramString3);

  public abstract VisitStatistics update(VisitStatistics paramVisitStatistics);

  public abstract VisitStatistics deleteById(Integer paramInteger);

  public abstract VisitStatistics[] deleteByIds(Integer[] paramArrayOfInteger);
}


 
 
 
package com.portal.datacenter.commdata.dao;

import com.javassf.basic.hibernate4.QueryDao;
import com.portal.datacenter.commdata.entity.ProfessPost;
import java.util.List;
import org.springframework.data.domain.Page;

public abstract interface ProfessPostDao extends QueryDao<ProfessPost>
{
  public abstract Page<ProfessPost> getPage(int paramInt1, int paramInt2);

  public abstract List<ProfessPost> getProfessPostList(Integer paramInteger);
}

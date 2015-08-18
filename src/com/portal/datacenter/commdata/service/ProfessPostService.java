package com.portal.datacenter.commdata.service;

import com.portal.datacenter.commdata.entity.ProfessPost;
import java.util.List;
import org.springframework.data.domain.Page;

public abstract interface ProfessPostService
{
  public abstract Page<ProfessPost> getPage(int paramInt1, int paramInt2);

  public abstract List<ProfessPost> getProfessPostList(Integer paramInteger);

  public abstract ProfessPost findById(Integer paramInteger);

  public abstract ProfessPost save(ProfessPost paramProfessPost);

  public abstract ProfessPost update(ProfessPost paramProfessPost);

  public abstract ProfessPost deleteById(Integer paramInteger);

  public abstract ProfessPost[] deleteByIds(Integer[] paramArrayOfInteger);
}


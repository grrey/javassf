package com.portal.extrafunc.service;

import com.portal.extrafunc.entity.Category;
import com.portal.sysmgr.entity.Site;
import java.util.List;
import org.springframework.data.domain.Page;

public abstract interface CategoryService
{
  public abstract Page<Category> getPage(String paramString1, String paramString2, int paramInt1, int paramInt2);

  public abstract List<Category> getList(Integer paramInteger, String paramString1, String paramString2);

  public abstract Category findById(Integer paramInteger);

  public abstract Category save(Category paramCategory, Site paramSite);

  public abstract Category update(Category paramCategory);

  public abstract int deleteBySiteId(Integer paramInteger);

  public abstract Category deleteById(Integer paramInteger);

  public abstract Category[] deleteByIds(Integer[] paramArrayOfInteger);
}


 
 
 
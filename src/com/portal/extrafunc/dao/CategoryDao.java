package com.portal.extrafunc.dao;

import com.javassf.basic.hibernate4.QueryDao;
import com.portal.extrafunc.entity.Category;
import java.util.List;
import org.springframework.data.domain.Page;

public abstract interface CategoryDao extends QueryDao<Category>
{
  public abstract Page<Category> getPage(String paramString1, String paramString2, int paramInt1, int paramInt2);

  public abstract List<Category> getList(Integer paramInteger, String paramString1, String paramString2);

  public abstract int deleteBySiteId(Integer paramInteger);
}


 
 
 
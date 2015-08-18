package com.portal.usermgr.dao;

import com.javassf.basic.hibernate4.QueryDao;
import com.portal.usermgr.entity.Admin;
import java.util.List;
import org.springframework.data.domain.Page;

public abstract interface AdminDao extends QueryDao<Admin>
{
  public abstract Page<Admin> getPage(int paramInt1, int paramInt2);

  public abstract Page<Admin> getPage(String paramString1, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, String paramString2, String paramString3, int paramInt1, int paramInt2);

  public abstract List<Admin> getListByDepart(Integer paramInteger);

  public abstract List<Admin> getListByRole(Integer paramInteger);
}


 
 
 
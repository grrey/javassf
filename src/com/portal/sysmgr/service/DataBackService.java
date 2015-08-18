package com.portal.sysmgr.service;

import java.sql.SQLException;
import java.util.List;

public abstract interface DataBackService
{
  public abstract List<Object[]> getCreateTableData(String paramString);

  public abstract String getWholeTableSql(String paramString);

  public abstract List<String> getTables()
    throws SQLException;

  public abstract String getCreateSQL(String paramString)
    throws SQLException;

  public abstract String getAllExportedKeys(String paramString)
    throws SQLException;

  public abstract String getInsertSql(Object[] paramArrayOfObject, String paramString);

  public abstract String getDefaultCatalog()
    throws SQLException;

  public abstract Boolean executeSQL(String paramString);
}


 
 
 
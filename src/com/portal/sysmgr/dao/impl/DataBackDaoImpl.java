 package com.portal.sysmgr.dao.impl;
 
 import com.portal.sysmgr.dao.DataBackDao;
 import java.sql.Connection;
 import java.sql.DatabaseMetaData;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import java.util.List;
 import javax.sql.DataSource;
 import org.apache.commons.lang.StringUtils;
 import org.springframework.jdbc.core.JdbcTemplate;
 import org.springframework.jdbc.core.support.JdbcDaoSupport;
 import org.springframework.jdbc.support.rowset.SqlRowSet;
 
 public class DataBackDaoImpl extends JdbcDaoSupport
   implements DataBackDao
 {
   public List<Object[]> getCreateTableData(String tablename)
   {
     int filedNum = getFieldNum(tablename);
     List results = new ArrayList();
     String sql = "select * from " + tablename;
     SqlRowSet set = getJdbcTemplate().queryForRowSet(sql);
     while (set.next()) {
       Object[] oneResult = new Object[filedNum];
       for (int i = 1; i <= filedNum; i++) {
         oneResult[(i - 1)] = set.getObject(i);
       }
       results.add(oneResult);
     }
     return results;
   }
 
   public DatabaseMetaData getMetaData() throws SQLException {
     return getJdbcTemplate().getDataSource().getConnection().getMetaData();
   }
 
   public String getDefaultCatalog() throws SQLException {
     return getJdbcTemplate().getDataSource().getConnection().getCatalog();
   }
 
   public Boolean executeSQL(String sqls) {
     try {
       String[] s = sqls.split(";--end");
       for (String sql : s)
         if (StringUtils.isNotBlank(sql))
           getJdbcTemplate().execute(sql.trim() + ";");
     }
     catch (Exception e)
     {
       e.printStackTrace();
       return Boolean.valueOf(false);
     }
     return Boolean.valueOf(true);
   }
 
   private int getFieldNum(String tablename) {
     int num = 0;
     try {
       ResultSet rs = getMetaData().getColumns(getDefaultCatalog(), 
         getMetaData().getUserName(), tablename, null);
       while (rs.next()) {
         num++;
       }
       rs.close();
     } catch (SQLException e) {
       e.printStackTrace();
     }
     return num;
   }
 }


 
 
 
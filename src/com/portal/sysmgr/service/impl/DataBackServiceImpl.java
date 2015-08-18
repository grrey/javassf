 package com.portal.sysmgr.service.impl;
 
 import com.javassf.basic.utils.StringBeanUtils;
 import com.portal.sysmgr.dao.DataBackDao;
 import com.portal.sysmgr.service.DataBackService;
 import java.sql.DatabaseMetaData;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import java.util.Date;
 import java.util.List;
 import org.apache.commons.lang.StringUtils;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class DataBackServiceImpl
   implements DataBackService
 {
   private DataBackDao dao;
 
   @Transactional(readOnly=true)
   public List<Object[]> getCreateTableData(String tablename)
   {
     return this.dao.getCreateTableData(tablename);
   }
   @Transactional(readOnly=true)
   public String getDefaultCatalog() throws SQLException {
     return this.dao.getDefaultCatalog();
   }
 
   public String getWholeTableSql(String tablename) {
     StringBuffer sb = new StringBuffer();
     try
     {
       sb.append(getCreateSQL(tablename));
     } catch (SQLException e) {
       e.printStackTrace();
     }
     List results = getCreateTableData(tablename);
     for (int i = 0; i < results.size(); i++) {
       Object[] oneResult = (Object[])results.get(i);
       sb.append(getInsertSql(oneResult, tablename));
     }
     return sb.toString();
   }
 
   public List<String> getTables()
     throws SQLException
   {
     DatabaseMetaData d = this.dao.getMetaData();
     String c = this.dao.getDefaultCatalog();
     ResultSet rs = d.getTables(c, d.getUserName(), "tq_%", 
       new String[] { "TABLE" });
     List tables = new ArrayList();
     try {
       while (rs.next())
         tables.add(rs.getString("TABLE_NAME"));
     }
     catch (SQLException e) {
       e.printStackTrace();
     } finally {
       rs.close();
     }
     return tables;
   }
 
   public String getCreateSQL(String table) throws SQLException {
     DatabaseMetaData d = this.dao.getMetaData();
     String c = this.dao.getDefaultCatalog();
     ResultSet rs = d.getColumns(c, d.getUserName(), table, null);
     StringBuffer sb = new StringBuffer("");
     sb.append("DROP TABLE IF EXISTS ");
     sb.append(table);
     sb.append(";--end");
     sb.append("\r\n");
     sb.append("CREATE TABLE ");
     sb.append(table);
     sb.append("\r\n");
     sb.append("(");
     sb.append("\r\n");
     try {
       label332: while (rs.next()) {
         sb.append("   ");
         sb.append(rs.getString("COLUMN_NAME"));
         sb.append("           ");
         sb.append(rs.getString("TYPE_NAME"));
         if ((rs.getString("TYPE_NAME").indexOf("DATE") == -1) && 
           (rs.getString("TYPE_NAME").indexOf("TIME") == -1) && 
           (rs.getString("TYPE_NAME").indexOf("TEXT") == -1) && 
           (StringUtils.isNotBlank(rs.getString("COLUMN_SIZE")))) {
           sb.append("(");
           if (StringUtils.isNotBlank(rs
             .getString("DECIMAL_DIGITS")))
             if (!rs.getString("DECIMAL_DIGITS").equals("0")) {
               sb.append(rs.getString("COLUMN_SIZE"));
               sb.append(",");
               sb.append(rs.getString("DECIMAL_DIGITS")); break label332;
             }
           sb.append(rs.getString("COLUMN_SIZE"));
 
           sb.append(")");
         }
 
         if (rs.getString("NULLABLE").equals("0")) {
           sb.append(" NOT NULL");
         }
         if (StringUtils.isNotBlank(rs.getString("COLUMN_DEF"))) {
           sb.append(" DEFAULT ");
           if (rs.getString("TYPE_NAME").indexOf("BIT") == -1) {
             sb.append("'");
           }
           sb.append(rs.getString("COLUMN_DEF"));
           if (rs.getString("TYPE_NAME").indexOf("BIT") == -1) {
             sb.append("'");
           }
         }
         if (rs.getString("IS_AUTOINCREMENT").equals("YES")) {
           sb.append(" AUTO_INCREMENT");
         }
         if (StringUtils.isNotBlank(rs.getString("REMARKS"))) {
           sb.append(" COMMENT ");
           sb.append("'");
           sb.append(rs.getString("REMARKS"));
           sb.append("'");
         }
         if (!rs.isLast()) {
           sb.append(",");
           sb.append("\r\n");
         }
       }
       String sp = getPrimaryKeys(table, d, c);
       if (StringUtils.isNotBlank(sp)) {
         sb.append(",");
         sb.append("\r\n");
         sb.append(sp);
       } else {
         sb.append("\r\n");
       }
       sb.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8");
       sb.append(";--end");
       sb.append("\r\n");
     } catch (SQLException localSQLException) {
     } finally {
       rs.close();
     }
     return sb.toString();
   }
 
   private String getPrimaryKeys(String tablename, DatabaseMetaData d, String c)
     throws SQLException
   {
     ResultSet rs = d.getPrimaryKeys(c, d.getUserName(), tablename);
     StringBuffer sb = new StringBuffer("");
     boolean b = false;
     try {
       sb.append("   PRIMARY KEY");
       sb.append("(");
       while (rs.next()) {
         sb.append(rs.getString("COLUMN_NAME"));
         sb.append(",");
         b = true;
       }
       if (!b) {
         rs.close();
         return "";
       }
       sb.deleteCharAt(sb.lastIndexOf(","));
       sb.append(")");
       sb.append("\r\n");
     } catch (SQLException localSQLException) {
     } finally {
       rs.close();
     }
     return sb.toString();
   }
 
   public String getAllExportedKeys(String tableName)
     throws SQLException
   {
     DatabaseMetaData d = this.dao.getMetaData();
     String c = this.dao.getDefaultCatalog();
     ResultSet rs = d.getImportedKeys(c, d.getUserName(), tableName);
     StringBuffer sb = new StringBuffer("");
     try {
       while (rs.next()) {
         sb.append("ALTER TABLE ");
         sb.append(rs.getString("FKTABLE_NAME"));
         sb.append(" ADD CONSTRAINT ");
         sb.append(rs.getString("FK_NAME"));
         sb.append(" FOREIGN KEY ");
         sb.append("(");
         sb.append(rs.getString("FKCOLUMN_NAME"));
         sb.append(")");
         sb.append(" REFERENCES ");
         sb.append(rs.getString("PKTABLE_NAME"));
         sb.append(" (");
         sb.append(rs.getString("PKCOLUMN_NAME"));
         sb.append(")");
         sb.append(";--end");
         sb.append("\r\n");
       }
     } catch (SQLException e) {
       e.printStackTrace();
     } finally {
       rs.close();
     }
     return sb.toString();
   }
 
   public String getInsertSql(Object[] o, String tablename) {
     StringBuffer sb = new StringBuffer();
     sb.append(" INSERT INTO ");
     sb.append(tablename);
     sb.append(" VALUES(");
     for (int i = 0; i < o.length; i++) {
       if (o[i] != null) {
         if ((o[i] instanceof Date))
           sb.append("'" + o[i] + "'");
         else if ((o[i] instanceof String))
           sb.append("'" + 
             StringBeanUtils.replaceKeyString((String)o[i]) + 
             "'");
         else if ((o[i] instanceof Boolean)) {
           if (((Boolean)o[i]).booleanValue())
             sb.append(1);
           else
             sb.append(0);
         }
         else
           sb.append(o[i]);
       }
       else {
         sb.append(o[i]);
       }
       sb.append(",");
     }
     sb = sb.deleteCharAt(sb.lastIndexOf(","));
     sb.append(")");
     sb.append(";--end");
     sb.append("\r\n");
     return sb.toString();
   }
 
   public Boolean executeSQL(String sql) {
     return this.dao.executeSQL(sql);
   }
 
   @Autowired
   public void setDao(DataBackDao dao)
   {
     this.dao = dao;
   }
 }


 
 
 
package com.portal.sysmgr.action;

import com.javassf.basic.plugin.springmvc.RealPathResolver;
import com.javassf.basic.utils.DateUtils;
import com.javassf.basic.utils.ResponseUtils;
import com.portal.sysmgr.service.DataBackService;
import com.portal.sysmgr.service.ResourceService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/data"})
public class DataAct
{
  private static String BR = "\r\n";
  private static String backup_table;
  private static String percent;

  @Autowired
  private RealPathResolver realPathResolver;

  @Autowired
  private DataBackService dataBackService;

  @Autowired
  private ResourceService resourceService;

  @RequestMapping({"/v_revert.do"})
  public String listDataBases(ModelMap model, HttpServletRequest request, HttpServletResponse response)
  {
    try
    {
      String defaultCatalog = this.dataBackService.getDefaultCatalog();
      model.addAttribute("defaultCatalog", defaultCatalog);
    } catch (SQLException e) {
      System.out.println("");
    }
    model.addAttribute("dbfiles", 
      this.resourceService.listFile("backup", "", false));
    return "dataCenter/dbback/revert";
  }

  @RequestMapping({"/o_revert.do"})
  public void revert(String filename, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException, JSONException, SQLException
  {
    JSONObject json = new JSONObject();
    String backpath = this.realPathResolver.get("backup");
    String backFilePath = backpath + "/" + filename;
    String sql = readFile(backFilePath);
    this.dataBackService.executeSQL("use " + this.dataBackService.getDefaultCatalog() + 
      ";" + BR);
    this.dataBackService.executeSQL(sql);
    json.put("status", 1);
    ResponseUtils.renderJson(response, json.toString());
  }

  @RequestMapping({"/v_backup.do "})
  public String backup(String[] tableNames, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
    return "dataCenter/dbback/backup";
  }

  @RequestMapping({"/o_backup.do"})
  public void backupsubmit(ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException, InterruptedException, JSONException
  {
    JSONObject json = new JSONObject();
    String backpath = this.realPathResolver.get("backup");
    File backDirectory = new File(backpath);
    if (!backDirectory.exists())
      backDirectory.mkdir();
    try
    {
      List<String> tables = this.dataBackService.getTables();
      String backFilePath = backpath + "/" + 
        DateUtils.getNowToString() + ".sql";
      File file = new File(backFilePath);
      Thread thread = new DateBackupTableThread(file, tables);
      thread.start();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    json.put("status", 1);
    ResponseUtils.renderJson(response, json.toString());
  }

  @RequestMapping({"/o_backup_progress.do"})
  public void getBackupProgress(HttpServletRequest request, HttpServletResponse response) throws JSONException {
    JSONObject json = new JSONObject();
    json.put("tablename", backup_table);
    json.put("percent", percent);
    ResponseUtils.renderJson(response, json.toString());
  }

  private String readFile(String filename) throws IOException {
    if (StringUtils.isBlank(filename)) {
      throw new NullPointerException("文件名为空");
    }
    StringBuffer sb = new StringBuffer();
    FileInputStream file = new FileInputStream(filename);
    InputStreamReader isr = new InputStreamReader(file, "utf8");
    BufferedReader br = new BufferedReader(isr);
    String line = null;
    while ((line = br.readLine()) != null) {
      sb.append(line);
      sb.append(BR);
    }
    file.close();
    isr.close();
    br.close();
    return sb.toString();
  }
  private class DateBackupTableThread extends Thread {
    private File file;
    private List<String> tablenames;

    public DateBackupTableThread(File file,List<String> tablenames) {
      this.file = file;
      this.tablenames = tablenames;
    }

    public void run()
    {
      OutputStreamWriter writer = null;
      try {
        FileOutputStream out = new FileOutputStream(this.file);
        writer = new OutputStreamWriter(out, "utf8");
        writer.write("SET FOREIGN_KEY_CHECKS = 0");
        writer.write(";--end" + DataAct.BR);
        for (int i = 0; i < this.tablenames.size(); i++) {
          DataAct.backup_table = (String)this.tablenames.get(i);
          DataAct.percent = i * 100 / this.tablenames.size() + "%";
          backupTable(writer, (String)this.tablenames.get(i));
        }
        for (int i = 0; i < this.tablenames.size(); i++) {
          try {
            String sf = DataAct.this.dataBackService
              .getAllExportedKeys((String)this.tablenames.get(i));
            if (StringUtils.isNotBlank(sf))
              writer.write(sf);
          }
          catch (SQLException e) {
            e.printStackTrace();
          }
          writer.flush();
        }
        writer.write("SET FOREIGN_KEY_CHECKS = 1");
        writer.write(";--end" + DataAct.BR);
        DataAct.backup_table = "";
        writer.close();
        out.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    private String backupTable(OutputStreamWriter writer, String tablename) throws IOException
    {
      writer.write(DataAct.this.dataBackService.getWholeTableSql(tablename));
      writer.flush();
      return tablename;
    }
  }
}
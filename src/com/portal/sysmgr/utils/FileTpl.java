 package com.portal.sysmgr.utils;
 
 import java.io.File;
 import java.io.IOException;
 import java.sql.Timestamp;
 import java.util.Date;
 import org.apache.commons.io.FileUtils;
 
 public class FileTpl
   implements Tpl
 {
   private File file;
   private String root;
 
   public FileTpl(File file, String root)
   {
     this.file = file;
     this.root = root;
   }
 
   public String getName() {
     String ap = this.file.getAbsolutePath().substring(this.root.length());
     ap = ap.replace(File.separatorChar, '/');
 
     if (!ap.startsWith("/")) {
       ap = "/" + ap;
     }
     return ap;
   }
 
   public String getPath() {
     String name = getName();
     return name.substring(0, name.lastIndexOf('/'));
   }
 
   public String getFilename() {
     return this.file.getName();
   }
 
   public String getSource() {
     if (this.file.isDirectory())
       return null;
     try
     {
       return FileUtils.readFileToString(this.file, "UTF-8"); } catch (IOException e) {
	     throw new RuntimeException(e);
     }
   }
 
   public long getLastModified()
   {
     return this.file.lastModified();
   }
 
   public Date getLastModifiedDate() {
     return new Timestamp(getLastModified());
   }
 
   public long getLength() {
     return this.file.length();
   }
 
   public int getSize() {
     return (int)(getLength() / 1024L) + 1;
   }
 
   public boolean isDirectory() {
     return this.file.isDirectory();
   }
 
   public boolean isLeaf() {
     File[] child = this.file.listFiles();
     if (child != null) {
       for (File f : child) {
         if (f.isDirectory()) {
           return false;
         }
       }
     }
     return true;
   }
 }


 
 
 
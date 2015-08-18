 package com.portal.sysmgr.service.impl;
 
 import com.javassf.basic.file.FileWrap;
 import com.javassf.basic.file.FileWrap.FileComparator;
 import com.javassf.basic.plugin.springmvc.RealPathResolver;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.service.ResourceService;
 import java.io.File;
 import java.io.FileFilter;
 import java.io.FilenameFilter;
 import java.io.IOException;
 import java.util.ArrayList;
 import java.util.Arrays;
 import java.util.List;
 import org.apache.commons.io.FileUtils;
 import org.apache.commons.io.FilenameUtils;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;
 import org.springframework.web.multipart.MultipartFile;
 
 @Service
 public class ResourceServiceImpl
   implements ResourceService
 {
   private FileFilter filter = new FileFilter()
   {
     public boolean accept(File file) {
       return (file.isDirectory()) || 
         (FileWrap.editableExt(FilenameUtils.getExtension(file
         .getName())));
     }
   };
   private RealPathResolver realPathResolver;
 
   public List<FileWrap> listFile(String root, String path, boolean dirAndEditable)
   {
     File parent = new File(this.realPathResolver.get(root + path));
     if (parent.exists())
     {
       File[] files;
       //File[] files;
       if (dirAndEditable)
         files = parent.listFiles(this.filter);
       else {
         files = parent.listFiles();
       }
       Arrays.sort(files, new FileWrap.FileComparator());
       List list = new ArrayList(files.length);
       for (File f : files) {
         list.add(new FileWrap(f, this.realPathResolver.get("") + root));
       }
       return list;
     }
     return new ArrayList(0);
   }
 
   public boolean createDir(String path, String dirName)
   {
     File parent = new File(this.realPathResolver.get(path));
     parent.mkdirs();
     File dir = new File(parent, dirName);
     return dir.mkdir();
   }
 
   public void saveFile(String path, MultipartFile file) throws IllegalStateException, IOException
   {
     File dest = new File(this.realPathResolver.get(path), 
       file.getOriginalFilename());
     file.transferTo(dest);
   }
 
   public void createFile(String path, String filename, String data) throws IOException
   {
     File parent = new File(this.realPathResolver.get(path));
     parent.mkdirs();
     File file = new File(parent, filename);
     FileUtils.writeStringToFile(file, data, "UTF-8");
   }
 
   public String readFile(String name) throws IOException {
     File file = new File(this.realPathResolver.get(name));
     return FileUtils.readFileToString(file, "UTF-8");
   }
 
   public void updateFile(String name, String data) throws IOException {
     File file = new File(this.realPathResolver.get(name));
     FileUtils.writeStringToFile(file, data, "UTF-8");
   }
 
   public int delete(String root, String[] names) {
     int count = 0;
 
     for (String name : names) {
       File f = new File(this.realPathResolver.get(root + name));
       if (FileUtils.deleteQuietly(f)) {
         count++;
       }
     }
     return count;
   }
 
   public void rename(String origName, String destName) {
     File orig = new File(this.realPathResolver.get(origName));
     File dest = new File(this.realPathResolver.get(destName));
     orig.renameTo(dest);
   }
 
   public void copyTplAndRes(Site from, Site to) throws IOException {
     String fromSol = from.getTplStyle();
     String toSol = to.getTplStyle();
     File tplFrom = new File(this.realPathResolver.get(from.getTplPath()), 
       fromSol);
     File tplTo = new File(this.realPathResolver.get(to.getTplPath()), toSol);
     FileUtils.copyDirectory(tplFrom, tplTo);
     File resFrom = new File(this.realPathResolver.get(from.getResPath()), 
       fromSol);
     if (resFrom.exists()) {
       File resTo = new File(this.realPathResolver.get(to.getResPath()), toSol);
       FileUtils.copyDirectory(resFrom, resTo);
     }
   }
 
   public void delTplAndRes(Site site) throws IOException {
     File tpl = new File(this.realPathResolver.get(site.getTplPath()));
     File res = new File(this.realPathResolver.get(site.getResPath()));
     FileUtils.deleteDirectory(tpl);
     FileUtils.deleteDirectory(res);
   }
 
   public String[] getStyles(String path) {
     File tpl = new File(this.realPathResolver.get(path));
     return tpl.list(new FilenameFilter() {
       public boolean accept(File dir, String name) {
         return dir.isDirectory();
       }
     });
   }
 
   @Autowired
   public void setRealPathResolver(RealPathResolver realPathResolver)
   {
     this.realPathResolver = realPathResolver;
   }
 }


 
 
 
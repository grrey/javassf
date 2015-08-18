 package com.portal.sysmgr.service.impl;
 
 import com.javassf.basic.plugin.springmvc.RealPathResolver;
 import com.portal.sysmgr.service.TplService;
 import com.portal.sysmgr.utils.FileTpl;
 import com.portal.sysmgr.utils.Tpl;
 import java.io.File;
 import java.io.FileFilter;
 import java.io.IOException;
 import java.util.ArrayList;
 import java.util.List;
 import org.apache.commons.io.FileUtils;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;
 import org.springframework.web.multipart.MultipartFile;
 
 @Service
 public class TplServiceImpl
   implements TplService
 {
   private static Logger log = LoggerFactory.getLogger(TplServiceImpl.class);
   private String root;
   private RealPathResolver realPathResolver;
 
   public int delete(String mroot, String[] names)
   {
     int count = 0;
     for (String name : names) {
       File f = new File(this.realPathResolver.get(mroot + name));
       if (f.isDirectory()) {
         if (FileUtils.deleteQuietly(f)) {
           count++;
         }
       }
       else if (f.delete()) {
         count++;
       }
     }
 
     return count;
   }
 
   public Tpl get(String mroot, String name) {
     File f = new File(this.realPathResolver.get(mroot + name));
     if (f.exists()) {
       return new FileTpl(f, this.root + mroot);
     }
     return null;
   }
 
   public List<Tpl> getListByPrefix(String prefix)
   {
     File f = new File(this.realPathResolver.get(prefix));
     String name = prefix.substring(prefix.lastIndexOf("/") + 1);
     File parent;
     //File parent;
     if (prefix.endsWith("/")) {
       name = "";
       parent = f;
     } else {
       parent = f.getParentFile();
     }
     if (parent.exists()) {
       File[] files = parent.listFiles(new PrefixFilter(name));
       if (files != null) {
         List list = new ArrayList();
         for (File file : files) {
           list.add(new FileTpl(file, this.root));
         }
         return list;
       }
       return new ArrayList(0);
     }
 
     return new ArrayList(0);
   }
 
   public List<String> getNameListByPrefix(String prefix)
   {
     List<Tpl> list = getListByPrefix(prefix);
     List<String> result = new ArrayList<String>(list.size());
     for (Tpl tpl : list) {
       result.add(tpl.getName());
     }
     return result;
   }
 
   public List<Tpl> getChild(String mroot, String path) {
     File file = new File(this.realPathResolver.get(mroot + path));
     File[] child = file.listFiles();
     if (child != null) {
       List<Tpl> list = new ArrayList<Tpl>(child.length);
       for (File f : child) {
         list.add(new FileTpl(f, this.root + mroot));
       }
       return list;
     }
     return new ArrayList<Tpl>(0);
   }
 
   public List<Tpl> getDirChild(String mroot, String path)
   {
     File file = new File(this.realPathResolver.get(mroot + path));
     File[] child = file.listFiles();
     if (child != null) {
       List list = new ArrayList(child.length);
       for (File f : child) {
         if (f.isDirectory()) {
           list.add(new FileTpl(f, this.root + mroot));
         }
       }
       return list;
     }
     return new ArrayList(0);
   }
 
   public List<Tpl> getFileChild(String mroot, String path)
   {
     File file = new File(this.realPathResolver.get(mroot + path));
     File[] child = file.listFiles();
     if (child != null) {
       List list = new ArrayList(child.length);
       for (File f : child) {
         if (!f.isDirectory()) {
           list.add(new FileTpl(f, this.root + mroot));
         }
       }
       return list;
     }
     return new ArrayList(0);
   }
 
   public void rename(String orig, String dist)
   {
     String os = this.realPathResolver.get(orig);
     File of = new File(os);
     String ds = this.realPathResolver.get(dist);
     File df = new File(ds);
     try {
       if (of.isDirectory())
         FileUtils.moveDirectory(of, df);
       else
         FileUtils.moveFile(of, df);
     }
     catch (IOException e) {
       log.error("Move template error: " + orig + " to " + dist, e);
     }
   }
 
   public void save(String name, String source, boolean isDirectory) {
     String real = this.realPathResolver.get(name);
     File f = new File(real);
     if (isDirectory)
       f.mkdirs();
     else
       try {
         FileUtils.writeStringToFile(f, source, "UTF-8");
       } catch (IOException e) {
         log.error("Save template error: " + name, e);
         throw new RuntimeException(e);
       }
   }
 
   public void save(String path, MultipartFile file)
   {
     File f = new File(this.realPathResolver.get(path), 
       file.getOriginalFilename());
     try {
       file.transferTo(f);
     } catch (IllegalStateException e) {
       log.error("upload template error!", e);
     } catch (IOException e) {
       log.error("upload template error!", e);
     }
   }
 
   public void update(String name, String source) {
     String real = this.realPathResolver.get(name);
     File f = new File(real);
     try {
       FileUtils.writeStringToFile(f, source, "UTF-8");
     } catch (IOException e) {
       log.error("Save template error: " + name, e);
       throw new RuntimeException(e);
     }
   }
 
   @Autowired
   public void setRealPathResolver(RealPathResolver realPathResolver)
   {
     this.realPathResolver = realPathResolver;
     this.root = realPathResolver.get("");
   }
   private static class PrefixFilter implements FileFilter {
     private String prefix;
 
     public PrefixFilter(String prefix) {
       this.prefix = prefix;
     }
 
     public boolean accept(File file) {
       String name = file.getName();
       return (file.isFile()) && (name.startsWith(this.prefix));
     }
   }
 }


 
 
 
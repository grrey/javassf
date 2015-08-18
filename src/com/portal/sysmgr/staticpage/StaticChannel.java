 package com.portal.sysmgr.staticpage;
 
 import com.portal.doccenter.entity.Channel;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.utils.ContextTools;
 import com.portal.sysmgr.utils.ViewTools;
 import freemarker.template.Configuration;
 import freemarker.template.Template;
 import freemarker.template.TemplateException;
 import java.io.BufferedWriter;
 import java.io.File;
 import java.io.FileNotFoundException;
 import java.io.FileOutputStream;
 import java.io.IOException;
 import java.io.OutputStreamWriter;
 import java.io.PrintStream;
 import java.io.UnsupportedEncodingException;
 import java.io.Writer;
 import java.util.HashMap;
 import java.util.Map;
 import javax.servlet.ServletContext;
 import org.tuckey.web.filters.urlrewrite.utils.StringUtils;
 
 public class StaticChannel
 {
   public static Integer staticChannel(Channel channel, Configuration config, ServletContext ctx, Integer countpage)
   {
     if ((channel == null) || (!channel.getStaticChannel())) {
       return Integer.valueOf(0);
     }
     Template template;
     try
     {
       template = config.getTemplate(channel.getTplChannelOrDef());
     }
     catch (IOException e)
     {
       return Integer.valueOf(-1);
     }
     Map data = new HashMap();
     data.put("channel", channel);
     Integer total = Integer.valueOf(1);
     for (int page = 1; page <= total.intValue(); page++) {
       String filename = ctx.getRealPath(channel.getChannelRealPath(Integer.valueOf(page)));
       File file = new File(filename);
       file.getParentFile().mkdirs();
       String url = channel.getUrlStatic(Integer.valueOf(page));
       ViewTools.frontData(url, data, channel.getSite());
       ViewTools.frontPageData(url, data, Integer.valueOf(page));
       FileOutputStream fos = null;
       Writer out = null;
				Integer localInteger1;
       try {
         fos = new FileOutputStream(file);
         out = new OutputStreamWriter(fos, "UTF-8");
         ContextTools.resetTotalPages();
         template.process(data, new BufferedWriter(out));
         Integer cou = ContextTools.getTotalPages();
         if ((countpage != null) && (cou != null) && (countpage.intValue() <= cou.intValue())) {
           total = countpage;
         } else {
           total = cou;
           if ((total == null) || (total.intValue() < 1))
             total = Integer.valueOf(1);
         }
       }
       catch (FileNotFoundException e) {
         localInteger1 = Integer.valueOf(-3);
         return localInteger1;
       } catch (UnsupportedEncodingException e) {
         localInteger1 = Integer.valueOf(-4);
         return localInteger1;
       } catch (TemplateException e) {
         localInteger1 = Integer.valueOf(-1);
         return localInteger1;
       } catch (IOException e) {
         localInteger1 = Integer.valueOf(-2);
         return localInteger1;
       } finally {
         try {
           if (out != null) {
             out.flush();
             out.close();
           }
           if (fos != null)
             fos.close();
         }
         catch (IOException localIOException5)
         {
         }
       }
       try
       {
         if (out != null) {
           out.flush();
           out.close();
         }
         if (fos != null)
           fos.close();
       }
       catch (IOException localIOException6)
       {
       }
     }
     return Integer.valueOf(1);
   }
 
   public static void staticChannelpage(Channel channel, Configuration config, ServletContext ctx, Integer page)
   {
     if ((channel == null) || (!channel.getStaticChannel())) {
       return;
     }
     Template template;
     try
     {
       template = config.getTemplate(channel.getTplChannelOrDef());
     }
     catch (IOException e)
     {
       System.out.println(channel.getName() + "的模板不存在，请检查!");
       return;
     }
     Map data = new HashMap();
     data.put("channel", channel);
     String filename = ctx.getRealPath(channel.getChannelRealPath(page));
     File file = new File(filename);
     file.getParentFile().mkdirs();
     String url = channel.getUrlStatic(page);
     ViewTools.frontData(url, data, channel.getSite());
     ViewTools.frontPageData(url, data, page);
     FileOutputStream fos = null;
     Writer out = null;
     try {
       fos = new FileOutputStream(file);
       out = new OutputStreamWriter(fos, "UTF-8");
       template.process(data, new BufferedWriter(out));
     }
     catch (FileNotFoundException localFileNotFoundException)
     {
       try
       {
         if (out != null) {
           out.flush();
           out.close();
         }
         if (fos != null)
           fos.close();
       }
       catch (IOException localIOException1)
       {
       }
     }
     catch (UnsupportedEncodingException localUnsupportedEncodingException)
     {
       try
       {
         if (out != null) {
           out.flush();
           out.close();
         }
         if (fos != null)
           fos.close();
       }
       catch (IOException localIOException2)
       {
       }
     }
     catch (TemplateException e)
     {
       System.out.println("模板中有错误，静态页生成失败!");
       try
       {
         if (out != null) {
           out.flush();
           out.close();
         }
         if (fos != null)
           fos.close();
       }
       catch (IOException localIOException3)
       {
       }
     }
     catch (IOException e)
     {
       System.out.println("生成静态页异常!");
       try
       {
         if (out != null) {
           out.flush();
           out.close();
         }
         if (fos != null)
           fos.close();
       }
       catch (IOException localIOException4)
       {
       }
     }
     finally
     {
       try
       {
         if (out != null) {
           out.flush();
           out.close();
         }
         if (fos != null)
           fos.close();
       }
       catch (IOException localIOException5)
       {
       }
     }
   }
 
   public static int staticIndex(Site site, Configuration config, ServletContext ctx) {
     if ((site == null) || (StringUtils.isBlank(site.getTplIndexOrDef()))) {
       System.out.println("---------111");
       return 0;
     }
     Template template;
     try
     {
       template = config.getTemplate(site.getTplIndexOrDef());
     }
     catch (IOException e)
     {
       System.out.println("---------222");
       return -1;
     }
     Map data = new HashMap();
     String filename = ctx.getRealPath(site.getStaticRealPath());
     File file = new File(filename);
     file.getParentFile().mkdirs();
     ViewTools.frontData(site.getUrl(), data, site);
     FileOutputStream fos = null;
     Writer out = null;
     try {
       fos = new FileOutputStream(file);
       out = new OutputStreamWriter(fos, "UTF-8");
       template.process(data, new BufferedWriter(out));
     }
     catch (FileNotFoundException e)
     {
       return -3;
     }
     catch (UnsupportedEncodingException e)
     {
       return -4;
     }
     catch (TemplateException e)
     {
       return -1;
     }
     catch (IOException e)
     {
       return -2;
     } finally {
       try {
         if (out != null) {
           out.flush();
           out.close();
         }
         if (fos != null)
           fos.close();
       }
       catch (IOException localIOException5)
       {
       }
     }
     try
     {
       if (out != null) {
         out.flush();
         out.close();
       }
       if (fos != null)
         fos.close();
     }
     catch (IOException localIOException6)
     {
     }
     return 1;
   }
 }


 
 
 
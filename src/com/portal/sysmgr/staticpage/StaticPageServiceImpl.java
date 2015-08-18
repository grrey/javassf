 package com.portal.sysmgr.staticpage;
 
 import com.portal.datacenter.docdata.service.KeywordService;
 import com.portal.doccenter.entity.Article;
 import com.portal.doccenter.entity.Channel;
 import com.portal.sysmgr.entity.Site;
 import freemarker.template.Configuration;
 import freemarker.template.TemplateException;
 import java.io.File;
 import java.io.IOException;
 import java.util.List;
 import javax.servlet.ServletContext;
 import org.apache.commons.io.FileUtils;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
 
 @Service
 @Transactional
 public class StaticPageServiceImpl
   implements StaticPageService
 {
   private FreeMarkerConfigurer freeMarkerConfigurer;
   private ServletContext context;
   private StaticPageChannelDao staticPageChannelDao;
   private StaticPageArticleDao staticPageArticleDao;
   private KeywordService keywordService;
 
   public int staticIndex(Site site)
   {
     return StaticChannel.staticIndex(site, getConfig(), this.context);
   }
 
   public int staticIndexCheck(Site site) {
     String filename = this.context.getRealPath(site.getStaticRealPath());
     File file = new File(filename);
     if (!file.exists()) {
       return StaticChannel.staticIndex(site, getConfig(), this.context);
     }
     return 2;
   }
   @Transactional(readOnly=true)
   public List<String> staticChannelPage(Channel c) {
     String treeNumber = "";
     if (c != null) {
       treeNumber = c.getNumber();
     }
     return this.staticPageChannelDao.staticChannelPage(treeNumber, getConfig(), 
       this.context);
   }
   @Transactional(readOnly=true)
   public void staticChannel(Channel c) {
     while (c != null) {
       staticChannelCheck(c, Integer.valueOf(1));
       c = c.getParent();
     }
   }
 
   @Transactional(readOnly=true)
   public void staticChannelAlone(Channel c) {
     StaticChannel.staticChannel(c, getConfig(), this.context, Integer.valueOf(3));
   }
   @Transactional(readOnly=true)
   public void staticChannelCheck(Channel c, Integer page) {
     String filename = this.context.getRealPath(c.getChannelRealPath(page));
     File file = new File(filename);
     if (file.exists()) {
       if (c.isChanged(file.lastModified()))
         StaticChannel.staticChannelpage(c, getConfig(), this.context, page);
     }
     else
       StaticChannel.staticChannelpage(c, getConfig(), this.context, page);
   }
 
   public void deleteStaticChannel(Channel c)
   {
     if (c == null) {
       return;
     }
     String filename = this.context.getRealPath(c.getChannelRealPath(Integer.valueOf(1)));
     File file = new File(filename);
     if (file.exists())
       FileUtils.deleteQuietly(file);
   }
 
   public void deleteAllStaticArticle(Channel c)
   {
     if (c == null) {
       return;
     }
     String filename = this.context.getRealPath(c.getStaticDocPath());
     File file = new File(filename);
     if (file.exists())
       FileUtils.deleteQuietly(file);
   }
 
   @Transactional(readOnly=true)
   public String staticArticlePage(Channel c)
     throws IOException, TemplateException
   {
     String treeNumber = "";
     if (c != null) {
       treeNumber = c.getNumber();
     }
     return this.staticPageArticleDao.staticArticlePage(treeNumber, getConfig(), 
       this.context, this.keywordService);
   }
   @Transactional(readOnly=true)
   public void staticArticle(Article a) {
     StaticArticle.staticArticle(a, getConfig(), this.context, this.keywordService);
     staticChannel(a.getChannel());
   }
   @Transactional(readOnly=true)
   public void staticArticleCheck(Article a, Integer page) {
     String filename = this.context.getRealPath(a.getStaticRealPath(page));
     File file = new File(filename);
     if (!file.exists())
     {
       StaticArticle.staticArticle(a, getConfig(), this.context, this.keywordService);
     }
     else if (a.isChanged(file.lastModified()))
       StaticArticle.staticArticle(a, getConfig(), this.context, 
         this.keywordService);
   }
 
   public void deleteStaticArticle(Article a)
   {
     if (a == null) {
       return;
     }
     String filename = this.context.getRealPath(a.getStaticRealPath(Integer.valueOf(1)));
     File file = new File(filename);
     if (file.exists())
       FileUtils.deleteQuietly(file);
   }
 
   private Configuration getConfig()
   {
     return this.freeMarkerConfigurer.getConfiguration();
   }
 
   @Autowired
   public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
     this.freeMarkerConfigurer = freeMarkerConfigurer;
   }
   @Autowired
   public void setContext(ServletContext context) {
     this.context = context;
   }
 
   @Autowired
   public void setStaticPageChannelDao(StaticPageChannelDao staticPageChannelDao) {
     this.staticPageChannelDao = staticPageChannelDao;
   }
 
   @Autowired
   public void setStaticPageArticleDao(StaticPageArticleDao staticPageArticleDao) {
     this.staticPageArticleDao = staticPageArticleDao;
   }
   @Autowired
   public void setKeywordService(KeywordService keywordService) {
     this.keywordService = keywordService;
   }
 }


 
 
 
 package com.portal.sysmgr.staticpage;
 
 import com.portal.datacenter.docdata.service.KeywordService;
 import com.portal.doccenter.entity.Article;
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
 import java.io.UnsupportedEncodingException;
 import java.io.Writer;
 import java.util.Collections;
 import java.util.HashMap;
 import java.util.Map;
 import javax.servlet.ServletContext;
 import org.apache.commons.lang.StringUtils;
 import org.springframework.data.domain.Page;
 import org.springframework.data.domain.PageImpl;
 import org.springframework.data.domain.PageRequest;
 import org.springframework.data.domain.Pageable;
 
 public class StaticArticle
 {
   public static Integer staticArticle(Article article, Configuration config, ServletContext ctx, KeywordService keywordService)
   {
     if ((article == null) || (!article.getStaticDoc())) {
       return Integer.valueOf(0);
     }
			  Template template;
     try
     {
       template = config.getTemplate(article.getTplContentOrDef());
     }
     catch (IOException e)
     {
       return Integer.valueOf(-1);
     }
     
     Map data = new HashMap();
     data.put("doc", article);
     data.put("channel", article.getChannel());
     data.put("title", article.getTitle());
     Integer total = Integer.valueOf(article.getPageCount());
     for (int page = 1; page <= total.intValue(); page++) {
       String filename = ctx.getRealPath(article.getStaticRealPath(Integer.valueOf(page)));
       File file = new File(filename);
       file.getParentFile().mkdirs();
       String url = article.getUrlStatic(Integer.valueOf(page));
       Pageable pa = new PageRequest(page - 1, 1);
       Page p = new PageImpl(Collections.emptyList(), pa, 
         total.intValue());
       String ctxs = article.getSite().getContextPath();
       ctxs = ctxs == null ? "" : ctxs;
       String txt = article.getTxtByNo(page);
       txt = StringUtils.replace(txt, "../..", ctxs);
       txt = keywordService.attachKeyword(article.getSite().getId(), txt);
       data.put("page", p);
       data.put("txt", txt);
       ViewTools.frontData(url, data, article.getSite());
       ViewTools.frontPageData(url, data, Integer.valueOf(page));
       FileOutputStream fos = null;
       Writer out = null;
				Integer localInteger1;
       try {
         fos = new FileOutputStream(file);
         out = new OutputStreamWriter(fos, "UTF-8");
         ContextTools.resetTotalPages();
         template.process(data, new BufferedWriter(out));
       } catch (FileNotFoundException e) {
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
     return null;
   }
 }


 
 
 
 package com.portal.doccenter.action.tag;
 
 import com.portal.doccenter.entity.Article;
 import com.portal.doccenter.service.ArticleService;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.utils.TagModelTools;
 import com.portal.sysmgr.utils.ViewTools;
 import freemarker.core.Environment;
 import freemarker.template.ObjectWrapper;
 import freemarker.template.TemplateDirectiveBody;
 import freemarker.template.TemplateDirectiveModel;
 import freemarker.template.TemplateException;
 import freemarker.template.TemplateModel;
 import java.io.IOException;
 import java.io.PrintStream;
 import java.util.Map;
 import org.springframework.beans.factory.annotation.Autowired;
 
 public class DocTagModel
   implements TemplateDirectiveModel
 {
   public static final String PARAM_ID = "id";
   public static final String PRAMA_NEXT = "next";
   public static final String PARAM_CHANNEL_ID = "cId";
 
   @Autowired
   private ArticleService articleService;
 
   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
     throws TemplateException, IOException
   {
     Integer id = getId(params);
     Boolean next = TagModelTools.getBool("next", params);
     Article article;
     //Article article;
     if (next == null) {
       article = this.articleService.findById(id);
     } else {
       Site site = ViewTools.getSite(env);
       Integer channelId = TagModelTools.getInt("cId", params);
       article = this.articleService.getSide(id, site.getId(), channelId, next.booleanValue());
     }
     env.setVariable("bean", ObjectWrapper.DEFAULT_WRAPPER.wrap(article));
     if (body != null)
       body.render(env.getOut());
   }
 
   private Integer getId(Map<String, TemplateModel> params)
     throws TemplateException
   {
     Integer id = TagModelTools.getInt("id", params);
     if (id != null) {
       return id;
     }
     System.out.println("缺少必要参数!");
     return Integer.valueOf(0);
   }
 }


 
 
 
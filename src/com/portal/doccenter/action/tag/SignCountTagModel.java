 package com.portal.doccenter.action.tag;
 
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
 import java.util.Map;
 import org.springframework.beans.factory.annotation.Autowired;
 
 public class SignCountTagModel
   implements TemplateDirectiveModel
 {
   public static final String PARAM_DEPART_ID = "departId";
 
   @Autowired
   private ArticleService articleService;
 
   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
     throws TemplateException, IOException
   {
     Site site = ViewTools.getSite(env);
     Integer departId = TagModelTools.getInt("departId", params);
     Long count = 
       Long.valueOf(this.articleService
       .getCountByDepartSign(site.getId(), departId));
     env.setVariable("count", ObjectWrapper.DEFAULT_WRAPPER.wrap(count));
     if (body != null)
       body.render(env.getOut());
   }
 }


 
 
 
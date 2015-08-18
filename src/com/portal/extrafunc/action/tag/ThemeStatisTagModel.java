 package com.portal.extrafunc.action.tag;
 
 import com.portal.extrafunc.action.cache.ThemeStatisCache;
 import com.portal.sysmgr.utils.TagModelTools;
 import freemarker.core.Environment;
 import freemarker.template.ObjectWrapper;
 import freemarker.template.TemplateDirectiveBody;
 import freemarker.template.TemplateDirectiveModel;
 import freemarker.template.TemplateException;
 import freemarker.template.TemplateModel;
 import java.io.IOException;
 import java.util.Map;
 import org.springframework.beans.factory.annotation.Autowired;
 
 public class ThemeStatisTagModel
   implements TemplateDirectiveModel
 {
   public static final String PARAM_THEME_ID = "id";
 
   @Autowired
   private ThemeStatisCache statisCache;
 
   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
     throws TemplateException, IOException
   {
     Integer id = TagModelTools.getInt("id", params);
     Integer viewsCount = this.statisCache.getStatis(id);
     env.setVariable("viewsCount", ObjectWrapper.DEFAULT_WRAPPER.wrap(viewsCount));
     body.render(env.getOut());
   }
 }


 
 
 
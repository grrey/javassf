 package com.portal.extrafunc.action.tag;
 
 import com.portal.extrafunc.action.cache.ForumStatisCache;
 import com.portal.extrafunc.entity.ForumStatis;
 import com.portal.sysmgr.entity.Site;
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
 
 public class ForumStatisTagModel
   implements TemplateDirectiveModel
 {
 
   @Autowired
   private ForumStatisCache statisCache;
 
   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
     throws TemplateException, IOException
   {
     Site site = ViewTools.getSite(env);
     ForumStatis statis = this.statisCache.getStatis(site);
     env.setVariable("bean", ObjectWrapper.DEFAULT_WRAPPER.wrap(statis));
     body.render(env.getOut());
   }
 }


 
 
 
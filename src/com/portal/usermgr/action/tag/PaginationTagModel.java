 package com.portal.usermgr.action.tag;
 
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.utils.ViewTools;
 import freemarker.core.Environment;
 import freemarker.template.TemplateDirectiveBody;
 import freemarker.template.TemplateDirectiveModel;
 import freemarker.template.TemplateException;
 import freemarker.template.TemplateModel;
 import java.io.IOException;
 import java.util.Map;
 
 public class PaginationTagModel
   implements TemplateDirectiveModel
 {
   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
     throws TemplateException, IOException
   {
     Site site = ViewTools.getSite(env);
     ViewTools.includePagination(site, params, env);
   }
 }


 
 
 
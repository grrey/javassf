 package com.portal.extrafunc.action.tag;
 
 import com.portal.extrafunc.service.CategoryService;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.utils.ViewTools;
 import freemarker.core.Environment;
 import freemarker.template.ObjectWrapper;
 import freemarker.template.TemplateDirectiveBody;
 import freemarker.template.TemplateDirectiveModel;
 import freemarker.template.TemplateException;
 import freemarker.template.TemplateModel;
 import java.io.IOException;
 import java.util.List;
 import java.util.Map;
 import org.springframework.beans.factory.annotation.Autowired;
 
 public class CategoryTagModel
   implements TemplateDirectiveModel
 {
 
   @Autowired
   private CategoryService service;
 
   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
     throws TemplateException, IOException
   {
     Site site = ViewTools.getSite(env);
     List list = this.service.getList(site.getId(), null, null);
     env.setVariable("list", ObjectWrapper.DEFAULT_WRAPPER.wrap(list));
     body.render(env.getOut());
   }
 }


 
 
 
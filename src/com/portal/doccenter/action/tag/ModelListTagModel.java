 package com.portal.doccenter.action.tag;
 
 import com.portal.doccenter.service.ModelService;
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
 import java.util.List;
 import java.util.Map;
 import org.apache.commons.lang.StringUtils;
 import org.springframework.beans.factory.annotation.Autowired;
 
 public class ModelListTagModel
   implements TemplateDirectiveModel
 {
   public static final String TPL_NAME = "tplName";
 
   @Autowired
   private ModelService modelService;
 
   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
     throws TemplateException, IOException
   {
     Site site = ViewTools.getSite(env);
     String tplName = TagModelTools.getString("tplName", params);
     List list = this.modelService.getList(false, null, null);
     env.setVariable("list", ObjectWrapper.DEFAULT_WRAPPER.wrap(list));
     if (!StringUtils.isBlank(tplName)) {
       ViewTools.includeTpl(tplName, site, env);
     }
     else if (body != null)
       body.render(env.getOut());
   }
 }


 
 
 
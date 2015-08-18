 package com.portal.extrafunc.action.tag;
 
 import com.portal.extrafunc.service.ForumService;
 import com.portal.sysmgr.utils.TagModelTools;
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
 
 public class ForumTagModel
   implements TemplateDirectiveModel
 {
   public static final String PARAM_CATEGORY_ID = "cId";
 
   @Autowired
   private ForumService service;
 
   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
     throws TemplateException, IOException
   {
     Integer cId = TagModelTools.getInt("cId", params);
     List list = this.service.getList(cId);
     env.setVariable("list", ObjectWrapper.DEFAULT_WRAPPER.wrap(list));
     body.render(env.getOut());
   }
 }


 
 
 
 package com.portal.extrafunc.action.tag;
 
 import com.portal.extrafunc.service.QuestionnaireService;
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
 import org.springframework.data.domain.Page;
 
 public class QuestionnaireTagModel
   implements TemplateDirectiveModel
 {
   public static final String PARAM_QUESTION_ID = "qId";
   public static final String PARAM_CUSTOM = "custom";
 
   @Autowired
   private QuestionnaireService service;
 
   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
     throws TemplateException, IOException
   {
     Site site = ViewTools.getSite(env);
     Boolean custom = TagModelTools.getBool("custom", params);
     Page page = this.service.getPage(site.getId(), true, 
       ViewTools.getPageNo(env), ViewTools.getCount(params));
     env.setVariable("page", ObjectWrapper.DEFAULT_WRAPPER.wrap(page));
     if ((custom != null) && (custom.booleanValue()))
       body.render(env.getOut());
     else
       ViewTools.includeTpl("question", site, env);
   }
 }


 
 
 
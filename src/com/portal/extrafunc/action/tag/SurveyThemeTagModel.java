 package com.portal.extrafunc.action.tag;
 
 import com.portal.extrafunc.entity.SurveyTheme;
 import com.portal.extrafunc.service.SurveyThemeService;
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
 
 public class SurveyThemeTagModel
   implements TemplateDirectiveModel
 {
   public static final String PARAM_QUESTION_ID = "qId";
   public static final String PARAM_CUSTOM = "custom";
 
   @Autowired
   private SurveyThemeService service;
 
   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
     throws TemplateException, IOException
   {
     Site site = ViewTools.getSite(env);
     Integer qId = TagModelTools.getInt("qId", params);
     Boolean custom = TagModelTools.getBool("custom", params);
     Page page = this.service.getPage(qId, ViewTools.getPageNo(env), 
       ViewTools.getCount(params));
     env.setVariable("NORMAL", ObjectWrapper.DEFAULT_WRAPPER.wrap(SurveyTheme.NORMAL));
     env.setVariable("WRITED", ObjectWrapper.DEFAULT_WRAPPER.wrap(SurveyTheme.WRITED));
     env.setVariable("CHECKED", ObjectWrapper.DEFAULT_WRAPPER.wrap(SurveyTheme.CHECKED));
     env.setVariable("SELECTED", ObjectWrapper.DEFAULT_WRAPPER.wrap(SurveyTheme.SELECTED));
     env.setVariable("TEXT", ObjectWrapper.DEFAULT_WRAPPER.wrap(SurveyTheme.TEXT));
     env.setVariable("ONLINE", ObjectWrapper.DEFAULT_WRAPPER.wrap(SurveyTheme.ONLINE));
     env.setVariable("AREA", ObjectWrapper.DEFAULT_WRAPPER.wrap(SurveyTheme.AREA));
     env.setVariable("page", ObjectWrapper.DEFAULT_WRAPPER.wrap(page));
     if ((custom != null) && (custom.booleanValue()))
       body.render(env.getOut());
     else
       ViewTools.includeTpl("surveyTheme", site, env);
   }
 }


 
 
 
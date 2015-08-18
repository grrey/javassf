 package com.portal.extrafunc.action.tag;
 
 import com.portal.extrafunc.service.QuestionDetailService;
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
 
 public class QuestionCountTagModel
   implements TemplateDirectiveModel
 {
   public static final String PARAM_QUESTION_ID = "qId";
 
   @Autowired
   private QuestionDetailService service;
 
   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
     throws TemplateException, IOException
   {
     Integer qId = TagModelTools.getInt("qId", params);
     long count = this.service.getCountDetail(qId);
     env.setVariable("count", ObjectWrapper.DEFAULT_WRAPPER.wrap(Long.valueOf(count)));
     body.render(env.getOut());
   }
 }


 
 
 
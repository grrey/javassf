 package com.portal.extrafunc.action.tag;
 
 import com.portal.extrafunc.service.PostsService;
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
 
 public class PostsTagModel
   implements TemplateDirectiveModel
 {
   public static final String PARAM_THEME_ID = "tId";
 
   @Autowired
   private PostsService service;
 
   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
     throws TemplateException, IOException
   {
     Integer tId = TagModelTools.getInt("tId", params);
     Page page = this.service.getPostsPageForTag(tId, 
       ViewTools.getPageNo(env), ViewTools.getCount(params));
     env.setVariable("page", ObjectWrapper.DEFAULT_WRAPPER.wrap(page));
     body.render(env.getOut());
   }
 }


 
 
 
 package com.portal.extrafunc.action.tag;
 
 import com.portal.extrafunc.action.cache.ForumCache;
 import com.portal.extrafunc.entity.Forum;
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
 
 public class ForumCountTagModel
   implements TemplateDirectiveModel
 {
   public static final String PARAM_FORUM_ID = "id";
 
   @Autowired
   private ForumCache forumCache;
 
   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
     throws TemplateException, IOException
   {
     Integer id = TagModelTools.getInt("id", params);
     Forum forum = this.forumCache.getForum(id);
     env.setVariable("bean", ObjectWrapper.DEFAULT_WRAPPER.wrap(forum));
     body.render(env.getOut());
   }
 }


 
 
 
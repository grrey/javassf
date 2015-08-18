 package com.portal.govcenter.action.tag;
 
 import com.portal.govcenter.service.MailboxService;
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
 
 public class MailboxTagModel
   implements TemplateDirectiveModel
 {
   public static final String PARAM_TITLE = "title";
   public static final String PARAM_TYPE_ID = "tId";
 
   @Autowired
   private MailboxService mailboxService;
 
   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
     throws TemplateException, IOException
   {
     Site site = ViewTools.getSite(env);
     String title = TagModelTools.getString("title", params);
     Integer tId = TagModelTools.getInt("tId", params);
     Page p = this.mailboxService
       .getPageByTag(title, site.getId(), null, tId, 
       ViewTools.getPageNo(env), ViewTools.getCount(params));
     env.setVariable("page", ObjectWrapper.DEFAULT_WRAPPER.wrap(p));
     if (body != null)
       body.render(env.getOut());
   }
 }


 
 
 
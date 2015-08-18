 package com.portal.usermgr.action.tag;
 
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.utils.TagModelTools;
 import com.portal.sysmgr.utils.ViewTools;
 import com.portal.usermgr.service.SiteMessageService;
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
 
 public class SiteMessageTagModel
   implements TemplateDirectiveModel
 {
   public static final String PARAM_SEND_ID = "sendId";
   public static final String PARAM_STATUS = "status";
 
   @Autowired
   private SiteMessageService messageService;
 
   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
     throws TemplateException, IOException
   {
     Site site = ViewTools.getSite(env);
     Integer sendId = TagModelTools.getInt("sendId", params);
     Integer status = TagModelTools.getInt("status", params);
     Page page = this.messageService.getPageByTag(sendId, status, 
       ViewTools.getPageNo(env), ViewTools.getCount(params));
     env.setVariable("page", ObjectWrapper.DEFAULT_WRAPPER.wrap(page));
     body.render(env.getOut());
     ViewTools.includePagination(site, params, env);
   }
 }


 
 
 
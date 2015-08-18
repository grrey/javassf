 package com.portal.extrafunc.action.tag;
 
 import com.portal.extrafunc.service.LinksService;
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
 import org.springframework.beans.factory.annotation.Autowired;
 
 public class LinksTagModel
   implements TemplateDirectiveModel
 {
   public static final String PARAM_TYPE_ID = "tId";
   public static final String PARAM_COUNT = "count";
 
   @Autowired
   private LinksService service;
 
   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
     throws TemplateException, IOException
   {
     Site site = ViewTools.getSite(env);
     Integer tId = TagModelTools.getInt("tId", params);
     Integer count = TagModelTools.getInt("count", params);
     List list = this.service.getListByTag(site.getId(), tId, count);
     env.setVariable("list", ObjectWrapper.DEFAULT_WRAPPER.wrap(list));
     body.render(env.getOut());
   }
 }


 
 
 
 package com.portal.usermgr.action.tag;
 
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.utils.TagModelTools;
 import com.portal.sysmgr.utils.ViewTools;
 import com.portal.usermgr.service.DepartService;
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
 
 public class DepartTagModel
   implements TemplateDirectiveModel
 {
   public static final String PARAM_PARENT_ID = "parentId";
   public static final String PARAM_ORDER_BY = "orderBy";
 
   @Autowired
   private DepartService departService;
 
   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
     throws TemplateException, IOException
   {
     Site site = ViewTools.getSite(env);
     Integer parentId = TagModelTools.getInt("parentId", params);
     Integer orderBy = TagModelTools.getInt("orderBy", params);
     List list = this.departService.getListByTag(site.getId(), parentId, 
       orderBy);
     env.setVariable("list", ObjectWrapper.DEFAULT_WRAPPER.wrap(list));
     body.render(env.getOut());
   }
 }


 
 
 
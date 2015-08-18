 package com.portal.doccenter.action.tag;
 
 import com.portal.doccenter.action.tag.base.BaseDocTagModel;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.utils.TagModelTools;
 import com.portal.sysmgr.utils.ViewTools;
 import freemarker.core.Environment;
 import freemarker.template.ObjectWrapper;
 import freemarker.template.TemplateDirectiveBody;
 import freemarker.template.TemplateException;
 import freemarker.template.TemplateModel;
 import java.io.IOException;
 import java.util.Map;
 import org.apache.commons.lang.StringUtils;
 import org.springframework.data.domain.Page;
 
 public class DocPageTagModel extends BaseDocTagModel
 {
   public static final String TPL_NAME = "tplName";
 
   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
     throws TemplateException, IOException
   {
     Site site = ViewTools.getSite(env);
     String tplName = TagModelTools.getString("tplName", params);
     Page page = (Page)getData(params, env);
     env.setVariable("page", ObjectWrapper.DEFAULT_WRAPPER.wrap(page));
     env.setVariable("list", 
       ObjectWrapper.DEFAULT_WRAPPER.wrap(page.getContent()));
     env.setVariable("isPage", ObjectWrapper.DEFAULT_WRAPPER.wrap(Boolean.valueOf(true)));
     if (!StringUtils.isBlank(tplName)) {
       ViewTools.includeTpl(tplName, site, env);
     }
     else if (body != null)
       body.render(env.getOut());
   }
 
   protected boolean isPage()
   {
     return true;
   }
 }


 
 
 
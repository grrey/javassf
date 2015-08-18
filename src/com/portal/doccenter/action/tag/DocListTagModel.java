 package com.portal.doccenter.action.tag;
 
 import com.portal.doccenter.action.tag.base.BaseDocTagModel;
 import com.portal.doccenter.entity.Article;
 import com.portal.doccenter.service.ArticleService;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.utils.TagModelTools;
 import com.portal.sysmgr.utils.ViewTools;
 import freemarker.core.Environment;
 import freemarker.template.ObjectWrapper;
 import freemarker.template.TemplateDirectiveBody;
 import freemarker.template.TemplateException;
 import freemarker.template.TemplateModel;
 import java.io.IOException;
 import java.util.List;
 import java.util.Map;
 import org.apache.commons.lang.StringUtils;
 
 public class DocListTagModel extends BaseDocTagModel
 {
   public static final String TPL_NAME = "tplName";
   public static final String PARAM_IDS = "ids";
 
   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
     throws TemplateException, IOException
   {
     Site site = ViewTools.getSite(env);
     String tplName = TagModelTools.getString("tplName", params);
     List list = getList(params, env);
     env.setVariable("list", ObjectWrapper.DEFAULT_WRAPPER.wrap(list));
     env.setVariable("isPage", ObjectWrapper.DEFAULT_WRAPPER.wrap(Boolean.valueOf(false)));
     if (!StringUtils.isBlank(tplName)) {
       ViewTools.includeTpl(tplName, site, env);
     }
     else if (body != null)
       body.render(env.getOut());
   }
 
   protected List<Article> getList(Map<String, TemplateModel> params, Environment env)
     throws TemplateException
   {
     Integer[] ids = TagModelTools.getIntArray("ids", params);
     if (ids != null) {
       return this.articleService.getListTagByIds(ids, getOrderBy(params));
     }
     return (List)super.getData(params, env);
   }
 
   protected boolean isPage()
   {
     return false;
   }
 }


 
 
 
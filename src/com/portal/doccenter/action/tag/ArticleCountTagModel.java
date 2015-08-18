 package com.portal.doccenter.action.tag;
 
 import com.portal.doccenter.service.ArticleService;
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
 import java.util.Date;
 import java.util.List;
 import java.util.Map;
 import org.springframework.beans.factory.annotation.Autowired;
 
 public class ArticleCountTagModel
   implements TemplateDirectiveModel
 {
   public static final String PARAM_CID = "cid";
   public static final String PARAM_DID = "did";
   public static final String PARAM_START = "start";
   public static final String PARAM_END = "end";
 
   @Autowired
   private ArticleService service;
 
   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
     throws TemplateException, IOException
   {
     Site site = ViewTools.getSite(env);
     Integer cid = TagModelTools.getInt("cid", params);
     Integer did = TagModelTools.getInt("did", params);
     Date start = TagModelTools.getDate("start", params);
     Date end = TagModelTools.getDate("end", params);
     List list = this.service.getCountByDepart(site.getId(), cid, did, 
       start, end);
     env.setVariable("list", ObjectWrapper.DEFAULT_WRAPPER.wrap(list));
     if (body != null)
       body.render(env.getOut());
   }
 }


 
 
 
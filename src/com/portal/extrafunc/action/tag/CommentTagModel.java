 package com.portal.extrafunc.action.tag;
 
 import com.portal.extrafunc.action.tag.base.BaseCommentTagModel;
 import com.portal.extrafunc.service.CommentService;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.utils.ViewTools;
 import freemarker.core.Environment;
 import freemarker.template.ObjectWrapper;
 import freemarker.template.TemplateDirectiveBody;
 import freemarker.template.TemplateException;
 import freemarker.template.TemplateModel;
 import java.io.IOException;
 import java.util.Map;
 import org.springframework.data.domain.Page;
 
 public class CommentTagModel extends BaseCommentTagModel
 {
   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
     throws TemplateException, IOException
   {
     Site site = ViewTools.getSite(env);
     boolean isPage = getIsPage(params);
     Page page = null;
     if (isPage)
       page = this.commentService.getPageForTag(getSiteId(params), 
         getDocId(params), getParentId(params), Boolean.valueOf(true), null, 
         getOrderBy(params), ViewTools.getPageNo(env), 
         ViewTools.getCount(params));
     else {
       page = this.commentService.getPageForTag(getSiteId(params), 
         getDocId(params), getParentId(params), Boolean.valueOf(true), null, 
         getOrderBy(params), 1, ViewTools.getCount(params));
     }
     env.setVariable("page", ObjectWrapper.DEFAULT_WRAPPER.wrap(page));
     body.render(env.getOut());
     if (isPage)
       ViewTools.includePagination(site, params, env);
   }
 }


 
 
 
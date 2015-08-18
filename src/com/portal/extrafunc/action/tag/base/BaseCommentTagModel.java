 package com.portal.extrafunc.action.tag.base;
 
 import com.portal.extrafunc.service.CommentService;
 import com.portal.sysmgr.utils.TagModelTools;
 import freemarker.template.TemplateDirectiveModel;
 import freemarker.template.TemplateException;
 import freemarker.template.TemplateModel;
 import java.util.Map;
 import org.springframework.beans.factory.annotation.Autowired;
 
 public abstract class BaseCommentTagModel
   implements TemplateDirectiveModel
 {
   public static final String PARAM_SITE_ID = "siteId";
   public static final String PARAM_DOC_ID = "docId";
   public static final String PARAM_PARENT_ID = "parentId";
   public static final String PARAM_ORDER_BY = "orderBy";
   public static final String PARAM_IS_PAGE = "isPage";
 
   @Autowired
   protected CommentService commentService;
 
   protected Integer getSiteId(Map<String, TemplateModel> params)
     throws TemplateException
   {
     return TagModelTools.getInt("siteId", params);
   }
 
   protected Integer getDocId(Map<String, TemplateModel> params) throws TemplateException
   {
     return TagModelTools.getInt("docId", params);
   }
 
   protected Integer getParentId(Map<String, TemplateModel> params) throws TemplateException
   {
     return TagModelTools.getInt("parentId", params);
   }
 
   protected int getOrderBy(Map<String, TemplateModel> params) throws TemplateException
   {
     Integer orderBy = TagModelTools.getInt("orderBy", params);
     if (orderBy != null) {
       return orderBy.intValue();
     }
     return 0;
   }
 
   protected boolean getIsPage(Map<String, TemplateModel> params)
     throws TemplateException
   {
     Integer isPage = TagModelTools.getInt("isPage", params);
 
     return (isPage == null) || (!isPage.equals(Integer.valueOf(0)));
   }
 }


 
 
 
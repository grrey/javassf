 package com.portal.extrafunc.action.fnt;
 
 import com.javassf.basic.utils.ResponseUtils;
 import com.javassf.basic.utils.ServicesUtils;
 import com.portal.doccenter.entity.Article;
 import com.portal.doccenter.service.ArticleService;
 import com.portal.extrafunc.action.cache.CommentUpCache;
 import com.portal.extrafunc.entity.Comment;
 import com.portal.extrafunc.service.CommentService;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.utils.ContextTools;
 import com.portal.sysmgr.utils.ViewTools;
 import com.portal.usermgr.entity.User;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.json.JSONException;
 import org.json.JSONObject;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.ModelMap;
 import org.springframework.web.bind.annotation.PathVariable;
 import org.springframework.web.bind.annotation.RequestMapping;
 
 @Controller
 public class CommentAct
 {
   public static final String COMMENT_PAGE = "tpl.commentPage";
   public static final String COMMENT_LIST = "comment_list";
   public static final String PARENT_LIST = "parent_list";
 
   @Autowired
   private CommentService commentService;
 
   @Autowired
   private ArticleService articleService;
 
   @Autowired
   private CommentUpCache commentUpCache;
 
   @RequestMapping(value={"/comment-{docId:[0-9]+}.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
   public String page(@PathVariable Integer docId, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     return commentpage(docId, request, response, model);
   }
 
   @RequestMapping(value={"/comment-{docId:[0-9]+}_{page:[0-9]+}.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
   public String commentpage(@PathVariable Integer docId, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     Article doc = this.articleService.findById(docId);
     if (doc == null) {
       return ViewTools.showMessage(null, request, model, "文档不存在!", Integer.valueOf(0));
     }
     ViewTools.frontData(request, model, site);
     model.addAttribute("doc", doc);
     return ViewTools.getTplPath(request, site.getSolutionPath(), 
       "extrafunc/comment", "tpl.commentPage");
   }
 
   @RequestMapping(value={"/comment.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
   public void submit(Integer docId, Integer parentId, String content, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws JSONException
   {
     Site site = ContextTools.getSite(request);
     User user = ContextTools.getUser(request);
     JSONObject json = new JSONObject();
     if (docId == null) {
       json.put("success", false);
       json.put("status", -1);
       ResponseUtils.renderJson(response, json.toString());
       return;
     }
     Article doc = this.articleService.findById(docId);
     if (doc == null) {
       json.put("success", false);
       json.put("status", -2);
       ResponseUtils.renderJson(response, json.toString());
       return;
     }
     if (!doc.getCommentControl().booleanValue()) {
       json.put("success", false);
       json.put("status", -3);
       ResponseUtils.renderJson(response, json.toString());
       return;
     }
     String ip = ServicesUtils.getIpAddr(request);
     this.commentService.comment(content, ip, parentId, doc, user, site);
     json.put("success", true);
     json.put("status", 0);
 
     ResponseUtils.renderJson(response, json.toString());
   }
 
   @RequestMapping(value={"/commentList.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
   public String commentList(Integer docId, Integer pageNo, Integer count, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     Page page = this.commentService.getPage(site.getId(), docId, null, 
       Boolean.valueOf(true), Boolean.valueOf(true), 2, null, null, pageNo.intValue(), count.intValue());
     model.addAttribute("page", page);
     ViewTools.frontData(request, model, site);
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return ViewTools.getTplPath(null, site.getSolutionPath(), 
       "extrafunc/comment", "comment_list");
   }
 
   @RequestMapping(value={"/commentListByPre.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
   public String commentListByPre(Integer parentId, Integer pageNo, Integer count, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     Page page = this.commentService.getPage(site.getId(), null, 
       parentId, Boolean.valueOf(true), Boolean.valueOf(false), 3, null, null, pageNo.intValue(), count.intValue());
     Comment comment = this.commentService.findById(parentId);
     model.addAttribute("page", page);
     model.addAttribute("comment", comment);
     ViewTools.frontData(request, model, site);
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return ViewTools.getTplPath(null, site.getSolutionPath(), 
       "extrafunc/comment", "parent_list");
   }
 
   @RequestMapping(value={"/commentUps.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
   public void commentUps(Integer commentId, HttpServletRequest request, HttpServletResponse response) throws JSONException {
     JSONObject json = new JSONObject();
     Integer ups = this.commentUpCache.upAndGet(commentId);
     json.put("ups", ups);
     ResponseUtils.renderJson(response, json.toString());
   }
 }


 
 
 
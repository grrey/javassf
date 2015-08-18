 package com.portal.extrafunc.action.fnt;
 
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.utils.ContextTools;
 import com.portal.sysmgr.utils.ViewTools;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.ModelMap;
 import org.springframework.web.bind.annotation.RequestMapping;
 
 @Controller
 public class ForumAct
 {
   public static final String FORUM_INDEX = "tpl.forumIndex";
 
   @RequestMapping(value={"/forum.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
   public String index(HttpServletRequest request, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     ViewTools.frontData(request, model, site);
     return ViewTools.getTplPath(request, site.getSolutionPath(), 
       "extrafunc/forum", "tpl.forumIndex");
   }
 }


 
 
 
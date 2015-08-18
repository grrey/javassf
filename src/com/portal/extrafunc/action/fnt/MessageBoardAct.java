 package com.portal.extrafunc.action.fnt;
 
 import com.javassf.basic.utils.ResponseUtils;
 import com.portal.extrafunc.service.MessageBoardService;
 import com.portal.extrafunc.service.MessageTypeService;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.utils.ContextTools;
 import com.portal.sysmgr.utils.ViewTools;
 import java.util.List;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.json.JSONException;
 import org.json.JSONObject;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.ModelMap;
 import org.springframework.web.bind.annotation.RequestMapping;
 
 @Controller
 public class MessageBoardAct
 {
   public static final String BOARD_INPUT = "tpl.boardInput";
   public static final String BOARD_LIST = "board_list";
 
   @Autowired
   private MessageBoardService service;
 
   @Autowired
   private MessageTypeService typeService;
 
   @RequestMapping(value={"/messageboard.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
   public String input(HttpServletRequest request, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     List typeList = this.typeService.getList(site.getId(), null, 
       null);
     model.addAttribute("typeList", typeList);
     ViewTools.frontData(request, model, site);
     return ViewTools.getTplPath(request, site.getSolutionPath(), 
       "extrafunc/board", "tpl.boardInput");
   }
 
   @RequestMapping(value={"/messageboard.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
   public void submit(String title, String name, String mobile, String email, String address, String zipcode, Integer typeId, String content, HttpServletRequest request, HttpServletResponse response)
     throws JSONException
   {
     Site site = ContextTools.getSite(request);
     JSONObject json = new JSONObject();
     this.service.save(title, name, mobile, email, address, zipcode, typeId, 
       content, site);
     json.put("success", true);
     json.put("status", 1);
     ResponseUtils.renderJson(response, json.toString());
   }
 
   @RequestMapping({"/board/list.jsp"})
   public String list(Integer pageNo, Integer count, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     Page page = this.service.getPage(null, site.getId(), Boolean.valueOf(true), null, null, 
       pageNo.intValue(), count.intValue());
     model.addAttribute("page", page);
     ViewTools.frontData(request, model, site);
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return ViewTools.getTplPath(null, site.getSolutionPath(), 
       "common/tags", "board_list");
   }
 }


 
 
 
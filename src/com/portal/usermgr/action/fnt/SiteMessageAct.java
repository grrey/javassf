 package com.portal.usermgr.action.fnt;
 
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.utils.ContextTools;
 import com.portal.sysmgr.utils.ViewTools;
 import com.portal.usermgr.entity.SiteMessage;
 import com.portal.usermgr.entity.SiteMessageStatus;
 import com.portal.usermgr.entity.User;
 import com.portal.usermgr.service.SiteMessageService;
 import com.portal.usermgr.service.SiteMessageStatusService;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.ModelMap;
 import org.springframework.web.bind.annotation.PathVariable;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.servlet.mvc.support.RedirectAttributes;
 
 @Controller
 public class SiteMessageAct
 {
   public static final String SEND_LIST = "tpl.sendList";
   public static final String RECEIVE_LIST = "tpl.receiveList";
   public static final String TRASH_LIST = "tpl.trashList";
   public static final String MESSAGE_VIEW = "tpl.messageView";
 
   @Autowired
   private SiteMessageService messageService;
 
   @Autowired
   private SiteMessageStatusService statusService;
 
   @RequestMapping(value={"/message/sendlist.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
   public String sendList(HttpServletRequest request, ModelMap model)
   {
     return sendListpage(Integer.valueOf(1), request, model);
   }
 
   @RequestMapping(value={"/message/sendlist_{page:[0-9]+}.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
   public String sendListpage(@PathVariable Integer page, HttpServletRequest request, ModelMap model) {
     Site site = ContextTools.getSite(request);
     User user = ContextTools.getUser(request);
     ViewTools.frontData(request, model, site);
     if (user == null) {
       return ViewTools.showLogin(request, model, null);
     }
     ViewTools.frontPageData(request, model, page);
     return ViewTools.getTplPath(request, site.getSolutionPath(), 
       "user", "tpl.sendList");
   }
   @RequestMapping(value={"/message/receivelist.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
   public String receiveList(HttpServletRequest request, ModelMap model) {
     return receiveListpage(Integer.valueOf(1), request, model);
   }
 
   @RequestMapping(value={"/message/receivelist_{page:[0-9]+}.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
   public String receiveListpage(@PathVariable Integer page, HttpServletRequest request, ModelMap model) {
     Site site = ContextTools.getSite(request);
     User user = ContextTools.getUser(request);
     ViewTools.frontData(request, model, site);
     if (user == null) {
       return ViewTools.showLogin(request, model, null);
     }
     ViewTools.frontPageData(request, model, page);
     return ViewTools.getTplPath(request, site.getSolutionPath(), 
       "user", "tpl.receiveList");
   }
   @RequestMapping(value={"/message/trashlist.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
   public String trashList(HttpServletRequest request, ModelMap model) {
     return trashListpage(Integer.valueOf(1), request, model);
   }
 
   @RequestMapping(value={"/message/trashlist_{page:[0-9]+}.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
   public String trashListpage(@PathVariable Integer page, HttpServletRequest request, ModelMap model) {
     Site site = ContextTools.getSite(request);
     User user = ContextTools.getUser(request);
     ViewTools.frontData(request, model, site);
     if (user == null) {
       return ViewTools.showLogin(request, model, null);
     }
     ViewTools.frontPageData(request, model, page);
     return ViewTools.getTplPath(request, site.getSolutionPath(), 
       "user", "tpl.trashList");
   }
 
   @RequestMapping(value={"/message/view-{mId:[0-9]+}.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
   public String messageView(@PathVariable Integer mId, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     User user = ContextTools.getUser(request);
     ViewTools.frontData(request, model, site);
     if (user == null) {
       return ViewTools.showLogin(request, model, null);
     }
     if (mId == null) {
       return ViewTools.pageNotFound(response);
     }
     SiteMessage message = this.messageService.findById(mId);
     if (message == null) {
       return ViewTools.pageNotFound(response);
     }
     model.addAttribute("message", message);
     return ViewTools.getTplPath(request, site.getSolutionPath(), 
       "user", "tpl.messageView");
   }
 
   @RequestMapping(value={"/message/send.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
   public String messageSubmit(SiteMessage bean, Integer[] receiveIds, Integer replyId, HttpServletRequest request, HttpServletResponse response, ModelMap model, RedirectAttributes ra)
   {
     Site site = ContextTools.getSite(request);
     User user = ContextTools.getUser(request);
     ViewTools.frontData(request, model, site);
     if (user == null) {
       return ViewTools.showLogin(request, model, null);
     }
     if ((receiveIds == null) || (receiveIds.length == 0)) {
       return ViewTools.pageNotFound(response);
     }
     this.messageService.save(bean, user.getId(), receiveIds);
     ra.addFlashAttribute("msg", "信息发送成功");
     return "redirect:sendlist.jsp";
   }
 
   @RequestMapping(value={"/message/delete-{mId:[0-9]+}.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
   public String messageDelete(@PathVariable Integer mId, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     User user = ContextTools.getUser(request);
     ViewTools.frontData(request, model, site);
     if (user == null) {
       return ViewTools.showLogin(request, model, null);
     }
     if (mId == null) {
       return ViewTools.pageNotFound(response);
     }
     SiteMessage message = this.messageService.findById(mId);
     if (message == null) {
       return ViewTools.pageNotFound(response);
     }
     if (message.getSend().equals(user)) {
       this.messageService.deleteById(mId, user);
       return "redirect:sendlist.jsp";
     }
     SiteMessageStatus messageStatus = this.statusService.findByRecive(
       user.getId(), mId);
     if (messageStatus != null) {
       this.messageService.deleteById(mId, user);
       return "redirect:receivelist.jsp";
     }
     return ViewTools.pageNotFound(response);
   }
 }


 
 
 
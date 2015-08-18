 package com.portal.govcenter.action.fnt;
 
 import com.portal.govcenter.entity.Mailbox;
 import com.portal.govcenter.entity.MailboxExt;
 import com.portal.govcenter.service.MailboxService;
 import com.portal.govcenter.service.MailboxTypeService;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.utils.ContextTools;
 import com.portal.sysmgr.utils.ViewTools;
 import java.util.List;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.ModelMap;
 import org.springframework.web.bind.annotation.PathVariable;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.servlet.mvc.support.RedirectAttributes;
 
 @Controller
 public class MailboxAct
 {
   public static final String MAILBOX_INPUT = "tpl.mailboxInput";
   public static final String MAILBOX_LIST = "mailbox_list";
 
   @Autowired
   private MailboxTypeService typeService;
 
   @Autowired
   private MailboxService mailboxService;
 
   @RequestMapping(value={"/mailbox/input.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
   public String input(HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     List list = this.typeService.getList(site.getId());
     model.addAttribute("typeList", list);
     ViewTools.frontData(request, model, site);
     return ViewTools.getTplPath(null, site.getSolutionPath(), 
       "govcenter/interactive", "input");
   }
 
   @RequestMapping(value={"/mailbox/submit.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
   public String submit(Mailbox bean, MailboxExt ext, Integer departId, Integer typeId, HttpServletRequest request, HttpServletResponse response, RedirectAttributes ra)
   {
     Site site = ContextTools.getSite(request);
     this.mailboxService.save(bean, ext, site, departId, typeId);
     ra.addFlashAttribute("msg", "很感谢您的意见，我们会尽快做出处理！");
     return "redirect:input.jsp";
   }
 
   @RequestMapping(value={"/mailbox/list.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
   public String mailboxList(String name, Integer typeId, Integer pageNo, Integer count, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     Page page = this.mailboxService.getPageByTag(name, site.getId(), 
       null, typeId, pageNo.intValue(), count.intValue());
     model.addAttribute("page", page);
     model.addAttribute("name", name);
     model.addAttribute("typeId", typeId);
     ViewTools.frontData(request, model, site);
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return ViewTools.getTplPath(null, site.getSolutionPath(), 
       "govcenter/interactive", "mailbox_list");
   }
 
   @RequestMapping(value={"/mailbox/detail-{id:[0-9]+}.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
   public String mailboxDetail(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     Mailbox mailbox = this.mailboxService.findById(id);
     model.addAttribute("mailbox", mailbox);
     ViewTools.frontData(request, model, site);
     return ViewTools.getTplPath(null, site.getSolutionPath(), 
       "govcenter/interactive", "detail");
   }
 }


 
 
 
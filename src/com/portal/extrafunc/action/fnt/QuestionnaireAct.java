 package com.portal.extrafunc.action.fnt;
 
 import com.javassf.basic.utils.ServicesUtils;
 import com.portal.extrafunc.entity.QuestionDetail;
 import com.portal.extrafunc.entity.Questionnaire;
 import com.portal.extrafunc.service.QuestionDetailService;
 import com.portal.extrafunc.service.QuestionnaireService;
 import com.portal.extrafunc.service.SurveyThemeService;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.utils.ContextTools;
 import com.portal.sysmgr.utils.ViewTools;
 import com.portal.usermgr.entity.User;
 import java.util.Date;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.ModelMap;
 import org.springframework.web.bind.annotation.PathVariable;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.servlet.mvc.support.RedirectAttributes;
 
 @Controller
 public class QuestionnaireAct
 {
   public static final String QUESTION_LIST = "tpl.questionList";
   public static final String QUESTION_DETAIL = "tpl.questionDetail";
 
   @Autowired
   private QuestionnaireService questService;
 
   @Autowired
   private SurveyThemeService themeService;
 
   @Autowired
   private QuestionDetailService questionDetailService;
 
   @RequestMapping(value={"/questionList.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
   public String questionlist(HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     return questionlistpage(Integer.valueOf(1), request, response, model);
   }
 
   @RequestMapping(value={"/questionList_{page:[0-9]+}.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
   public String questionlistpage(@PathVariable Integer page, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     ViewTools.frontData(request, model, site);
     ViewTools.frontPageData(request, model, page);
     return ViewTools.getTplPath(request, site.getSolutionPath(), 
       "extrafunc/question", "tpl.questionList");
   }
 
   @RequestMapping(value={"/question-{qId:[0-9]+}.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
   public String input(@PathVariable Integer qId, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
     return inputpage(qId, Integer.valueOf(1), request, response, model);
   }
 
   @RequestMapping(value={"/question-{qId:[0-9]+}_{page:[0-9]+}.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
   public String inputpage(@PathVariable Integer qId, @PathVariable Integer page, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     Questionnaire question = this.questService.findById(qId);
     if (question == null) {
       return ViewTools.pageNotFound(response);
     }
     model.addAttribute("qId", qId);
     model.addAttribute("question", question);
     ViewTools.frontData(request, model, site);
     ViewTools.frontPageData(request, model, page);
     return ViewTools.getTplPath(request, site.getSolutionPath(), 
       "extrafunc/question", "tpl.questionDetail");
   }
 
   @RequestMapping(value={"/questionSubmit.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
   public String questionSubmit(Integer qId, HttpServletRequest request, HttpServletResponse response, ModelMap model, RedirectAttributes ra) {
     Questionnaire question = this.questService.findById(qId);
     if (question == null) {
       return ViewTools.pageNotFound(response);
     }
     User user = ContextTools.getUser(request);
     String result = checklogin(question, user, request, model);
     if (result != null) {
       return result;
     }
     String res = checkVote(question, user, request, model);
     if (res != null) {
       ra.addFlashAttribute("msg", res);
       return "redirect:question-" + qId + ".jsp";
     }
     String ip = ServicesUtils.getIpAddr(request);
     Map m = ServicesUtils.getRequestMap(request, "theme");
     Map ml = ServicesUtils.getRequestMapList(request, 
       "thlist");
     this.themeService.voteSurvey(qId, m, ml, ip, user);
     ra.addFlashAttribute("msg", "提交成功!");
     return "redirect:question-" + qId + ".jsp";
   }
 
   private String checklogin(Questionnaire question, User user, HttpServletRequest request, ModelMap model)
   {
     if ((question.getNeedLogin().booleanValue()) && 
       (user == null)) {
       return ViewTools.showLogin(request, model, "必须登录才可以投票!");
     }
 
     return null;
   }
 
   private String checkVote(Questionnaire question, User user, HttpServletRequest request, ModelMap model)
   {
     if (!question.getEnable().booleanValue()) {
       return "投票已关闭!";
     }
     if ((question.getStartTime() != null) && 
       (!question.getStartTime().before(new Date()))) {
       return "投票还未开始!";
     }
     if ((question.getEndTime() != null) && 
       (!question.getEndTime().after(new Date()))) {
       return "投票已结束!";
     }
     if (question.getRepeateTime().intValue() == 9999999) {
       if (question.getNeedLogin().booleanValue()) {
         QuestionDetail qd = this.questionDetailService.getDetail(
           question.getId(), user.getId(), null);
         if (qd != null) {
           return "你已经投过票了,不能重复投票!";
         }
       }
       if (question.getRestrictIp().booleanValue()) {
         String ip = ServicesUtils.getIpAddr(request);
         QuestionDetail qd = this.questionDetailService.getDetail(
           question.getId(), null, ip);
         if (qd != null) {
           return "你已经投过票了,不能重复投票!";
         }
       }
     }
     long now = System.currentTimeMillis();
     if ((question.getRepeateTime().intValue() > 0) && 
       (question.getRepeateTime().intValue() < 9999999)) {
       Integer d = question.getRepeateTime();
       long sencond = d.intValue() * 60 * 60 * 1000;
       if (question.getNeedLogin().booleanValue()) {
         QuestionDetail qd = this.questionDetailService.getDetail(
           question.getId(), user.getId(), null);
         if (qd.getCreateTime().getTime() + sencond > now) {
           return "你已经投过票了," + d + "小时内不能重复投票!";
         }
       }
       if (question.getRestrictIp().booleanValue()) {
         String ip = ServicesUtils.getIpAddr(request);
         QuestionDetail qd = this.questionDetailService.getDetail(
           question.getId(), null, ip);
         if (qd.getCreateTime().getTime() + sencond > now) {
           return "你已经投过票了," + d + "小时内不能重复投票!";
         }
       }
     }
     return null;
   }
 }


 
 
 
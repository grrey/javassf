package com.portal.doccenter.action.fnt;

import com.javassf.basic.utils.ResponseUtils;
import com.portal.datacenter.docdata.service.KeywordService;
import com.portal.doccenter.action.fnt.cache.DocViewsCountCache;
import com.portal.doccenter.entity.Article;
import com.portal.doccenter.entity.ArticleAttachment;
import com.portal.doccenter.entity.Channel;
import com.portal.doccenter.service.ArticleService;
import com.portal.doccenter.service.ArticleSignService;
import com.portal.doccenter.service.DocStatisService;
import com.portal.sysmgr.entity.Site;
import com.portal.sysmgr.staticpage.StaticPageService;
import com.portal.sysmgr.utils.ContextTools;
import com.portal.sysmgr.utils.ViewTools;
import com.portal.usermgr.entity.Group;
import com.portal.usermgr.entity.Member;
import com.portal.usermgr.entity.User;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DocAct
{

  @Autowired
  private ArticleService articleService;

  @Autowired
  private KeywordService keywordService;

  @Autowired
  private DocViewsCountCache viewsCountCache;

  @Autowired
  private StaticPageService staticPageService;

  @Autowired
  private ArticleSignService signService;

  @Autowired
  private DocStatisService statisService;

  @Autowired
  private ServletContext ctx;

  @RequestMapping({"/doc/{docId:[0-9]+}.jsp"})
  public String docdetail(@PathVariable Integer docId, HttpServletRequest request, HttpServletResponse response, ModelMap model)
  {
    return docdetailpageNo(docId, Integer.valueOf(1), request, response, model);
  }

  @RequestMapping({"/doc/{docId:[0-9]+}_{page:[0-9]+}.jsp"})
  public String docdetailpageNo(@PathVariable Integer docId, @PathVariable Integer page, HttpServletRequest request, HttpServletResponse response, ModelMap model)
  {
    Site site = ContextTools.getSite(request);
    Article doc = this.articleService.findById(docId);
    if (doc == null) {
      return ViewTools.pageNotFound(response);
    }
    if (!site.equals(doc.getSite())) {
      return ViewTools.pageNotFound(response);
    }
    if (!doc.isChecked()) {
      return ViewTools.pageNotFound(response);
    }
    if (!StringUtils.isBlank(doc.getLink())) {
      return "redirect:" + doc.getLink();
    }
    if (doc.getStaticDoc()) {
      this.staticPageService.staticArticleCheck(doc, page);
      return doc.getStaticRealPath(page);
    }
    String result = checkView(doc, request, response, model);
    if (result != null) {
      return result;
    }
    String ctx = site.getContextPath();
    ctx = ctx == null ? "" : ctx;
    String txt = doc.getTxtByNo(page.intValue());
    txt = StringUtils.replace(txt, "../..", ctx);
    txt = this.keywordService.attachKeyword(site.getId(), txt);
    Pageable pa = new PageRequest(page.intValue() - 1, 1);
    Page p = new PageImpl(Collections.emptyList(), pa, 
      doc.getPageCount());
    model.addAttribute("page", p);
    model.addAttribute("doc", doc);
    model.addAttribute("channel", doc.getChannel());
    model.addAttribute("title", doc.getTitle());
    model.addAttribute("txt", txt);
    ViewTools.frontData(request, model, site);
    ViewTools.frontPageData(doc.getUrl(page), model, page);
    return doc.getTplContentOrDef();
  }

  @RequestMapping({"/doc/viewCount.jsp"})
  public void viewsCount(Integer docId, HttpServletRequest request, HttpServletResponse response) throws JSONException {
    JSONObject json = new JSONObject();
    Integer views = this.viewsCountCache.viewsCount(docId);
    json.put("views", views);
    ResponseUtils.renderJson(response, json.toString());
  }
  @RequestMapping({"/doc/signcheck.jsp"})
  public String sign(Integer docId, HttpServletRequest request, ModelMap model) {
    User user = ContextTools.getUser(request);
    if (user == null) {
      Article doc = this.articleService.findById(docId);
      return ViewTools.showLogin(doc.getUrl(), request, model, 
        "必须登录才可以签收!");
    }
    return null;
  }

  @RequestMapping({"/doc/sign.jsp"})
  public void sign(Integer docId, HttpServletRequest request, HttpServletResponse response) throws JSONException {
    JSONObject json = new JSONObject();
    User user = ContextTools.getUser(request);
    this.signService.save(docId, user);
    json.put("success", true);
    ResponseUtils.renderJson(response, json.toString());
  }

  @RequestMapping({"/doc/ups.jsp"})
  public void ups(Integer docId, HttpServletRequest request, HttpServletResponse response) throws JSONException {
    JSONObject json = new JSONObject();
    if (docId == null) {
      json.put("success", false);
    } else {
      this.statisService.ups(docId);
      json.put("success", true);
    }
    ResponseUtils.renderJson(response, json.toString());
  }

  @RequestMapping({"/doc/treads.jsp"})
  public void treads(Integer docId, HttpServletRequest request, HttpServletResponse response) throws JSONException {
    JSONObject json = new JSONObject();
    if (docId == null) {
      json.put("success", false);
    } else {
      this.statisService.treads(docId);
      json.put("success", true);
    }
    ResponseUtils.renderJson(response, json.toString());
  }

  @RequestMapping({"/att/dowload-{aId:[0-9]+}-{index}.jsp"})
  public void docAttDowload(@PathVariable Integer aId, Integer index, HttpServletRequest request, HttpServletResponse response, ModelMap model)
  {
    ArticleAttachment att = checkAtt(aId, index);
    if (att == null) {
      return;
    }
    File file = new File(this.ctx.getRealPath(att.getPath()));
    response.setContentType("text/html; charset=UTF-8");
    response.setContentType("application/x-msdownload");
    response.setHeader("Content-Disposition", 
      "attachment;filename=" + att.getName());
    response.setContentLength((int)file.length());
    try
    {
      FileInputStream fis = new FileInputStream(file);
      BufferedInputStream buff = new BufferedInputStream(fis);
      byte[] b = new byte[1024];
      long k = 0L;
      try
      {
        OutputStream myout = response.getOutputStream();
        while (k < file.length()) {
          int j = buff.read(b, 0, 1024);
          k += j;
          myout.write(b, 0, j);
        }
        myout.flush();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  private String checkView(Article doc, HttpServletRequest request, HttpServletResponse response, ModelMap model)
  {
    User user = ContextTools.getUser(request);
    Site site = doc.getSite();
    Set<Group> groups = doc.getViewGroupsExt();
    if ((!doc.getStatus().equals(Byte.valueOf((byte) 2))) && (
      (user == null) || (user.getAdmin() == null))) {
      return ViewTools.pageNotFound(response);
    }

    if ((groups != null) && (groups.size() > 0)) {
      if (user == null) {
        return ViewTools.showLogin(request, model, "您需要登录才能浏览该信息!");
      }
      if ((user.getAdmin() == null) && 
        (user.getMember() != null)) {
        Integer groupId = user.getMember().getGroup(site.getId())
          .getId();
        for (Group group : groups) {
          if (group.getId().equals(groupId)) {
            return null;
          }
        }
        String groupName = user.getMember().getGroup(site.getId())
          .getName();
        String msg = "您所在的会员组：" + groupName + "没有访问该页面的权限";
        return ViewTools.showMessage(doc.getChannel().getUrl(), 
          request, model, msg, Integer.valueOf(0));
      }
    }

    return null;
  }

  private ArticleAttachment checkAtt(Integer aId, Integer index) {
    Article a = this.articleService.findById(aId);
    if (a == null) {
      return null;
    }
    List atts = a.getAtts();
    if (atts == null) {
      return null;
    }
    if (index.intValue() > atts.size() - 1) {
      return null;
    }
    ArticleAttachment att = (ArticleAttachment)atts.get(index.intValue());
    return att;
  }
}
package com.portal.usermgr.action.fnt;


import com.javassf.basic.security.encoder.PwdEncoder;

import com.javassf.basic.upload.FileRepository;

import com.javassf.basic.utils.ResponseUtils;

import com.javassf.basic.utils.ServicesUtils;

import com.portal.doccenter.entity.Article;

import com.portal.doccenter.entity.ArticleExt;

import com.portal.doccenter.entity.ArticleTxt;

import com.portal.doccenter.entity.Model;

import com.portal.doccenter.service.ArticleService;

import com.portal.doccenter.service.ArticleTypeService;

import com.portal.doccenter.service.ChannelService;

import com.portal.doccenter.service.ModelFieldService;

import com.portal.doccenter.service.ModelService;

import com.portal.sysmgr.entity.Site;

import com.portal.sysmgr.utils.ContextTools;

import com.portal.sysmgr.utils.ViewTools;

import com.portal.usermgr.entity.Member;

import com.portal.usermgr.entity.User;

import com.portal.usermgr.service.MemberService;

import com.portal.usermgr.service.UserBindService;

import java.util.List;

import java.util.Map;

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

import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class MemberAct
{
	public static final String MEMBER_INDEX = "tpl.memberIndex";
	public static final String MEMBER_EDIT_PASS = "tpl.memberEditPass";
	public static final String MEMBER_EDIT_INFO = "tpl.memberEditInfo";
	public static final String DOC_LIST = "tpl.docList";
	public static final String DOC_ADD = "tpl.docAdd";
	public static final String DOC_EDIT = "tpl.docEdit";
	public static final String MY_THEME = "tpl.myTheme";
	public static final String MY_REPLY = "tpl.myReply";
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private ModelService modelService;
	
	@Autowired
	private ModelFieldService modelFieldService;
	
	@Autowired
	private ArticleTypeService articleTypeService;
	
	@Autowired
	private UserBindService userBindService;
	
	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private ArticleService service;
	
	@Autowired
	private PwdEncoder pwdEncoder;
	
	@Autowired
	private FileRepository fileRepository;

	@RequestMapping(value = { "/member/index.jsp" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String index(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Site site = ContextTools.getSite(request);
		User user = ContextTools.getUser(request);
		if (user == null) {
			return ViewTools.showLogin(request, model, "必须登录才可以访问该页面!");
		}
		ViewTools.frontData(request, model, site);
		return ViewTools.getTplPath(request, site.getSolutionPath(), "user", "tpl.memberIndex");
	}

	@RequestMapping(value = { "/member/editPass.jsp" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String editPass(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Site site = ContextTools.getSite(request);
		User user = ContextTools.getUser(request);
		if (user == null) {
			return ViewTools.showLogin(request, model, "必须登录才可以访问该页面!");
		}
		ViewTools.frontData(request, model, site);
		return ViewTools.getTplPath(request, site.getSolutionPath(), "user", "tpl.memberEditPass");
	}

	@RequestMapping(value = { "/member/editPass.jsp" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public String editPassSumbit(String oldpassword, String password, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Site site = ContextTools.getSite(request);
		User user = ContextTools.getUser(request);
		if (user == null) {
			return ViewTools.showLogin(request, model, "必须登录才可以访问该页面!");
		}
		if (user.getPassword().equals(this.pwdEncoder.encodePassword(oldpassword))) {
			this.memberService.updatePass(user.getId(), password);
			model.addAttribute("msg", "密码修改成功!");
			model.addAttribute("status", Integer.valueOf(1));
		} else {
			model.addAttribute("msg", "原密码错误，修改失败!");
			model.addAttribute("status", Integer.valueOf(0));
		}
		ViewTools.frontData(request, model, site);
		return ViewTools.getTplPath(request, site.getSolutionPath(), "user", "tpl.memberEditPass");
	}

	@RequestMapping(value = { "/member/editInfo.jsp" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String editInfo(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Site site = ContextTools.getSite(request);
		User user = ContextTools.getUser(request);
		if (user == null) {
			return ViewTools.showLogin(request, model, "必须登录才可以访问该页面!");
		}
		ViewTools.frontData(request, model, site);
		return ViewTools.getTplPath(request, site.getSolutionPath(), "user", "tpl.memberEditInfo");
	}

	@RequestMapping(value = { "/member/editInfo.jsp" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public String editInfoSubmit(User u, Member m, @RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response, ModelMap model, RedirectAttributes ra) {
		Site site = ContextTools.getSite(request);
		User user = ContextTools.getUser(request);
		if (user == null) {
			return ViewTools.showLogin(request, model, "必须登录才可以访问该页面!");
		}
		if (!file.isEmpty()) {
			String fileUrl = this.fileRepository.uploadFile(file, site.getUploadPath());
			m.setAvatar(fileUrl);
		}
		m.setRegisterIp(ServicesUtils.getIpAddr(request));
		this.memberService.updateMember(u, m, null, site.getId());
		ra.addFlashAttribute("msg", "基本资料修改成功!");
		return "redirect:index.jsp";
	}

	@RequestMapping(value = { "/member/docList.jsp" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String docList(String title, Integer chnlId, Integer[] typeIds, Integer[] modelIds, boolean top, boolean recommend, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return docListpage(Integer.valueOf(1), title, chnlId, typeIds, modelIds, top, recommend, request, response, model);
	}

	@RequestMapping(value = { "/member/docList_{page:[0-9]+}.jsp" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String docListpage(@PathVariable Integer page, String title, Integer chnlId, Integer[] typeIds, Integer[] modelIds, boolean top, boolean recommend, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Site site = ContextTools.getSite(request);
		User user = ContextTools.getUser(request);
		if (user == null) {
			return ViewTools.showLogin(request, model, "必须登录才可以访问该页面!");
		}
		List typeList = this.articleTypeService.getList(false, null, null);
		Page p = this.service.getPageDocByMember(title, typeIds, modelIds, top, recommend, site.getId(), user.getId(), chnlId, page.intValue(), 15);
		model.addAttribute("page", p);
		model.addAttribute("typeList", typeList);
		model.addAttribute("modelList", this.modelService.getList(false, null, null));
		ViewTools.frontData(request, model, site);
		ViewTools.frontPageData(request, model, page);
		return ViewTools.getTplPath(request, site.getSolutionPath(), "user", "tpl.docList");
	}

	@RequestMapping(value = { "/member/docAdd-{modelId:[0-9]+}.jsp" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String docAdd(@PathVariable Integer modelId, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Site site = ContextTools.getSite(request);
		User user = ContextTools.getUser(request);
		if (user == null) {
			return ViewTools.showLogin(request, model, "必须登录才可以访问该页面!");
		}
		Model m = this.modelService.findById(modelId);
		if (m == null) {
			return ViewTools.pageNotFound(response);
		}
		List fieldList = this.modelFieldService.getList(m.getId(), false, null, null);
		List typeList = this.articleTypeService.getList(false, null, null);
		ViewTools.frontData(request, model, site);
		model.addAttribute("model", m);
		model.addAttribute("fieldList", fieldList);
		model.addAttribute("typeList", typeList);
		return ViewTools.getTplPath(request, site.getSolutionPath(), "user", "tpl.docAdd");
	}

	@RequestMapping(value = { "/member/docSave.jsp" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public String docSave(Article bean, ArticleExt ext, ArticleTxt txt, Integer modelId, String[] attPaths, String[] attNames, String[] imgPaths, String[] imgDescs, Boolean[] thumbs, String[] imgStyles, Integer channelId, HttpServletRequest request, HttpServletResponse response,
			ModelMap model, RedirectAttributes ra) {
		Site site = ContextTools.getSite(request);
		User user = ContextTools.getUser(request);
		if (user == null) {
			return ViewTools.showLogin(request, model, "必须登录才可以访问该页面!");
		}
		bean.setSite(site);
		bean.setAttr(ServicesUtils.getRequestMap(request, "attr_"));
		bean = this.service.save(bean, ext, txt, site, user, null, attPaths, attNames, imgPaths, imgDescs, thumbs, imgStyles, channelId, modelId, true);
		ra.addFlashAttribute("msg", "文档添加成功，请等待审核!");
		return "redirect:docList.jsp";
	}

	@RequestMapping(value = { "/member/docEdit-{id:[0-9]+}.jsp" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String docEdit(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Site site = ContextTools.getSite(request);
		User user = ContextTools.getUser(request);
		if (user == null) {
			return ViewTools.showLogin(request, model, "必须登录才可以访问该页面!");
		}
		Article article = this.service.findById(id);
		if ((article == null) || (!article.getUser().equals(user))) {
			return ViewTools.pageNotFound(response);
		}
		List fieldList = this.modelFieldService.getList(article.getModel().getId(), false, null, null);
		List typeList = this.articleTypeService.getList(false, null, null);
		model.addAttribute("article", article);
		model.addAttribute("channel", article.getChannel());
		model.addAttribute("fieldList", fieldList);
		model.addAttribute("typeList", typeList);
		ViewTools.frontData(request, model, site);
		return ViewTools.getTplPath(request, site.getSolutionPath(), "user", "tpl.docEdit");
	}

	@RequestMapping(value = { "/member/docUpdate.jsp" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public String docUpdate(Article bean, ArticleExt ext, ArticleTxt txt, Integer[] channelIds, Integer[] topicIds, Integer[] viewGroupIds, String[] attPaths, String[] attNames, String[] imgPaths, String[] imgDescs, Boolean[] thumbs, String[] imgStyles, Integer channelId,
			HttpServletRequest request, ModelMap model, RedirectAttributes ra) {
		User user = ContextTools.getUser(request);
		Map attr = ServicesUtils.getRequestMap(request, "attr_");
		bean = this.service.update(bean, ext, txt, channelIds, viewGroupIds, attPaths, attNames, imgPaths, imgDescs, thumbs, imgStyles, attr, channelId, user, true);
		ra.addFlashAttribute("msg", "文档修改成功，请等待审核!");
		return "redirect:docList.jsp";
	}

	@RequestMapping(value = { "/member/docDel-{id:[0-9]+}.jsp" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String docDel(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response, ModelMap model, RedirectAttributes ra) {
		User user = ContextTools.getUser(request);
		if (user == null) {
			return ViewTools.showLogin(request, model, "必须登录才可以访问该页面!");
		}
		Article article = this.service.findById(id);
		if ((article == null) || (!article.getUser().equals(user))) {
			return ViewTools.pageNotFound(response);
		}
		this.service.cycle(id);
		ra.addFlashAttribute("msg", "文档删除成功!");
		return "redirect:docList.jsp";
	}

	@RequestMapping(value = { "/member/themeList.jsp" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String themeList(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return themeListpage(Integer.valueOf(1), request, response, model);
	}

	@RequestMapping(value = { "/member/themeList_{page:[0-9]+}.jsp" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String themeListpage(@PathVariable Integer page, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Site site = ContextTools.getSite(request);
		User user = ContextTools.getUser(request);
		if (user == null) {
			return ViewTools.showLogin(request, model, "必须登录才可以访问该页面!");
		}
		ViewTools.frontData(request, model, site);
		ViewTools.frontPageData(request, model, page);
		return ViewTools.getTplPath(request, site.getSolutionPath(), "user", "tpl.myTheme");
	}

	@RequestMapping(value = { "/member/replyList.jsp" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String replyList(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return replyListpage(Integer.valueOf(1), request, response, model);
	}

	@RequestMapping(value = { "/member/replyList_{page:[0-9]+}.jsp" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String replyListpage(@PathVariable Integer page, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Site site = ContextTools.getSite(request);
		User user = ContextTools.getUser(request);
		if (user == null) {
			return ViewTools.showLogin(request, model, "必须登录才可以访问该页面!");
		}
		ViewTools.frontData(request, model, site);
		ViewTools.frontPageData(request, model, page);
		return ViewTools.getTplPath(request, site.getSolutionPath(), "user", "tpl.myReply");
	}

	@RequestMapping(value = { "/userbind.jsp" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public void userbind(String username, String password, Integer status, HttpServletRequest request, HttpServletResponse response) throws JSONException {
		JSONObject json = new JSONObject();
		User user = ContextTools.getUser(request);
		if (user == null) {
			return;
		}
		this.userBindService.save(user, username, password, status);
		ResponseUtils.renderJson(response, json.toString());
	}

	@RequestMapping({ "/member/channeltree.jsp" })
	public String addtree(Integer parentId, Integer modelId, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		model.addAttribute("parentId", parentId);
		Site site = ContextTools.getSite(request);
		User user = ContextTools.getUser(request);
		List list = this.channelService.getChannelByModelAndMember(parentId, modelId, user, site.getId());
		model.addAttribute("list", list);
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/json;charset=UTF-8");
		return ViewTools.getTplPath(null, site.getSolutionPath(), "user", "channeltree");
	}
}

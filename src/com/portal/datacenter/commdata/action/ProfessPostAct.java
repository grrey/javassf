package com.portal.datacenter.commdata.action;

import com.portal.datacenter.commdata.entity.ProfessPost;
import com.portal.datacenter.commdata.service.ProfessPostService;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProfessPostAct {
	private static final Logger log = LoggerFactory.getLogger(ProfessPostAct.class);

	@Autowired
	private ProfessPostService service;

	@RequiresPermissions({ "admin:professfost:list" })
	@RequestMapping({ "/professfost/v_list.do" })
	public String list(Integer pageNo, HttpServletRequest request, ModelMap model) {
		Page page = this.service.getPage(pageNo.intValue(), 20);
		model.addAttribute("page", page);
		return "professfost/list";
	}

	@RequiresPermissions({ "admin:professfost:add" })
	@RequestMapping({ "/professfost/v_add.do" })
	public String add(ModelMap model) {
		return "professfost/add";
	}

	@RequiresPermissions({ "admin:professfost:edit" })
	@RequestMapping({ "/professfost/v_edit.do" })
	public String edit(Integer id, Integer pageNo, HttpServletRequest request, ModelMap model) {
		ProfessPost post = this.service.findById(id);
		model.addAttribute("professPost", post);
		model.addAttribute("pageNo", pageNo);
		return "professfost/edit";
	}

	@RequiresPermissions({ "admin:professfost:save" })
	@RequestMapping({ "/professfost/o_save.do" })
	public String save(ProfessPost bean, HttpServletRequest request, ModelMap model) {
		bean = this.service.save(bean);
		log.info("save ProfessPost id={}", bean.getId());
		return "redirect:v_list.do";
	}

	@RequiresPermissions({ "admin:professfost:update" })
	@RequestMapping({ "/professfost/o_update.do" })
	public String update(ProfessPost bean, Integer pageNo, HttpServletRequest request, ModelMap model) {
		bean = this.service.update(bean);
		log.info("update ProfessPost id={}.", bean.getId());
		return list(pageNo, request, model);
	}

	@RequiresPermissions({ "admin:professfost:delete" })
	@RequestMapping({ "/professfost/o_delete.do" })
	public String delete(Integer[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
		ProfessPost[] beans = this.service.deleteByIds(ids);
		for (ProfessPost bean : beans) {
			log.info("delete ProfessPost id={}", bean.getId());
		}
		return list(pageNo, request, model);
	}
}

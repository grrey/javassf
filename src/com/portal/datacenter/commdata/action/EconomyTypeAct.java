package com.portal.datacenter.commdata.action;

import com.portal.datacenter.commdata.entity.EconomyType;
import com.portal.datacenter.commdata.service.EconomyTypeService;
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
public class EconomyTypeAct {
	private static final Logger log = LoggerFactory.getLogger(EconomyTypeAct.class);

	@Autowired
	private EconomyTypeService service;

	@RequiresPermissions({ "admin:economytype:list" })
	@RequestMapping({ "/economytype/v_list.do" })
	public String list(Integer pageNo, HttpServletRequest request, ModelMap model) {
		Page page = this.service.getPage(pageNo.intValue(), 20);
		model.addAttribute("page", page);
		return "economytype/list";
	}

	@RequiresPermissions({ "admin:economytype:add" })
	@RequestMapping({ "/economytype/v_add.do" })
	public String add(ModelMap model) {
		return "economytype/add";
	}

	@RequiresPermissions({ "admin:economytype:edit" })
	@RequestMapping({ "/economytype/v_edit.do" })
	public String edit(Integer id, Integer pageNo, HttpServletRequest request, ModelMap model) {
		model.addAttribute("economyType", this.service.findById(id));
		model.addAttribute("pageNo", pageNo);
		return "economytype/edit";
	}

	@RequiresPermissions({ "admin:economytype:save" })
	@RequestMapping({ "/economytype/o_save.do" })
	public String save(EconomyType bean, HttpServletRequest request, ModelMap model) {
		bean = this.service.save(bean);
		log.info("save EconomyType id={}", bean.getId());
		return "redirect:v_list.do";
	}

	@RequiresPermissions({ "admin:economytype:update" })
	@RequestMapping({ "/economytype/o_update.do" })
	public String update(EconomyType bean, Integer pageNo, HttpServletRequest request, ModelMap model) {
		bean = this.service.update(bean);
		log.info("update EconomyType id={}.", bean.getId());
		return list(pageNo, request, model);
	}

	@RequiresPermissions({ "admin:economytype:delete" })
	@RequestMapping({ "/economytype/o_delete.do" })
	public String delete(Integer[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
		EconomyType[] beans = this.service.deleteByIds(ids);
		for (EconomyType bean : beans) {
			log.info("delete EconomyType id={}", bean.getId());
		}
		return list(pageNo, request, model);
	}
}

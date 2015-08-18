package com.portal.datacenter.commdata.action;

import com.portal.datacenter.commdata.entity.Specialty;
import com.portal.datacenter.commdata.service.SpecialtyService;
import java.util.List;
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
public class SpecialtyAct {
	private static final Logger log = LoggerFactory.getLogger(SpecialtyAct.class);

	@Autowired
	private SpecialtyService service;

	@RequiresPermissions({ "admin:specialty:list" })
	@RequestMapping({ "/specialty/v_list.do" })
	public String list(String key, Integer pageNo, HttpServletRequest request, ModelMap model) {
		Page p = this.service.getPage(key, pageNo.intValue(), 20);
		model.addAttribute("p", p);
		model.addAttribute("key", key);
		return "specialty/list";
	}

	@RequiresPermissions({ "admin:specialty:add" })
	@RequestMapping({ "/specialty/v_add.do" })
	public String add(ModelMap model) {
		List specialtyList = this.service.getSpecialtyList(null);
		model.addAttribute("specialtyList", specialtyList);
		return "specialty/add";
	}

	@RequiresPermissions({ "admin:specialty:edit" })
	@RequestMapping({ "/specialty/v_edit.do" })
	public String edit(Integer id, Integer pageNo, HttpServletRequest request, ModelMap model) {
		List specialtyList = this.service.getSpecialtyList(id);
		Specialty specialty = this.service.findById(id);
		if (specialty.getParent() != null) {
			model.addAttribute("parentId", specialty.getParent().getId());
		}
		model.addAttribute("specialtyList", Boolean.valueOf(specialtyList.remove(specialty)));
		model.addAttribute("specialty", specialty);
		model.addAttribute("pageNo", pageNo);
		return "specialty/edit";
	}

	@RequiresPermissions({ "admin:specialty:save" })
	@RequestMapping({ "/specialty/o_save.do" })
	public String save(Specialty bean, Integer parentId, HttpServletRequest request, ModelMap model) {
		bean = this.service.save(bean, parentId);
		log.info("save Specialty id={}", bean.getId());
		return "redirect:v_list.do";
	}

	@RequiresPermissions({ "admin:specialty:update" })
	@RequestMapping({ "/specialty/o_update.do" })
	public String update(Specialty bean, Integer parentId, Integer pageNo, HttpServletRequest request, ModelMap model) {
		bean = this.service.update(bean, parentId);
		log.info("update Specialty id={}.", bean.getId());
		return list(null, pageNo, request, model);
	}

	@RequiresPermissions({ "admin:specialty:delete" })
	@RequestMapping({ "/specialty/o_delete.do" })
	public String delete(Integer[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
		Specialty[] beans = this.service.deleteByIds(ids);
		for (Specialty bean : beans) {
			log.info("delete Specialty id={}", bean.getId());
		}
		return list(null, pageNo, request, model);
	}
}

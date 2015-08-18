package com.portal.datacenter.commdata.action;

import com.portal.datacenter.commdata.entity.UnitType;
import com.portal.datacenter.commdata.service.UnitTypeService;
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
public class UnitTypeAct {
	private static final Logger log = LoggerFactory.getLogger(UnitTypeAct.class);

	@Autowired
	private UnitTypeService service;

	@RequiresPermissions({ "admin:unittype:list" })
	@RequestMapping({ "/unittype/v_list.do" })
	public String list(Integer pageNo, HttpServletRequest request, ModelMap model) {
		Page p = this.service.getPage(pageNo.intValue(), 20);
		model.addAttribute("p", p);
		return "unittype/list";
	}

	@RequiresPermissions({ "admin:unittype:add" })
	@RequestMapping({ "/unittype/v_add.do" })
	public String add(ModelMap model) {
		return "unittype/add";
	}

	@RequiresPermissions({ "admin:unittype:edit" })
	@RequestMapping({ "/unittype/v_edit.do" })
	public String edit(Integer id, HttpServletRequest request, ModelMap model) {
		model.addAttribute("unitType", this.service.findById(id));
		return "unittype/edit";
	}

	@RequiresPermissions({ "admin:unittype:save" })
	@RequestMapping({ "/unittype/o_save.do" })
	public String save(UnitType bean, HttpServletRequest request, ModelMap model) {
		bean = this.service.save(bean);
		log.info("save UnitType id={}", bean.getId());
		return "redirect:v_list.do";
	}

	@RequiresPermissions({ "admin:unittype:update" })
	@RequestMapping({ "/unittype/o_update.do" })
	public String update(UnitType bean, Integer pageNo, HttpServletRequest request, ModelMap model) {
		bean = this.service.update(bean);
		log.info("update UnitType id={}.", bean.getId());
		return list(pageNo, request, model);
	}

	@RequiresPermissions({ "admin:unittype:delete" })
	@RequestMapping({ "/unittype/o_delete.do" })
	public String delete(Integer[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
		UnitType[] beans = this.service.deleteByIds(ids);
		for (UnitType bean : beans) {
			log.info("delete UnitType id={}", bean.getId());
		}
		return list(pageNo, request, model);
	}
}

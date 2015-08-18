package com.portal.datacenter.commdata.action;

import com.portal.datacenter.commdata.entity.Metier;
import com.portal.datacenter.commdata.service.MetierService;
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
public class MetierAct {
	private static final Logger log = LoggerFactory.getLogger(MetierAct.class);

	@Autowired
	private MetierService service;

	@RequiresPermissions({ "admin:metier:list" })
	@RequestMapping({ "/metier/v_list.do" })
	public String list(String key, Integer pageNo, HttpServletRequest request, ModelMap model) {
		Page page = this.service.getPage(key, pageNo.intValue(), 20);
		model.addAttribute("page", page);
		model.addAttribute("key", key);
		return "metier/list";
	}

	@RequiresPermissions({ "admin:metier:add" })
	@RequestMapping({ "/metier/v_add.do" })
	public String add(ModelMap model) {
		List metierList = this.service.getMetierList(null);
		model.addAttribute("metierList", metierList);
		return "metier/add";
	}

	@RequiresPermissions({ "admin:metier:edit" })
	@RequestMapping({ "/metier/v_edit.do" })
	public String edit(Integer id, Integer pageNo, HttpServletRequest request, ModelMap model) {
		Metier metier = this.service.findById(id);
		List metierList = this.service.getMetierList(id);
		if (metier.getParent() != null) {
			model.addAttribute("parentId", metier.getParent().getId());
		}
		model.addAttribute("metierList", metierList);
		model.addAttribute("metier", metier);
		model.addAttribute("pageNo", pageNo);
		return "metier/edit";
	}

	@RequiresPermissions({ "admin:metier:save" })
	@RequestMapping({ "/metier/o_save.do" })
	public String save(Metier bean, Integer parentId, HttpServletRequest request, ModelMap model) {
		bean = this.service.save(bean, parentId);
		log.info("save Metier id={}", bean.getId());
		return "redirect:v_list.do";
	}

	@RequiresPermissions({ "admin:metier:update" })
	@RequestMapping({ "/metier/o_update.do" })
	public String update(Metier bean, Integer parentId, Integer pageNo, HttpServletRequest request, ModelMap model) {
		bean = this.service.update(bean, parentId);
		log.info("update Metier id={}.", bean.getId());
		return list(null, pageNo, request, model);
	}

	@RequiresPermissions({ "admin:metier:delete" })
	@RequestMapping({ "/metier/o_delete.do" })
	public String delete(Integer[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
		Metier[] beans = this.service.deleteByIds(ids);
		for (Metier bean : beans) {
			log.info("delete Metier id={}", bean.getId());
		}
		return list(null, pageNo, request, model);
	}
}

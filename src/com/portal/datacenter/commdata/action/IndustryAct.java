package com.portal.datacenter.commdata.action;

import com.portal.datacenter.commdata.entity.Industry;
import com.portal.datacenter.commdata.service.IndustryService;
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
public class IndustryAct {
	private static final Logger log = LoggerFactory.getLogger(IndustryAct.class);

	@Autowired
	private IndustryService service;

	@RequiresPermissions({ "admin:industry:list" })
	@RequestMapping({ "/industry/v_list.do" })
	public String list(String key, Integer pageNo, HttpServletRequest request, ModelMap model) {
		Page page = this.service.getPage(key, pageNo.intValue(), 20);
		model.addAttribute("page", page);
		model.addAttribute("key", key);
		return "industry/list";
	}

	@RequiresPermissions({ "admin:industry:add" })
	@RequestMapping({ "/industry/v_add.do" })
	public String add(ModelMap model) {
		List industryList = this.service.getIndustryList(null);
		model.addAttribute("industryList", industryList);
		return "industry/add";
	}

	@RequiresPermissions({ "admin:industry:edit" })
	@RequestMapping({ "/industry/v_edit.do" })
	public String edit(Integer id, Integer pageNo, HttpServletRequest request, ModelMap model) {
		List industryList = this.service.getIndustryList(id);
		Industry industry = this.service.findById(id);
		if (industry.getParent() != null) {
			model.addAttribute("parentId", industry.getParent().getId());
		}
		model.addAttribute("industryList", industryList);
		model.addAttribute("industry", industry);
		model.addAttribute("pageNo", pageNo);
		return "industry/edit";
	}

	@RequiresPermissions({ "admin:industry:save" })
	@RequestMapping({ "/industry/o_save.do" })
	public String save(Industry bean, Integer parentId, HttpServletRequest request, ModelMap model) {
		bean = this.service.save(bean, parentId);
		log.info("save Industry id={}", bean.getId());
		return "redirect:v_list.do";
	}

	@RequiresPermissions({ "admin:industry:update" })
	@RequestMapping({ "/industry/o_update.do" })
	public String update(Industry bean, Integer parentId, Integer pageNo, HttpServletRequest request, ModelMap model) {
		bean = this.service.update(bean, parentId);
		log.info("update Industry id={}.", bean.getId());
		return list(null, pageNo, request, model);
	}

	@RequiresPermissions({ "admin:industry:delete" })
	@RequestMapping({ "/industry/o_delete.do" })
	public String delete(Integer[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
		Industry[] beans = this.service.deleteByIds(ids);
		for (Industry bean : beans) {
			log.info("delete Industry id={}", bean.getId());
		}
		return list(null, pageNo, request, model);
	}
}

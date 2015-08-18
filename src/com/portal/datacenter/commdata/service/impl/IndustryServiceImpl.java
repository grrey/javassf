package com.portal.datacenter.commdata.service.impl;

import com.portal.datacenter.commdata.dao.IndustryDao;
import com.portal.datacenter.commdata.entity.Industry;
import com.portal.datacenter.commdata.service.IndustryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class IndustryServiceImpl implements IndustryService {
	private IndustryDao dao;

	@Transactional(readOnly = true)
	public Page<Industry> getPage(String key, int pageNo, int pageSize) {
		return this.dao.getPage(key, pageNo, pageSize);
	}

	@Transactional(readOnly = true)
	public List<Industry> getIndustryList(Integer id) {
		return this.dao.getIndustryList(id);
	}

	@Transactional(readOnly = true)
	public List<Industry> getIndustryChild(Integer id) {
		return this.dao.getIndustryChild(id);
	}

	@Transactional(readOnly = true)
	public Industry findByCode(String code) {
		return this.dao.findByCode(code);
	}

	@Transactional(readOnly = true)
	public Industry findById(Integer id) {
		Industry entity = (Industry) this.dao.findById(id);
		return entity;
	}

	public Industry save(Industry bean, Integer parentId) {
		if (parentId != null) {
			bean.setParent(findById(parentId));
		}
		this.dao.save(bean);
		return bean;
	}

	public Industry update(Industry bean, Integer parentId) {
		bean = (Industry) this.dao.update(bean);
		if (parentId != null)
			bean.setParent(findById(parentId));
		else {
			bean.setParent(null);
		}
		return bean;
	}

	public Industry deleteById(Integer id) {
		Industry bean = (Industry) this.dao.deleteById(id);
		return bean;
	}

	public Industry[] deleteByIds(Integer[] ids) {
		Industry[] beans = new Industry[ids.length];
		int i = 0;
		for (int len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	@Autowired
	public void setDao(IndustryDao dao) {
		this.dao = dao;
	}
}

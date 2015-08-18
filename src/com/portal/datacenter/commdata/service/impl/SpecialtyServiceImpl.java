package com.portal.datacenter.commdata.service.impl;

import com.portal.datacenter.commdata.dao.SpecialtyDao;
import com.portal.datacenter.commdata.entity.Specialty;
import com.portal.datacenter.commdata.service.SpecialtyService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SpecialtyServiceImpl implements SpecialtyService {
	private SpecialtyDao dao;

	@Transactional(readOnly = true)
	public Page<Specialty> getPage(String key, int pageNo, int pageSize) {
		return this.dao.getPage(key, pageNo, pageSize);
	}

	@Transactional(readOnly = true)
	public List<Specialty> getSpecialtyList(Integer id) {
		return this.dao.getSpecialtyList(id);
	}

	@Transactional(readOnly = true)
	public List<Specialty> getSpecialtyChild(Integer id) {
		return this.dao.getSpecialtyChild(id);
	}

	@Transactional(readOnly = true)
	public Specialty findByCode(String code) {
		return this.dao.findByCode(code);
	}

	@Transactional(readOnly = true)
	public Specialty findById(Integer id) {
		Specialty entity = (Specialty) this.dao.findById(id);
		return entity;
	}

	public Specialty save(Specialty bean, Integer parentId) {
		if (parentId != null) {
			bean.setParent(findById(parentId));
		}
		this.dao.save(bean);
		return bean;
	}

	public Specialty update(Specialty bean, Integer parentId) {
		bean = (Specialty) this.dao.update(bean);
		if (parentId != null)
			bean.setParent(findById(parentId));
		else {
			bean.setParent(null);
		}
		return bean;
	}

	public Specialty deleteById(Integer id) {
		Specialty bean = (Specialty) this.dao.deleteById(id);
		return bean;
	}

	public Specialty[] deleteByIds(Integer[] ids) {
		Specialty[] beans = new Specialty[ids.length];
		int i = 0;
		for (int len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	@Autowired
	public void setDao(SpecialtyDao dao) {
		this.dao = dao;
	}
}

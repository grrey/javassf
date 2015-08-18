package com.portal.datacenter.commdata.service.impl;

import com.portal.datacenter.commdata.dao.UnitTypeDao;
import com.portal.datacenter.commdata.entity.UnitType;
import com.portal.datacenter.commdata.service.UnitTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UnitTypeServiceImpl implements UnitTypeService {
	private UnitTypeDao dao;

	@Transactional(readOnly = true)
	public Page<UnitType> getPage(int pageNo, int pageSize) {
		return this.dao.getPage(pageNo, pageSize);
	}

	@Transactional(readOnly = true)
	public UnitType findById(Integer id) {
		UnitType entity = (UnitType) this.dao.findById(id);
		return entity;
	}

	public UnitType save(UnitType bean) {
		this.dao.save(bean);
		return bean;
	}

	public UnitType update(UnitType bean) {
		bean = (UnitType) this.dao.update(bean);
		return bean;
	}

	public UnitType deleteById(Integer id) {
		UnitType bean = (UnitType) this.dao.deleteById(id);
		return bean;
	}

	public UnitType[] deleteByIds(Integer[] ids) {
		UnitType[] beans = new UnitType[ids.length];
		int i = 0;
		for (int len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	@Autowired
	public void setDao(UnitTypeDao dao) {
		this.dao = dao;
	}
}

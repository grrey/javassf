package com.portal.datacenter.commdata.service.impl;

import com.portal.datacenter.commdata.dao.EconomyTypeDao;
import com.portal.datacenter.commdata.entity.EconomyType;
import com.portal.datacenter.commdata.service.EconomyTypeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EconomyTypeServiceImpl implements EconomyTypeService {
	private EconomyTypeDao dao;

	@Transactional(readOnly = true)
	public Page<EconomyType> getPage(int pageNo, int pageSize) {
		return this.dao.getPage(pageNo, pageSize);
	}

	@Transactional(readOnly = true)
	public List<EconomyType> getEconomyTypeList() {
		return this.dao.getEconomyTypeList();
	}

	@Transactional(readOnly = true)
	public EconomyType findByCode(String code) {
		return this.dao.findByCode(code);
	}

	@Transactional(readOnly = true)
	public EconomyType findById(Integer id) {
		EconomyType entity = (EconomyType) this.dao.findById(id);
		return entity;
	}

	public EconomyType save(EconomyType bean) {
		this.dao.save(bean);
		return bean;
	}

	public EconomyType update(EconomyType bean) {
		bean = (EconomyType) this.dao.update(bean);
		return bean;
	}

	public EconomyType deleteById(Integer id) {
		EconomyType bean = (EconomyType) this.dao.deleteById(id);
		return bean;
	}

	public EconomyType[] deleteByIds(Integer[] ids) {
		EconomyType[] beans = new EconomyType[ids.length];
		int i = 0;
		for (int len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	@Autowired
	public void setDao(EconomyTypeDao dao) {
		this.dao = dao;
	}
}

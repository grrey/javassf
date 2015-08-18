package com.portal.datacenter.commdata.service.impl;

import com.portal.datacenter.commdata.dao.ForeignLangDao;
import com.portal.datacenter.commdata.entity.ForeignLang;
import com.portal.datacenter.commdata.service.ForeignLangService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ForeignLangServiceImpl implements ForeignLangService {
	private ForeignLangDao dao;

	@Transactional(readOnly = true)
	public Page<ForeignLang> getPage(int pageNo, int pageSize) {
		return this.dao.getPage(pageNo, pageSize);
	}

	@Transactional(readOnly = true)
	public List<ForeignLang> getForeignLangList() {
		return this.dao.getForeignLangList();
	}

	@Transactional(readOnly = true)
	public ForeignLang findById(Integer id) {
		ForeignLang entity = (ForeignLang) this.dao.findById(id);
		return entity;
	}

	public ForeignLang save(ForeignLang bean) {
		this.dao.save(bean);
		return bean;
	}

	public ForeignLang update(ForeignLang bean) {
		bean = (ForeignLang) this.dao.update(bean);
		return bean;
	}

	public ForeignLang deleteById(Integer id) {
		ForeignLang bean = (ForeignLang) this.dao.deleteById(id);
		return bean;
	}

	public ForeignLang[] deleteByIds(Integer[] ids) {
		ForeignLang[] beans = new ForeignLang[ids.length];
		int i = 0;
		for (int len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	@Autowired
	public void setDao(ForeignLangDao dao) {
		this.dao = dao;
	}
}

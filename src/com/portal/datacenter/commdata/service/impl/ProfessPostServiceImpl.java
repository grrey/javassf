package com.portal.datacenter.commdata.service.impl;

import com.portal.datacenter.commdata.dao.ProfessPostDao;
import com.portal.datacenter.commdata.entity.ProfessPost;
import com.portal.datacenter.commdata.service.ProfessPostService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProfessPostServiceImpl implements ProfessPostService {
	private ProfessPostDao dao;

	@Transactional(readOnly = true)
	public Page<ProfessPost> getPage(int pageNo, int pageSize) {
		return this.dao.getPage(pageNo, pageSize);
	}

	@Transactional(readOnly = true)
	public List<ProfessPost> getProfessPostList(Integer id) {
		return this.dao.getProfessPostList(id);
	}

	@Transactional(readOnly = true)
	public ProfessPost findById(Integer id) {
		ProfessPost entity = (ProfessPost) this.dao.findById(id);
		return entity;
	}

	public ProfessPost save(ProfessPost bean) {
		this.dao.save(bean);
		return bean;
	}

	public ProfessPost update(ProfessPost bean) {
		bean = (ProfessPost) this.dao.update(bean);
		return bean;
	}

	public ProfessPost deleteById(Integer id) {
		ProfessPost bean = (ProfessPost) this.dao.deleteById(id);
		return bean;
	}

	public ProfessPost[] deleteByIds(Integer[] ids) {
		ProfessPost[] beans = new ProfessPost[ids.length];
		int i = 0;
		for (int len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	@Autowired
	public void setDao(ProfessPostDao dao) {
		this.dao = dao;
	}
}

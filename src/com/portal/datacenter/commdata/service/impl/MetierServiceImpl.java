package com.portal.datacenter.commdata.service.impl;

import com.portal.datacenter.commdata.dao.MetierDao;
import com.portal.datacenter.commdata.entity.Metier;
import com.portal.datacenter.commdata.service.MetierService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MetierServiceImpl implements MetierService {
	private MetierDao dao;

	@Transactional(readOnly = true)
	public Page<Metier> getPage(String key, int pageNo, int pageSize) {
		return this.dao.getPage(key, pageNo, pageSize);
	}

	@Transactional(readOnly = true)
	public List<Metier> getMetierList(Integer id) {
		return this.dao.getMetierList(id);
	}

	@Transactional(readOnly = true)
	public List<Metier> getMetierChild(Integer id) {
		return this.dao.getMetierChild(id);
	}

	public Metier findByCode(String code) {
		return this.dao.findByCode(code);
	}

	@Transactional(readOnly = true)
	public Metier findById(Integer id) {
		Metier entity = (Metier) this.dao.findById(id);
		return entity;
	}

	public Metier save(Metier bean, Integer parentId) {
		if (parentId != null) {
			bean.setParent(findById(parentId));
		}
		this.dao.save(bean);
		return bean;
	}

	public Metier update(Metier bean, Integer parentId) {
		bean = (Metier) this.dao.update(bean);
		if (parentId != null)
			bean.setParent(findById(parentId));
		else {
			bean.setParent(null);
		}
		return bean;
	}

	public Metier deleteById(Integer id) {
		Metier bean = (Metier) this.dao.deleteById(id);
		return bean;
	}

	public Metier[] deleteByIds(Integer[] ids) {
		Metier[] beans = new Metier[ids.length];
		int i = 0;
		for (int len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	@Autowired
	public void setDao(MetierDao dao) {
		this.dao = dao;
	}
}

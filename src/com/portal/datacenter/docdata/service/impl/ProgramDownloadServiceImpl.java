package com.portal.datacenter.docdata.service.impl;

import com.portal.datacenter.docdata.dao.ProgramDownloadDao;
import com.portal.datacenter.docdata.entity.ProgramDownload;
import com.portal.datacenter.docdata.service.ProgramDownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProgramDownloadServiceImpl implements ProgramDownloadService {
	private ProgramDownloadDao dao;

	@Transactional(readOnly = true)
	public Page<ProgramDownload> getPage(int pageNo, int pageSize) {
		return this.dao.getPage(pageNo, pageSize);
	}

	@Transactional(readOnly = true)
	public ProgramDownload findUnique() {
		return this.dao.findUnique();
	}

	@Transactional(readOnly = true)
	public ProgramDownload findById(Integer id) {
		ProgramDownload entity = (ProgramDownload) this.dao.findById(id);
		return entity;
	}

	public ProgramDownload save() {
		ProgramDownload bean = new ProgramDownload();
		bean.setCount(Integer.valueOf(1));
		this.dao.save(bean);
		return bean;
	}

	public ProgramDownload updateCount() {
		ProgramDownload pd = findUnique();
		if (pd != null)
			pd.setCount(Integer.valueOf(pd.getCount().intValue() + 1));
		else {
			pd = save();
		}
		return pd;
	}

	public ProgramDownload update(ProgramDownload bean) {
		bean = (ProgramDownload) this.dao.update(bean);
		return bean;
	}

	public ProgramDownload deleteById(Integer id) {
		ProgramDownload bean = (ProgramDownload) this.dao.deleteById(id);
		return bean;
	}

	public ProgramDownload[] deleteByIds(Integer[] ids) {
		ProgramDownload[] beans = new ProgramDownload[ids.length];
		int i = 0;
		for (int len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	@Autowired
	public void setDao(ProgramDownloadDao dao) {
		this.dao = dao;
	}
}

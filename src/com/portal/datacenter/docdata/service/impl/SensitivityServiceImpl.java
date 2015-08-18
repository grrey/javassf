package com.portal.datacenter.docdata.service.impl;

import com.portal.datacenter.docdata.dao.SensitivityDao;
import com.portal.datacenter.docdata.entity.Sensitivity;
import com.portal.datacenter.docdata.service.SensitivityService;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SensitivityServiceImpl
  implements SensitivityService
{
  private SensitivityDao dao;

  @Transactional(readOnly=true)
  public String replaceSensitivity(String s)
  {
    if (StringUtils.isBlank(s)) {
      return s;
    }
    List<Sensitivity> list = getList(true, null, null);
    for (Sensitivity sen : list) {
      s = StringUtils.replace(s, sen.getSearch(), sen.getReplacement());
    }
    return s;
  }

  @Transactional(readOnly=true)
  public List<Sensitivity> getList(boolean cacheable, String sortname, String sortorder) {
    return this.dao.getList(cacheable, sortname, sortorder);
  }
  @Transactional(readOnly=true)
  public Sensitivity findById(Integer id) {
    Sensitivity entity = (Sensitivity)this.dao.findById(id);
    return entity;
  }

  public Sensitivity save(Sensitivity bean) {
    this.dao.save(bean);
    return bean;
  }

  public Sensitivity update(Sensitivity bean) {
    bean = (Sensitivity)this.dao.update(bean);
    return bean;
  }

  public Sensitivity deleteById(Integer id) {
    Sensitivity bean = (Sensitivity)this.dao.deleteById(id);
    return bean;
  }

  public Sensitivity[] deleteByIds(Integer[] ids) {
    Sensitivity[] beans = new Sensitivity[ids.length];
    int i = 0; for (int len = ids.length; i < len; i++) {
      beans[i] = deleteById(ids[i]);
    }
    return beans;
  }

  @Autowired
  public void setDao(SensitivityDao dao)
  {
    this.dao = dao;
  }
}
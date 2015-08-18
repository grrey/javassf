 package com.portal.usermgr.service.impl;
 
 import com.portal.doccenter.entity.Channel;
 import com.portal.doccenter.service.ChannelService;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.event.DelDepartEvent;
 import com.portal.usermgr.dao.DepartDao;
 import com.portal.usermgr.entity.Depart;
 import com.portal.usermgr.service.DepartService;
 import java.util.List;
 import java.util.Set;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.context.ApplicationContext;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class DepartServiceImpl
   implements DepartService
 {
   private ChannelService channelService;
   private DepartDao dao;
   private ApplicationContext applicationContext;
 
   @Transactional(readOnly=true)
   public Page<Depart> getPage(String key, Integer siteId, String sortname, String sortorder, int pageNo, int pageSize)
   {
     return this.dao.getPage(key, siteId, sortname, sortorder, pageNo, pageSize);
   }
 
   @Transactional(readOnly=true)
   public List<Depart> getListByTag(Integer siteId, Integer parentId, Integer orderBy) {
     return this.dao.getListByTag(siteId, parentId, orderBy);
   }
   @Transactional(readOnly=true)
   public List<Depart> getAllList(Integer siteId) {
     return this.dao.getAllList(siteId);
   }
   @Transactional(readOnly=true)
   public List<Depart> getListNoParent(Integer siteId) {
     return this.dao.getListNoParent(siteId);
   }
   @Transactional(readOnly=true)
   public List<Depart> getListByParent(Integer parentId) {
     return this.dao.getListByParent(parentId);
   }
   @Transactional(readOnly=true)
   public Depart getDepartByPath(String path, Integer siteId) {
     return this.dao.getDepartByPath(path, siteId);
   }
   @Transactional(readOnly=true)
   public Depart getDepartByName(String name, Integer siteId) {
     return this.dao.getDepartByName(name, siteId);
   }
   @Transactional(readOnly=true)
   public Depart findById(Integer id) {
     Depart entity = (Depart)this.dao.findById(id);
     return entity;
   }
 
   public Depart save(Depart bean, Site site, Integer parentId, Integer[] channelIds)
   {
     if (parentId != null) {
       Depart parent = findById(parentId);
       bean.setParent(parent);
       parent.addToChilds(bean);
     }
     bean.setSite(site);
     if ((!bean.getAllChannel().booleanValue()) && 
       (channelIds != null)) {
       for (Integer cid : channelIds) {
         bean.addToChannels(this.channelService.findById(cid));
       }
     }
 
     bean.init();
     this.dao.save(bean);
     bean.initTreeNumber();
     return bean;
   }
 
   public Depart update(Depart bean, Integer parentId, Integer[] channelIds) {
     bean = (Depart)this.dao.update(bean);
     if (parentId != null) {
       Depart parent = findById(parentId);
       parent.addToChilds(bean);
       if ((bean.getParent() != null) && (bean.getParent().getChild() != null)) {
         bean.getParent().getChild().remove(bean);
       }
       bean.setParent(parent);
     } else {
       bean.setParent(null);
     }
     Set<Channel> channels = bean.getChannels();
     for (Channel channel : channels) {
       channel.getDeparts().remove(bean);
     }
     bean.getChannels().clear();
     if ((!bean.getAllChannel().booleanValue()) && 
       (channelIds != null)) {
       for (Integer cid : channelIds) {
         bean.addToChannels(this.channelService.findById(cid));
       }
     }
 
     bean.initTreeNumber();
     return bean;
   }
 
   public Depart updatePrio(Integer id, Integer priority) {
     Depart d = findById(id);
     d.setPriority(priority);
     return d;
   }
 
   public void deleteBySiteId(Integer siteId) {
     List<Depart> list = getAllList(siteId);
     for (Depart d : list)
       deleteById(d.getId());
   }
 
   public Depart deleteById(Integer id)
   {
     Depart bean = (Depart)this.dao.deleteById(id);
     this.applicationContext.publishEvent(new DelDepartEvent(bean));
     Set<Channel> channels = bean.getChannels();
     for (Channel channel : channels) {
       channel.getDeparts().remove(bean);
     }
     bean.getChannels().clear();
     moveChild(bean.getChild());
     Depart parent = bean.getParent();
     if (parent != null) {
       parent.getChild().remove(bean);
     }
     return bean;
   }
 
   public Depart[] deleteByIds(Integer[] ids) {
     Depart[] beans = new Depart[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   private void moveChild(Set<Depart> childs)
   {
     for (Depart d : childs)
       d.setParent(null);
   }
 
   @Autowired
   public void setDao(DepartDao dao)
   {
     this.dao = dao;
   }
   @Autowired
   public void setChannelService(ChannelService channelService) {
     this.channelService = channelService;
   }
   @Autowired
   public void setApplicationContext(ApplicationContext applicationContext) {
     this.applicationContext = applicationContext;
   }
 }


 
 
 
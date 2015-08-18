 package com.portal.sysmgr.service.impl;
 
 import com.portal.doccenter.entity.Channel;
 import com.portal.doccenter.entity.ChannelExt;
 import com.portal.doccenter.entity.ChannelTxt;
 import com.portal.doccenter.entity.ChnlTplSelection;
 import com.portal.doccenter.service.ChannelService;
 import com.portal.sysmgr.dao.SiteDao;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.service.ResourceService;
 import com.portal.sysmgr.service.SiteService;
 import com.portal.usermgr.entity.Admin;
 import com.portal.usermgr.entity.Depart;
 import com.portal.usermgr.entity.Role;
 import com.portal.usermgr.entity.RolePerm;
 import com.portal.usermgr.entity.User;
 import com.portal.usermgr.service.AdminCheckService;
 import com.portal.usermgr.service.DepartService;
 import com.portal.usermgr.service.RoleService;
 import java.io.IOException;
 import java.io.PrintStream;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import java.util.Set;
 import org.apache.commons.lang.StringUtils;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.beans.BeanUtils;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class SiteServiceImpl
   implements SiteService
 {
   private static final Logger log = LoggerFactory.getLogger(SiteServiceImpl.class);
 
   private Map<Integer, Channel> haveSaveIds = new HashMap<Integer, Channel>();
   private AdminCheckService adminCheckService;
   private ResourceService resourceService;
   private RoleService roleService;
   private DepartService departService;
   private ChannelService channelService;
   private SiteDao dao;
 
   @Transactional(readOnly=true)
   public List<Site> getList()
   {
     return this.dao.getList(false);
   }
   @Transactional(readOnly=true)
   public List<Site> getListFromCache() {
     return this.dao.getList(true);
   }
   @Transactional(readOnly=true)
   public Site findByDomain(String domain, boolean cacheable) {
     return this.dao.findByDomain(domain, cacheable);
   }
   @Transactional(readOnly=true)
   public Site findById(Integer id) {
     Site entity = (Site)this.dao.findById(id);
     return entity;
   }
 
   public Site save(Site bean, User user, Integer siteId, Integer[] channelIds) {
     bean.init();
     Admin admin = user.getAdmin();
     bean = (Site)this.dao.save(bean);
     try
     {
       this.resourceService.copyTplAndRes(findById(siteId), bean);
     } catch (IOException e) {
       System.out.println("模板拷贝失败");
     }
 
     Role r = copyRole(bean, user, siteId);
 
     Depart d = copyDepart(bean, user, siteId);
     admin.addToRoles(r);
     admin.addToDeparts(d);
 
     copyChannel(bean, channelIds);
     this.adminCheckService.save(bean, admin, Byte.valueOf((byte) 4), Boolean.valueOf(true), null);
     return bean;
   }
 
   public Site update(Site bean) {
     Site entity = findById(bean.getId());
     bean = (Site)this.dao.update(bean);
     if (bean.getPort() == null) {
       entity.setPort(null);
     }
     if (StringUtils.isBlank(bean.getContextPath())) {
       entity.setContextPath(null);
     }
     return entity;
   }
 
   public void updateTplStyle(Integer siteId, String style) {
     Site site = findById(siteId);
     site.setTplStyle(style);
   }
 
   public Site deleteById(Integer id) {
     this.adminCheckService.deleteBySiteId(id);
     Site bean = (Site)this.dao.deleteById(id);
     try {
       this.resourceService.delTplAndRes(bean);
     } catch (IOException e) {
       log.error("删除模板失败!", e);
     }
     return bean;
   }
 
   public Site[] deleteByIds(Integer[] ids) {
     Site[] beans = new Site[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   private Role copyRole(Site bean, User user, Integer siteId)
   {
     Role r = new Role();
     RolePerm rp = new RolePerm();
     BeanUtils.copyProperties(user.getRole(siteId), r);
     BeanUtils.copyProperties(user.getRole(siteId).getRolePerm(), rp);
     r.setId(null);
     rp.setRole(null);
     r = this.roleService.saveRole(r, rp, bean);
     return r;
   }
 
   private Depart copyDepart(Site bean, User user, Integer siteId)
   {
     Depart d = new Depart();
     BeanUtils.copyProperties(user.getDepart(siteId), d);
     d.setId(null);
     d.setChannels(null);
     d.setParent(null);
     d.setChild(null);
     d = this.departService.save(d, bean, null, null);
     return d;
   }
 
   private void copyChannel(Site bean, Integer[] channelIds)
   {
     this.haveSaveIds.clear();
     if (channelIds != null)
       for (Integer channelId : channelIds) {
         Channel channel = this.channelService.findById(channelId);
         if ((channel.getChild() != null) && 
           (channel.getChild().size() != 0)) continue;
         saveChannel(channel, bean);
       }
   }
 
   private Channel saveChannel(Channel channel, Site bean)
   {
     if (channel.getParent() != null)
     {
       Channel p;
       //Channel p;
       if (!this.haveSaveIds.containsKey(channel.getParent().getId()))
       {
         p = saveChannel(channel.getParent(), bean);
       }
       else p = (Channel)this.haveSaveIds.get(channel.getParent().getId());
 
       return saveChannelFinal(channel, p, bean);
     }
     return saveChannelFinal(channel, null, bean);
   }
 
   private Channel saveChannelFinal(Channel channel, Channel parent, Site bean)
   {
     Channel c = new Channel();
     ChannelExt ext = new ChannelExt();
     ChannelTxt txt = new ChannelTxt();
     BeanUtils.copyProperties(channel, c);
     BeanUtils.copyProperties(channel.getExt(), ext);
     if (channel.getTxt() != null) {
       BeanUtils.copyProperties(channel.getTxt(), txt);
       txt.setId(null);
     }
     c.setId(null);
 
     c.setChild(null);
     c.setViewGroups(null);
     c.setContriGroups(null);
     c.setDeparts(null);
     c.setTpls(null);
     ext.setId(null);
     if (channel.getTpls() != null) {
       for (ChnlTplSelection tpl : channel.getTpls()) {
         ChnlTplSelection tpls = new ChnlTplSelection();
         tpls.setModel(tpl.getModel());
         tpls.setTplDoc(tpl.getTplDoc());
         c.addToTpls(tpls);
       }
     }
     Channel cl = this.channelService.save(c, ext, txt, bean, null, null, null, 
       null, null, null);
     if (parent != null) {
       cl.setParent(parent);
       parent.addToChilds(cl);
     }
 
     this.haveSaveIds.put(channel.getId(), cl);
     return cl;
   }
 
   @Autowired
   public void setAdminCheckService(AdminCheckService adminCheckService)
   {
     this.adminCheckService = adminCheckService;
   }
   @Autowired
   public void setResourceService(ResourceService resourceService) {
     this.resourceService = resourceService;
   }
   @Autowired
   public void setRoleService(RoleService roleService) {
     this.roleService = roleService;
   }
   @Autowired
   public void setDepartService(DepartService departService) {
     this.departService = departService;
   }
   @Autowired
   public void setChannelService(ChannelService channelService) {
     this.channelService = channelService;
   }
   @Autowired
   public void setDao(SiteDao dao) {
     this.dao = dao;
   }
 }

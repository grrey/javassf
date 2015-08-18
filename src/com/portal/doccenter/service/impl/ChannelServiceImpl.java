 package com.portal.doccenter.service.impl;
 
 import com.portal.doccenter.dao.ChannelDao;
 import com.portal.doccenter.entity.Channel;
 import com.portal.doccenter.entity.ChannelExt;
 import com.portal.doccenter.entity.ChannelTxt;
 import com.portal.doccenter.entity.ChnlTplSelection;
 import com.portal.doccenter.entity.Model;
 import com.portal.doccenter.service.ChannelExtService;
 import com.portal.doccenter.service.ChannelService;
 import com.portal.doccenter.service.ChannelTxtService;
 import com.portal.doccenter.service.ModelService;
 import com.portal.doccenter.service.WorkFlowService;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.event.AddOrUpdateChannelEvent;
 import com.portal.sysmgr.event.DelChannelEvent;
 import com.portal.usermgr.entity.Admin;
 import com.portal.usermgr.entity.Depart;
 import com.portal.usermgr.entity.Group;
 import com.portal.usermgr.entity.Member;
 import com.portal.usermgr.entity.User;
 import com.portal.usermgr.service.GroupService;
 import com.portal.usermgr.service.UserService;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Map;
 import java.util.Map.Entry;
 import java.util.Set;
 import org.apache.commons.lang.ArrayUtils;
 import org.apache.commons.lang.StringUtils;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.context.ApplicationContext;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class ChannelServiceImpl
   implements ChannelService
 {
   private ChannelExtService channelExtService;
   private ChannelTxtService channelTxtService;
   private WorkFlowService workFlowService;
   private UserService userService;
   private GroupService groupService;
   private ModelService modelService;
   private ChannelDao dao;
   private ApplicationContext applicationContext;
 
   @Transactional(readOnly=true)
   public List<Channel> getChannelListByTag(Integer siteId, Integer parentId, Boolean alone, boolean show, int count)
   {
     return this.dao.getChannelListByTag(siteId, parentId, alone, show, count);
   }
 
   @Transactional(readOnly=true)
   public Page<Channel> getChannelPageByTag(Integer siteId, Integer parentId, Boolean alone, boolean show, int pageNo, int pageSize) {
     return this.dao.getChannelPageByTag(siteId, parentId, alone, show, pageNo, 
       pageSize);
   }
 
   @Transactional(readOnly=true)
   public List<Channel> getChannelBySite(Integer siteId, Integer parentId, String key, String sortname, String sortorder, Boolean alone) {
     return this.dao.getChannelBySite(siteId, parentId, key, sortname, sortorder, 
       alone);
   }
 
   @Transactional(readOnly=true)
   public List<Channel> getChannelByAdmin(Integer userId, Integer siteId, Integer parentId, String key, String sortname, String sortorder, Boolean alone)
   {
     User user = this.userService.findById(userId);
     Depart depart = user.getAdmin().getDepart(siteId);
     if (user.getTakeDepart(siteId).booleanValue()) {
       Integer departId = null;
       if ((!user.getAllChannel(siteId)) && (depart != null)) {
         departId = depart.getId();
       }
       return this.dao.getChannelByAdmin(siteId, departId, parentId, key, 
         sortname, sortorder, alone);
     }
     return this.dao.getChannelByAdminAndTake(siteId, 
       user.getAdmin().getId(), parentId, key, sortname, 
       sortorder, alone);
   }
 
   @Transactional(readOnly=true)
   public List<Channel> getChannelByModel(Integer parentId, Integer modelId, User user, Integer siteId)
   {
     Depart depart = user.getDepart(siteId);
     if (user.getAllChannel(siteId)) {
       return this.dao.getChannelByModel(parentId, modelId, null, siteId);
     }
     if (user.getTakeDepart(siteId).booleanValue()) {
       return this.dao.getChannelByModel(parentId, modelId, depart.getId(), 
         siteId);
     }
     return this.dao.getChannelByModelAndTake(parentId, modelId, user
       .getAdmin().getId(), siteId);
   }
 
   public List<Channel> getChannelByModelAndMember(Integer parentId, Integer modelId, User user, Integer siteId)
   {
     if (user.getAdmin() != null) {
       return this.dao.getChannelByModelAndMember(parentId, modelId, null, 
         siteId);
     }
     if (user.getMember() == null) {
       return new ArrayList();
     }
     if (user.getMember().getGroup(siteId) == null) {
       return new ArrayList();
     }
     return this.dao.getChannelByModelAndMember(parentId, modelId, user
       .getMember().getGroupId(siteId), siteId);
   }
 
   @Transactional(readOnly=true)
   public Channel findByNumber(String number) {
     return this.dao.findByNumber(number);
   }
   @Transactional(readOnly=true)
   public Channel findByName(String name) {
     return this.dao.findByName(name);
   }
   @Transactional(readOnly=true)
   public Channel findById(Integer id) {
     Channel entity = (Channel)this.dao.findById(id);
     return entity;
   }
   @Transactional(readOnly=true)
   public Channel findByPath(String path, Integer siteId) {
     return this.dao.findByPath(path, siteId, false);
   }
   @Transactional(readOnly=true)
   public Channel findByPathForTag(String path, Integer siteId) {
     return this.dao.findByPath(path, siteId, true);
   }
   @Transactional(readOnly=true)
   public int getAllChannelCount(Integer siteId) {
     return this.dao.getAllChannelCount(siteId);
   }
 
   public Channel save(Channel bean, ChannelExt ext, ChannelTxt txt, Site site, User user, Integer flowId, Integer[] viewGroupIds, Integer[] contriGroupIds, Integer parentId, Map<String, String> tpls)
   {
     if (parentId != null) {
       Channel parent = findById(parentId);
       bean.setParent(parent);
       parent.addToChilds(bean);
     }
     if (tpls != null) {
       addTpl(tpls, bean);
     }
     bean.setSite(site);
     if (flowId != null) {
       bean.setFlow(this.workFlowService.findById(flowId));
     }
     bean.setInputDepart(user.getDepart(site.getId()));
     bean.init();
     this.dao.save(bean);
     this.channelExtService.save(ext, bean);
     this.channelTxtService.save(txt, bean);
     if (!ArrayUtils.isEmpty(viewGroupIds)) {
       for (Integer gid : viewGroupIds) {
         bean.addToViewGroups(this.groupService.findById(gid));
       }
     }
     if (!ArrayUtils.isEmpty(contriGroupIds)) {
       for (Integer gid : contriGroupIds) {
         bean.addToContriGroups(this.groupService.findById(gid));
       }
     }
     if ((user != null) && (!user.getAllChannel(site.getId()))) {
       user.getDepart(site.getId()).addToChannels(bean);
     }
     bean.initTreeNumber();
     this.applicationContext.publishEvent(new AddOrUpdateChannelEvent(bean));
     return bean;
   }
 
   public Channel update(Channel bean, ChannelExt ext, ChannelTxt txt, Integer flowId, Integer[] viewGroupIds, Integer[] contriGroupIds, Integer parentId, Map<String, String> tpls)
   {
     bean = (Channel)this.dao.update(bean);
     Channel c = bean.getParent();
     if (c != null) {
       c.getChild().remove(bean);
     }
     if (parentId != null) {
       Channel parent = findById(parentId);
       parent.addToChilds(bean);
       if (bean.getParent() != null) {
         bean.getParent().getChild().remove(bean);
       }
       bean.setParent(parent);
     } else {
       bean.setParent(null);
     }
     if (flowId != null)
       bean.setFlow(this.workFlowService.findById(flowId));
     else {
       bean.setFlow(null);
     }
     this.channelExtService.update(ext);
     this.channelTxtService.update(txt, bean);
     for (Group g : bean.getViewGroups()) {
       g.getViewChannels().remove(bean);
     }
     bean.getViewGroups().clear();
     if (!ArrayUtils.isEmpty(viewGroupIds)) {
       for (Integer gid : viewGroupIds) {
         bean.addToViewGroups(this.groupService.findById(gid));
       }
     }
     for (Group g : bean.getContriGroups()) {
       g.getContriChannels().remove(bean);
     }
     bean.getContriGroups().clear();
     if (!ArrayUtils.isEmpty(contriGroupIds)) {
       for (Integer gid : contriGroupIds) {
         bean.addToContriGroups(this.groupService.findById(gid));
       }
     }
     bean.getTpls().clear();
     if (tpls != null) {
       addTpl(tpls, bean);
     }
     bean.initTreeNumber();
     this.applicationContext.publishEvent(new AddOrUpdateChannelEvent(bean));
     return bean;
   }
 
   public Channel updatePrio(Integer id, Integer priority) {
     Channel c = findById(id);
     c.setPriority(priority);
     return c;
   }
 
   public int updateChannelByInputDepart(Integer departId) {
     return this.dao.updateChannelByInputDepart(departId);
   }
 
   public int delChannelByInputDepart(Integer departId) {
     return this.dao.delChannelByInputDepart(departId);
   }
 
   public int updateChannelByWorkFlow(Integer flowId) {
     return this.dao.updateChannelByWorkFlow(flowId);
   }
 
   public Channel delChannel(Integer id, Boolean del, Integer cid) {
     Channel c = (Channel)this.dao.findById(id);
     if (del != null) {
       if (del.booleanValue()) {
         if ((c.getChild() != null) && (c.getChild().size() > 0)) {
           delChild(c.getChild());
         }
       }
       else if ((c.getChild() != null) && (c.getChild().size() > 0)) {
         moveChild(c.getChild(), cid);
       }
     }
 
     deleteById(c.getId());
     return null;
   }
 
   private void delChild(Set<Channel> childs) {
     for (Channel c : childs) {
       if ((c.getChild() != null) && (c.getChild().size() > 0)) {
         delChild(c.getChild());
       }
       deleteById(c.getId());
     }
   }
 
   private void moveChild(Set<Channel> childs, Integer cid) {
     Channel parent = findById(cid);
     for (Channel c : childs) {
       parent.addToChilds(c);
       c.getParent().getChild().remove(c);
       c.setParent(parent);
       c.setNumber(parent.getNumber() + c.getId() + "-");
     }
     this.applicationContext.publishEvent(new AddOrUpdateChannelEvent(parent));
   }
 
   public Channel deleteById(Integer id) {
     Channel entity = (Channel)this.dao.findById(id);
     for (Group group : entity.getViewGroups()) {
       group.getViewChannels().remove(entity);
     }
     for (Group group : entity.getContriGroups()) {
       group.getContriChannels().remove(entity);
     }
     for (Depart depart : entity.getDeparts()) {
       depart.getChannels().remove(entity);
     }
     if (entity.getParent() != null) {
       entity.getParent().getChild().remove(entity);
     }
     entity = (Channel)this.dao.deleteById(id);
     this.applicationContext.publishEvent(new DelChannelEvent(entity));
     return entity;
   }
 
   public Channel[] deleteByIds(Integer[] ids) {
     Channel[] beans = new Channel[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   public Channel[] updatePriority(Integer[] ids, Integer[] priority) {
     int len = ids.length;
     Channel[] beans = new Channel[len];
     for (int i = 0; i < len; i++) {
       beans[i] = findById(ids[i]);
       beans[i].setPriority(priority[i]);
     }
     return beans;
   }
 
   private void addTpl(Map<String, String> tpls, Channel bean) {
     if (tpls != null)
       for (Map.Entry entry : tpls.entrySet()) {
         ChnlTplSelection cs = new ChnlTplSelection();
         Model m = this.modelService
           .findById(Integer.valueOf(Integer.parseInt((String)entry.getKey())));
         if (!StringUtils.isBlank((String)entry.getValue()))
           cs.setTplDoc((String)entry.getValue());
         else {
           cs.setTplDoc(m.getTplDoc());
         }
         cs.setModel(m);
         bean.addToTpls(cs);
       }
   }
 
   @Autowired
   public void setChannelExtService(ChannelExtService channelExtService)
   {
     this.channelExtService = channelExtService;
   }
   @Autowired
   public void setChannelTxtService(ChannelTxtService channelTxtService) {
     this.channelTxtService = channelTxtService;
   }
   @Autowired
   public void setWorkFlowService(WorkFlowService workFlowService) {
     this.workFlowService = workFlowService;
   }
   @Autowired
   public void setModelService(ModelService modelService) {
     this.modelService = modelService;
   }
   @Autowired
   public void setUserService(UserService userService) {
     this.userService = userService;
   }
   @Autowired
   public void setGroupService(GroupService groupService) {
     this.groupService = groupService;
   }
   @Autowired
   public void setDao(ChannelDao dao) {
     this.dao = dao;
   }
   @Autowired
   public void setApplicationContext(ApplicationContext applicationContext) {
     this.applicationContext = applicationContext;
   }
 }


 
 
 
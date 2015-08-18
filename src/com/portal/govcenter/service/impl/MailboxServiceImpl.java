 package com.portal.govcenter.service.impl;
 
 import com.portal.govcenter.dao.MailboxDao;
 import com.portal.govcenter.entity.Mailbox;
 import com.portal.govcenter.entity.MailboxExt;
 import com.portal.govcenter.service.MailboxExtService;
 import com.portal.govcenter.service.MailboxService;
 import com.portal.govcenter.service.MailboxTypeService;
 import com.portal.sysmgr.entity.Site;
 import com.portal.usermgr.entity.Admin;
 import com.portal.usermgr.entity.Depart;
 import com.portal.usermgr.entity.User;
 import com.portal.usermgr.service.DepartService;
 import java.sql.Timestamp;
 import org.apache.commons.lang.StringUtils;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class MailboxServiceImpl
   implements MailboxService
 {
   private MailboxDao dao;
   private MailboxTypeService typeService;
   private MailboxExtService extService;
   private DepartService departService;
 
   @Transactional(readOnly=true)
   public Page<Mailbox> getPage(String name, Integer siteId, User user, Integer typeId, int pageNo, int pageSize)
   {
     Integer departId = null;
     if (!user.getAdmin().haveAllManage(siteId)) {
       Depart depart = user.getAdmin().getDepart(siteId);
       if (depart != null) {
         departId = depart.getId();
       }
     }
     return this.dao.getPage(name, siteId, departId, typeId, pageNo, pageSize);
   }
 
   public Page<Mailbox> getPageByTag(String name, Integer siteId, Integer departId, Integer typeId, int pageNo, int pageSize)
   {
     return this.dao.getPageByTag(name, siteId, departId, typeId, pageNo, 
       pageSize);
   }
   @Transactional(readOnly=true)
   public Mailbox findById(Integer id) {
     Mailbox entity = (Mailbox)this.dao.findById(id);
     return entity;
   }
 
   public Mailbox save(Mailbox bean, MailboxExt ext, Site site, Integer departId, Integer typeId)
   {
     bean.setType(this.typeService.findById(typeId));
     if (departId != null) {
       bean.setDepart(this.departService.findById(departId));
     }
     bean.setSite(site);
     bean.init();
     this.dao.save(bean);
     this.extService.save(bean, ext);
     return bean;
   }
 
   public Mailbox update(Mailbox bean, MailboxExt ext, User user, Integer siteId)
   {
     bean = (Mailbox)this.dao.update(bean);
     if (!StringUtils.isBlank(ext.getReply())) {
       bean.setReplyTime(new Timestamp(System.currentTimeMillis()));
       bean.setDepart(user.getAdmin().getDepart(siteId));
       bean.setStatus(Mailbox.DEALED);
     }
     this.extService.update(bean, ext);
     return bean;
   }
 
   public Mailbox showMailbox(Integer id) {
     Mailbox bean = (Mailbox)this.dao.findById(id);
     if (bean.getShow().booleanValue())
       bean.setShow(Boolean.valueOf(false));
     else {
       bean.setShow(Boolean.valueOf(true));
     }
     return bean;
   }
 
   public Mailbox forwardMailbox(Integer id, Integer departId) {
     Mailbox bean = (Mailbox)this.dao.findById(id);
     bean.setDepart(this.departService.findById(departId));
     bean.setStatus(Mailbox.FORWARD);
     return bean;
   }
 
   public Mailbox backMailbox(Integer id) {
     Mailbox bean = (Mailbox)this.dao.findById(id);
     bean.setDepart(null);
     bean.setStatus(Mailbox.BACK);
     return bean;
   }
 
   public Mailbox deleteById(Integer id) {
     Mailbox bean = (Mailbox)this.dao.deleteById(id);
     return bean;
   }
 
   public Mailbox[] deleteByIds(Integer[] ids) {
     Mailbox[] beans = new Mailbox[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   @Autowired
   public void setDao(MailboxDao dao)
   {
     this.dao = dao;
   }
   @Autowired
   public void setTypeService(MailboxTypeService typeService) {
     this.typeService = typeService;
   }
   @Autowired
   public void setExtService(MailboxExtService extService) {
     this.extService = extService;
   }
   @Autowired
   public void setDepartService(DepartService departService) {
     this.departService = departService;
   }
 }


 
 
 
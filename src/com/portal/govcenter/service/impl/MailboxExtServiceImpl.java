 package com.portal.govcenter.service.impl;
 
 import com.portal.govcenter.dao.MailboxExtDao;
 import com.portal.govcenter.entity.Mailbox;
 import com.portal.govcenter.entity.MailboxExt;
 import com.portal.govcenter.service.MailboxExtService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class MailboxExtServiceImpl
   implements MailboxExtService
 {
   private MailboxExtDao dao;
 
   @Transactional(readOnly=true)
   public Page<MailboxExt> getPage(int pageNo, int pageSize)
   {
     return this.dao.getPage(pageNo, pageSize);
   }
   @Transactional(readOnly=true)
   public MailboxExt findById(Integer id) {
     MailboxExt entity = (MailboxExt)this.dao.findById(id);
     return entity;
   }
 
   public MailboxExt save(Mailbox mailbox, MailboxExt bean) {
     bean.setMailbox(mailbox);
     this.dao.save(bean);
     mailbox.setExt(bean);
     return bean;
   }
 
   public MailboxExt update(Mailbox mailbox, MailboxExt bean) {
     MailboxExt ext = findById(mailbox.getId());
     if (ext == null) {
       ext = save(mailbox, bean);
       return ext;
     }
     bean = (MailboxExt)this.dao.update(bean);
     return bean;
   }
 
   public MailboxExt deleteById(Integer id)
   {
     MailboxExt bean = (MailboxExt)this.dao.deleteById(id);
     return bean;
   }
 
   public MailboxExt[] deleteByIds(Integer[] ids) {
     MailboxExt[] beans = new MailboxExt[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   @Autowired
   public void setDao(MailboxExtDao dao)
   {
     this.dao = dao;
   }
 }


 
 
 
 package com.portal.extrafunc.service.impl;
 
 import com.portal.extrafunc.dao.MessageBoardDao;
 import com.portal.extrafunc.entity.MessageBoard;
 import com.portal.extrafunc.entity.MessageBoardExt;
 import com.portal.extrafunc.service.MessageBoardExtService;
 import com.portal.extrafunc.service.MessageBoardService;
 import com.portal.extrafunc.service.MessageTypeService;
 import com.portal.sysmgr.entity.Site;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class MessageBoardServiceImpl
   implements MessageBoardService
 {
   private MessageBoardDao dao;
   private MessageTypeService typeService;
   private MessageBoardExtService extService;
 
   @Transactional(readOnly=true)
   public Page<MessageBoard> getPage(String name, Integer siteId, Boolean show, String sortname, String sortorder, int pageNo, int pageSize)
   {
     return this.dao.getPage(name, siteId, show, sortname, sortorder, pageNo, 
       pageSize);
   }
   @Transactional(readOnly=true)
   public Integer getAllMessageCount(Integer siteId) {
     return this.dao.getAllMessageCount(siteId);
   }
   @Transactional(readOnly=true)
   public Integer getNoRepMessageCount(Integer siteId) {
     return this.dao.getNoRepMessageCount(siteId);
   }
   @Transactional(readOnly=true)
   public MessageBoard findById(Integer id) {
     MessageBoard entity = (MessageBoard)this.dao.findById(id);
     return entity;
   }
 
   public MessageBoard save(String title, String name, String mobile, String email, String address, String zipcode, Integer typeId, String content, Site site)
   {
     MessageBoard board = new MessageBoard();
     MessageBoardExt ext = new MessageBoardExt();
     board.setTitle(title);
     board.setName(name);
     board.setMobile(mobile);
     board.setEmail(email);
     board.setAddress(address);
     board.setZipcode(zipcode);
     ext.setContent(content);
     save(board, ext, site, typeId);
     return board;
   }
 
   public MessageBoard save(MessageBoard bean, MessageBoardExt ext, Site site, Integer typeId)
   {
     if (typeId != null)
       bean.setType(this.typeService.findById(typeId));
     else {
       bean.setType(this.typeService.getUniqueType(site.getId()));
     }
     bean.setSite(site);
     bean.init();
     this.dao.save(bean);
     this.extService.save(bean, ext);
     return bean;
   }
 
   public MessageBoard update(MessageBoard bean, MessageBoardExt ext, Integer typeId)
   {
     bean = (MessageBoard)this.dao.update(bean);
     if (typeId != null) {
       bean.setType(this.typeService.findById(typeId));
     }
     this.extService.update(ext);
     return bean;
   }
 
   public MessageBoard showBoard(Integer id) {
     MessageBoard board = findById(id);
     if (board.getShow().booleanValue())
       board.setShow(Boolean.valueOf(false));
     else {
       board.setShow(Boolean.valueOf(true));
     }
     return board;
   }
 
   public MessageBoard deleteById(Integer id) {
     MessageBoard bean = (MessageBoard)this.dao.deleteById(id);
     return bean;
   }
 
   public MessageBoard[] deleteByIds(Integer[] ids) {
     MessageBoard[] beans = new MessageBoard[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   @Autowired
   public void setDao(MessageBoardDao dao)
   {
     this.dao = dao;
   }
   @Autowired
   public void setTypeService(MessageTypeService typeService) {
     this.typeService = typeService;
   }
   @Autowired
   public void setExtService(MessageBoardExtService extService) {
     this.extService = extService;
   }
 }


 
 
 
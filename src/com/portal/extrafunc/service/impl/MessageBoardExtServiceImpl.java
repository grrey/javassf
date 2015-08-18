 package com.portal.extrafunc.service.impl;
 
 import com.portal.extrafunc.dao.MessageBoardExtDao;
 import com.portal.extrafunc.entity.MessageBoard;
 import com.portal.extrafunc.entity.MessageBoardExt;
 import com.portal.extrafunc.service.MessageBoardExtService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class MessageBoardExtServiceImpl
   implements MessageBoardExtService
 {
   private MessageBoardExtDao dao;
 
   @Transactional(readOnly=true)
   public Page<MessageBoardExt> getPage(int pageNo, int pageSize)
   {
     return this.dao.getPage(pageNo, pageSize);
   }
   @Transactional(readOnly=true)
   public MessageBoardExt findById(Integer id) {
     MessageBoardExt entity = (MessageBoardExt)this.dao.findById(id);
     return entity;
   }
 
   public MessageBoardExt save(MessageBoard MessageBoard, MessageBoardExt bean) {
     bean.setBoard(MessageBoard);
     this.dao.save(bean);
     MessageBoard.setExt(bean);
     return bean;
   }
 
   public MessageBoardExt update(MessageBoardExt bean) {
     bean = (MessageBoardExt)this.dao.update(bean);
     return bean;
   }
 
   public MessageBoardExt deleteById(Integer id) {
     MessageBoardExt bean = (MessageBoardExt)this.dao.deleteById(id);
     return bean;
   }
 
   public MessageBoardExt[] deleteByIds(Integer[] ids) {
     MessageBoardExt[] beans = new MessageBoardExt[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   @Autowired
   public void setDao(MessageBoardExtDao dao)
   {
     this.dao = dao;
   }
 }


 
 
 
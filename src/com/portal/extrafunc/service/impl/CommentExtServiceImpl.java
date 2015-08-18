 package com.portal.extrafunc.service.impl;
 
 import com.portal.extrafunc.dao.CommentExtDao;
 import com.portal.extrafunc.entity.Comment;
 import com.portal.extrafunc.entity.CommentExt;
 import com.portal.extrafunc.service.CommentExtService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class CommentExtServiceImpl
   implements CommentExtService
 {
   private CommentExtDao dao;
 
   public CommentExt save(String ip, String content, Comment comment)
   {
     CommentExt ext = new CommentExt();
     ext.setContent(content);
     ext.setIp(ip);
     ext.setComment(comment);
     comment.setCommentExt(ext);
     this.dao.save(ext);
     return ext;
   }
 
   public CommentExt update(CommentExt bean) {
     bean = (CommentExt)this.dao.update(bean);
     return bean;
   }
 
   public int deleteByParentId(Integer parentId) {
     return this.dao.deleteByParentId(parentId);
   }
 
   public int deleteByDocId(Integer docId) {
     return this.dao.deleteByDocId(docId);
   }
 
   public int deleteByTreeNumber(String treeNumber) {
     return this.dao.deleteByTreeNumber(treeNumber);
   }
 
   public int deleteByDocUserId(Integer userId) {
     return this.dao.deleteByDocUserId(userId);
   }
 
   public int deleteByUserId(Integer userId) {
     return this.dao.deleteByUserId(userId);
   }
 
   public int deleteByUserIdAndParent(Integer userId) {
     return this.dao.deleteByUserIdAndParent(userId);
   }
 
   public int deleteBySiteId(Integer siteId) {
     return this.dao.deleteBySiteId(siteId);
   }
 
   @Autowired
   public void setDao(CommentExtDao dao)
   {
     this.dao = dao;
   }
 }


 
 
 
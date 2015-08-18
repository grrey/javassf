 package com.portal.extrafunc.service.impl;
 
 import com.portal.datacenter.docdata.service.SensitivityService;
 import com.portal.doccenter.entity.Article;
 import com.portal.extrafunc.dao.CommentDao;
 import com.portal.extrafunc.entity.Comment;
 import com.portal.extrafunc.entity.CommentExt;
 import com.portal.extrafunc.service.CommentExtService;
 import com.portal.extrafunc.service.CommentService;
 import com.portal.sysmgr.entity.Site;
 import com.portal.usermgr.entity.User;
 import java.sql.Timestamp;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class CommentServiceImpl
   implements CommentService
 {
   private SensitivityService sensitivityService;
   private CommentExtService commentExtService;
   private CommentDao dao;
 
   @Transactional(readOnly=true)
   public Page<Comment> getPage(Integer siteId, Integer docId, Integer parentId, Boolean checked, Boolean noparent, int orderBy, String sortname, String sortorder, int pageNo, int pageSize)
   {
     return this.dao.getPage(siteId, docId, parentId, checked, noparent, orderBy, 
       sortname, sortorder, false, pageNo, pageSize);
   }
 
   @Transactional(readOnly=true)
   public Page<Comment> getPageForTag(Integer siteId, Integer docId, Integer parentId, Boolean checked, Boolean noparent, int orderBy, int pageNo, int pageSize)
   {
     return this.dao.getPage(siteId, docId, parentId, checked, noparent, orderBy, 
       null, null, true, pageNo, pageSize);
   }
   @Transactional(readOnly=true)
   public Integer getAllCommentCount(Integer siteId) {
     return this.dao.getAllCommentCount(siteId);
   }
   @Transactional(readOnly=true)
   public Comment findById(Integer id) {
     Comment entity = (Comment)this.dao.findById(id);
     return entity;
   }
 
   public Comment comment(String content, String ip, Integer parentId, Article doc, User user, Site site)
   {
     Comment comment = new Comment();
     comment.setDoc(doc);
     comment.setSite(site);
     comment.setUser(user);
     if (parentId != null) {
       Comment parent = findById(parentId);
       parent.setLastTime(new Timestamp(System.currentTimeMillis()));
       comment.setParent(parent);
     }
     comment.init();
     this.dao.save(comment);
     content = this.sensitivityService.replaceSensitivity(content);
     this.commentExtService.save(ip, content, comment);
     return comment;
   }
 
   public Comment update(Comment bean, CommentExt ext) {
     update(bean);
     this.commentExtService.update(ext);
     return bean;
   }
 
   public Comment update(Comment bean) {
     bean = (Comment)this.dao.update(bean);
     return bean;
   }
 
   public int deleteByDocId(Integer docId) {
     this.commentExtService.deleteByDocId(docId);
     return this.dao.deleteByDocId(docId);
   }
 
   public int deleteByTreeNumber(String treeNumber) {
     this.commentExtService.deleteByTreeNumber(treeNumber);
     return this.dao.deleteByTreeNumber(treeNumber);
   }
 
   public int deleteByUserId(Integer userId) {
     this.commentExtService.deleteByDocUserId(userId);
     this.dao.deleteByDocUserId(userId);
     this.commentExtService.deleteByUserIdAndParent(userId);
     this.dao.deleteByUserIdAndParent(userId);
     this.commentExtService.deleteByUserId(userId);
     return this.dao.deleteByUserId(userId);
   }
 
   public int deleteBySiteId(Integer siteId) {
     this.commentExtService.deleteBySiteId(siteId);
     return this.dao.deleteBySiteId(siteId);
   }
 
   public Comment checkById(Integer id) {
     Comment bean = findById(id);
     if (bean.getChecked().booleanValue())
       bean.setChecked(Boolean.valueOf(false));
     else {
       bean.setChecked(Boolean.valueOf(true));
     }
     return bean;
   }
 
   public Comment[] checkByIds(Integer[] ids) {
     Comment[] beans = new Comment[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = checkById(ids[i]);
     }
     return beans;
   }
 
   public Comment deleteById(Integer id) {
     this.commentExtService.deleteByParentId(id);
     this.dao.deleteByParentId(id);
     Comment bean = (Comment)this.dao.deleteById(id);
     return bean;
   }
 
   public Comment[] deleteByIds(Integer[] ids) {
     Comment[] beans = new Comment[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   public void ups(Integer id) {
     Comment comment = findById(id);
     comment.setUps(Integer.valueOf(comment.getUps().intValue() + 1));
   }
 
   public void ups(Integer id, Integer ups) {
     Comment comment = findById(id);
     comment.setUps(ups);
   }
 
   @Autowired
   public void setSensitivityService(SensitivityService sensitivityService)
   {
     this.sensitivityService = sensitivityService;
   }
   @Autowired
   public void setCommentExtService(CommentExtService commentExtService) {
     this.commentExtService = commentExtService;
   }
   @Autowired
   public void setDao(CommentDao dao) {
     this.dao = dao;
   }
 }


 
 
 
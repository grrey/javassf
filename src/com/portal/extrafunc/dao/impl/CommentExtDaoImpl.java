 package com.portal.extrafunc.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.extrafunc.dao.CommentExtDao;
 import com.portal.extrafunc.entity.CommentExt;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class CommentExtDaoImpl extends QueryDaoImpl<CommentExt, Integer>
   implements CommentExtDao
 {
   public int deleteByParentId(Integer parentId)
   {
     String hql = "delete from CommentExt bean where bean.id in (select c.id from Comment c where c.parent.id=?)";
 
     return executeQuery(hql, new Object[] { parentId });
   }
 
   public int deleteByDocId(Integer docId) {
     String hql = "delete from CommentExt bean where bean.id in (select c.id from Comment c where c.doc.id=?)";
 
     return executeQuery(hql, new Object[] { docId });
   }
 
   public int deleteByTreeNumber(String treeNumber) {
     String hql = "delete from CommentExt bean where bean.id in (select c.id from Comment c inner join c.doc a where a.channel.number like ?)";
 
     return executeQuery(hql, new Object[] { treeNumber + "%" });
   }
 
   public int deleteByDocUserId(Integer userId) {
     String hql = "delete from CommentExt bean where bean.id in (select c.id from Comment c inner join c.doc a where a.user.id=?)";
 
     return executeQuery(hql, new Object[] { userId });
   }
 
   public int deleteByUserId(Integer userId) {
     String hql = "delete from CommentExt bean where bean.id in (select c.id from Comment c inner join c.user u where u.id=?)";
 
     return executeQuery(hql, new Object[] { userId });
   }
 
   public int deleteByUserIdAndParent(Integer userId) {
     String hql = "delete from CommentExt bean where bean.id in (select c.id from Comment c inner join c.parent p where p.id in (select c.id from Comment c inner join c.user u where u.id=?))";
 
     return executeQuery(hql, new Object[] { userId });
   }
 
   public int deleteBySiteId(Integer siteId) {
     String hql = "delete from CommentExt bean where bean.id in (select c.id from Comment c inner join c.site s where s.id=?)";
 
     return executeQuery(hql, new Object[] { siteId });
   }
 
   protected Class<CommentExt> getEntityClass()
   {
     return CommentExt.class;
   }
 }


 
 
 
 package com.portal.extrafunc.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.extrafunc.dao.CommentDao;
 import com.portal.extrafunc.entity.Comment;
 import org.apache.commons.lang.StringUtils;
 import org.hibernate.Criteria;
 import org.hibernate.criterion.Order;
 import org.hibernate.criterion.Projections;
 import org.hibernate.criterion.Restrictions;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class CommentDaoImpl extends QueryDaoImpl<Comment, Integer>
   implements CommentDao
 {
   public Page<Comment> getPage(Integer siteId, Integer docId, Integer parentId, Boolean checked, Boolean noparent, int orderBy, String sortname, String sortorder, boolean cacheable, int pageNo, int pageSize)
   {
     Criteria crit = createCriteria();
     if ((noparent != null) && (noparent.booleanValue())) {
       if (docId != null)
         crit.add(Restrictions.eq("doc.id", docId));
       else if (siteId != null) {
         crit.add(Restrictions.eq("site.id", siteId));
       }
       crit.add(Restrictions.isNull("parent"));
     }
     else if (parentId != null) {
       crit.add(Restrictions.eq("parent.id", parentId));
     } else if (docId != null) {
       crit.add(Restrictions.eq("doc.id", docId));
     } else if (siteId != null) {
       crit.add(Restrictions.eq("site.id", siteId));
     }
 
     if (checked != null) {
       crit.add(Restrictions.eq("checked", checked));
     }
     if (orderBy == 1)
       crit.addOrder(Order.desc("ups"));
     else if (orderBy == 2)
       crit.addOrder(Order.desc("lastTime"));
     else if (orderBy == 3) {
       crit.addOrder(Order.desc("createTime"));
     }
     else if (!StringUtils.isBlank(sortname)) {
       if ("asc".equals(sortorder))
         crit.addOrder(Order.asc(sortname));
       else
         crit.addOrder(Order.desc(sortname));
     }
     else {
       crit.addOrder(Order.desc("createTime"));
     }
 
     crit.setCacheable(cacheable);
     return findByCriteria(crit, pageNo, pageSize);
   }
 
   public Integer getAllCommentCount(Integer siteId) {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("site.id", siteId));
     crit.setProjection(Projections.count("id"));
     return Integer.valueOf(((Number)crit.uniqueResult()).intValue());
   }
 
   public Integer getFloorByComment(Integer commentId) {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("parent.id", commentId));
     crit.setProjection(Projections.max("floor"));
     if (crit.uniqueResult() == null) {
       return Integer.valueOf(2);
     }
     return Integer.valueOf(((Number)crit.uniqueResult()).intValue());
   }
 
   public int deleteByParentId(Integer parentId) {
     String hql = "delete from Comment bean where bean.parent.id=?";
     return executeQuery(hql, new Object[] { parentId });
   }
 
   public int deleteByDocId(Integer docId) {
     String hql = "delete from Comment bean where bean.doc.id=?";
     return executeQuery(hql, new Object[] { docId });
   }
 
   public int deleteByTreeNumber(String treeNumber) {
     String hql = "delete from Comment bean where bean.doc.id in (select a.id from Article a where a.channel.number like ?)";
 
     return executeQuery(hql, new Object[] { treeNumber + "%" });
   }
 
   public int deleteByDocUserId(Integer userId) {
     String hql = "delete from Comment bean where bean.doc.id in (select a.id from Article a where a.user.id=?)";
 
     return executeQuery(hql, new Object[] { userId });
   }
 
   public int deleteByUserId(Integer userId) {
     String hql = "delete from Comment bean where bean.user.id=?";
     return executeQuery(hql, new Object[] { userId });
   }
 
   public int deleteByUserIdAndParent(Integer userId) {
     String hql = "delete from Comment bean where bean.parent.id in (select c.id from Comment c where c.user.id=?)";
 
     return executeQuery(hql, new Object[] { userId });
   }
 
   public int deleteBySiteId(Integer siteId) {
     String hql = "delete from Comment bean where bean.site.id=?";
     return executeQuery(hql, new Object[] { siteId });
   }
 
   protected Class<Comment> getEntityClass()
   {
     return Comment.class;
   }
 }


 
 
 
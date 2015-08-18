 package com.portal.extrafunc.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.extrafunc.dao.PostsDao;
 import com.portal.extrafunc.entity.Posts;
 import org.hibernate.CacheMode;
 import org.hibernate.Criteria;
 import org.hibernate.ScrollMode;
 import org.hibernate.ScrollableResults;
 import org.hibernate.Session;
 import org.hibernate.criterion.Order;
 import org.hibernate.criterion.Projections;
 import org.hibernate.criterion.Restrictions;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class PostsDaoImpl extends QueryDaoImpl<Posts, Integer>
   implements PostsDao
 {
   public Page<Posts> getPage(int pageNo, int pageSize)
   {
     Criteria crit = createCriteria();
     return findByCriteria(crit, pageNo, pageSize);
   }
 
   public Page<Posts> getPostsPageForTag(Integer themeId, int pageNo, int pageSize)
   {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("theme.id", themeId));
     crit.addOrder(Order.asc("floor"));
     return findByCriteria(crit, pageNo, pageSize);
   }
 
   public Posts getPostsOneFloor(Integer themeId) {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("theme.id", themeId));
     crit.add(Restrictions.eq("floor", Integer.valueOf(1)));
     return (Posts)findUnique(crit);
   }
 
   public Posts getLastPostsByUserAndForum(Integer forumId, Integer userId) {
     Criteria crit = createCriteria();
     crit.createAlias("theme", "t");
     crit.add(Restrictions.eq("t.forum.id", forumId));
     crit.add(Restrictions.ne("creater.id", userId));
     crit.addOrder(Order.desc("createTime"));
     return (Posts)findUnique(crit);
   }
 
   public Posts getLastPostsByUserAndTheme(Integer themeId, Integer userId) {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("theme.id", themeId));
     crit.add(Restrictions.ne("creater.id", userId));
     crit.addOrder(Order.desc("createTime"));
     return (Posts)findUnique(crit);
   }
 
   public Integer getAllPostCount(Integer siteId) {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("site.id", siteId));
     crit.setProjection(Projections.count("id"));
     return Integer.valueOf(((Number)crit.uniqueResult()).intValue());
   }
 
   public Integer getFloorByTheme(Integer themeId) {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("theme.id", themeId));
     crit.setProjection(Projections.max("floor"));
     return Integer.valueOf(((Number)crit.uniqueResult()).intValue() + 1);
   }
 
   public int updatePostsBySite(Integer siteId) {
     String hql = "update Posts bean set bean.quote=null where bean.site.id=?";
     return executeQuery(hql, new Object[] { siteId });
   }
 
   public int updatePostsByCategory(Integer categoryId) {
     String hql = "update Posts bean set bean.quote=null where bean.theme.id in (select t.id from Theme t inner join t.forum f where f.id in (select f.id from Forum c inner join f.category c where c.id=?))";
 
     return executeQuery(hql, new Object[] { categoryId });
   }
 
   public int updatePostsByForum(Integer forumId) {
     String hql = "update Posts bean set bean.quote=null where bean.theme.id in (select t.id from Theme t inner join t.forum f where f.id=?)";
 
     return executeQuery(hql, new Object[] { forumId });
   }
 
   public int updatePostsByTheme(Integer themeId) {
     String hql = "update Posts bean set bean.quote=null where bean.theme.id=?";
     return executeQuery(hql, new Object[] { themeId });
   }
 
   public int updatePostsByUser(Integer userId) {
     String hql = "update Posts bean set bean.quote=null where bean.quote.id in (select p.id from Posts p inner join p.creater c where c.id=?)";
 
     return executeQuery(hql, new Object[] { userId });
   }
 
   public int deletePosts(Integer siteId, Integer categoryId, Integer forumId, Integer themeId, Integer userId)
   {
     Criteria crit = createCriteria();
     if (siteId != null) {
       crit.createAlias("site", "s");
       crit.add(Restrictions.eq("s.id", siteId));
     }
     if (categoryId != null) {
       crit.createAlias("theme", "t");
       crit.createAlias("t.forum", "f");
       crit.createAlias("f.category", "c");
       crit.add(Restrictions.eq("c.id", categoryId));
     }
     if (forumId != null) {
       crit.createAlias("theme", "t");
       crit.createAlias("t.forum", "f");
       crit.add(Restrictions.eq("f.id", forumId));
     }
     if (themeId != null) {
       crit.add(Restrictions.eq("theme.id", themeId));
     }
     Session session = getSession();
     ScrollableResults posts = crit.setCacheMode(CacheMode.IGNORE).scroll(
       ScrollMode.FORWARD_ONLY);
 
     int count = 0;
     while (posts.next()) {
       Posts p = (Posts)posts.get(0);
       session.delete(p);
       count++; if (count % 20 == 0) {
         session.clear();
       }
     }
     return count;
   }
 
   protected Class<Posts> getEntityClass()
   {
     return Posts.class;
   }
 }


 
 
 
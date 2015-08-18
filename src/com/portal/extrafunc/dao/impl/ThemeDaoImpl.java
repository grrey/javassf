 package com.portal.extrafunc.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.extrafunc.dao.ThemeDao;
 import com.portal.extrafunc.entity.Theme;
 import java.util.List;
 import org.hibernate.CacheMode;
 import org.hibernate.Criteria;
 import org.hibernate.ScrollMode;
 import org.hibernate.ScrollableResults;
 import org.hibernate.Session;
 import org.hibernate.criterion.Order;
 import org.hibernate.criterion.Restrictions;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class ThemeDaoImpl extends QueryDaoImpl<Theme, Integer>
   implements ThemeDao
 {
   public Page<Theme> getPage(int pageNo, int pageSize)
   {
     Criteria crit = createCriteria();
     return findByCriteria(crit, pageNo, pageSize);
   }
 
   public Page<Theme> getThemePageForTag(Integer forumId, Integer status, Integer createrId, Integer replyId, int orderBy, int pageNo, int pageSize)
   {
     Criteria crit = createCriteria();
     if (forumId != null) {
       crit.add(Restrictions.eq("forum.id", forumId));
     }
     if (status != null) {
       crit.add(Restrictions.ge("status", status));
     }
     if (createrId != null) {
       crit.add(Restrictions.eq("creater.id", createrId));
     }
     if (replyId != null) {
       crit.createAlias("txt", "t");
       crit.add(Restrictions.like("t.content", "%," + replyId + ",%"));
     }
     appendOrder(crit, orderBy);
     return findByCriteria(crit, pageNo, pageSize);
   }
 
   public List<Theme> getThemeByTop() {
     Criteria crit = createCriteria();
     crit.add(Restrictions.gt("status", Integer.valueOf(0)));
     crit.addOrder(Order.desc("status"));
     crit.addOrder(Order.desc("lastReplyTime"));
     return findByCriteria(crit);
   }
 
   public List<Theme> getThemeByLight() {
     Criteria crit = createCriteria();
     crit.add(Restrictions.isNotNull("color"));
     crit.addOrder(Order.desc("status"));
     crit.addOrder(Order.desc("lastReplyTime"));
     return findByCriteria(crit);
   }
 
   public List<Theme> getThemeByLock() {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("lock", Boolean.valueOf(true)));
     crit.addOrder(Order.desc("status"));
     crit.addOrder(Order.desc("lastReplyTime"));
     return findByCriteria(crit);
   }
 
   public List<Theme> getThemeAll() {
     Criteria crit = createCriteria();
     crit.addOrder(Order.desc("status"));
     crit.addOrder(Order.desc("lastReplyTime"));
     return findByCriteria(crit);
   }
 
   public int deleteByForumId(Integer forumId) {
     String hql = "delete from Theme bean where bean.forum.id=?";
     return executeQuery(hql, new Object[] { forumId });
   }
 
   public int deleteByUserId(Integer userId) {
     String hql = "delete from Theme bean where bean.creater.id=?";
     return executeQuery(hql, new Object[] { userId });
   }
 
   public int deleteBySiteId(Integer siteId) {
     String hql = "delete from Theme bean where bean.site.id=?";
     return executeQuery(hql, new Object[] { siteId });
   }
 
   public int deleteByCategoryId(Integer categoryId) {
     Criteria crit = createCriteria();
     crit.createAlias("forum", "f");
     crit.add(Restrictions.eq("f.category.id", categoryId));
     Session session = getSession();
     ScrollableResults themes = crit.setCacheMode(CacheMode.IGNORE).scroll(
       ScrollMode.FORWARD_ONLY);
 
     int count = 0;
     while (themes.next()) {
       Theme theme = (Theme)themes.get(0);
       session.delete(theme);
       count++; if (count % 20 == 0) {
         session.clear();
       }
     }
     return count;
   }
 
   private void appendOrder(Criteria crit, int orderBy) {
     switch (orderBy) {
     case 1:
       crit.addOrder(Order.desc("status"));
       crit.addOrder(Order.desc("replyCount"));
       break;
     case 2:
       crit.addOrder(Order.desc("status"));
       crit.addOrder(Order.desc("viewsCount"));
       break;
     case 3:
       crit.addOrder(Order.desc("replyCount"));
       break;
     case 4:
       crit.addOrder(Order.desc("viewsCount"));
       break;
     default:
       crit.addOrder(Order.desc("status"));
       crit.addOrder(Order.desc("lastReplyTime"));
     }
   }
 
   protected Class<Theme> getEntityClass()
   {
     return Theme.class;
   }
 }


 
 
 
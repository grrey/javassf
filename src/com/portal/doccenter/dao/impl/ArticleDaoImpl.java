 package com.portal.doccenter.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.doccenter.dao.ArticleDao;
 import com.portal.doccenter.entity.Article;
 import com.portal.doccenter.entity.Channel;
 import java.util.Calendar;
 import java.util.Date;
 import java.util.List;
 import org.apache.commons.lang.StringUtils;
 import org.hibernate.CacheMode;
 import org.hibernate.Criteria;
 import org.hibernate.ScrollMode;
 import org.hibernate.ScrollableResults;
 import org.hibernate.Session;
 import org.hibernate.criterion.Disjunction;
 import org.hibernate.criterion.Order;
 import org.hibernate.criterion.ProjectionList;
 import org.hibernate.criterion.Projections;
 import org.hibernate.criterion.Restrictions;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class ArticleDaoImpl extends QueryDaoImpl<Article, Integer>
   implements ArticleDao
 {
   public Page<Article> getPageArticle(String title, Integer[] typeIds, Integer[] modelIds, 
		   Integer inputDepartId, boolean top, boolean recommend, Byte[] statuss,
		   Integer siteId, Integer userId, Integer departId, Integer roleId,
		   String number, Byte manageStatus, Boolean allChannel, Boolean takeDepart, int orderBy, String sortname, String sortorder, int pageNo, int pageSize)
   {
     Criteria crit = byPageArticle(title, typeIds, modelIds, inputDepartId, 
       top, recommend, statuss, siteId, userId, departId, roleId, 
       number, manageStatus, allChannel, takeDepart);
     
     if (!StringUtils.isBlank(sortname)) {
       if ("asc".equals(sortorder))
         crit.addOrder(Order.asc(sortname));
       else
         crit.addOrder(Order.desc(sortname));
     }
     else {
       crit.addOrder(Order.desc("releaseDate"));
     }
     return findByCriteria(crit, pageNo, pageSize);
   }
 
   public Page<Article> getPageDocByMember(String title, Integer[] typeIds, Integer[] modelIds, boolean top, boolean recommend, Integer siteId, Integer userId, String number, int pageNo, int pageSize)
   {
     Criteria crit = createCriteria();
     crit.createAlias("channel", "c");
     if (!StringUtils.isBlank(number))
       crit.add(Restrictions.like("c.number", number + "%"));
     else if (siteId != null) {
       crit.add(Restrictions.eq("site.id", siteId));
     }
     crit.add(Restrictions.eq("user.id", userId));
     if (!StringUtils.isBlank(title)) {
       crit.add(Restrictions.like("title", "%" + title + "%"));
     }
     if (typeIds != null) {
       appendTypeIds(crit, typeIds);
     }
     if (modelIds != null) {
       appendModelIds(crit, modelIds);
     }
     if (top) {
       crit.add(Restrictions.eq("top", Boolean.valueOf(true)));
     }
     if (recommend) {
       crit.add(Restrictions.eq("recommend", Boolean.valueOf(true)));
     }
     crit.add(Restrictions.ne("status", Byte.valueOf((byte) 3)));
     crit.addOrder(Order.desc("releaseDate"));
     return findByCriteria(crit, pageNo, pageSize);
   }
 
   public long getCountDoc(String number) {
     Criteria crit = createCriteria();
     crit.createAlias("channel", "c");
     crit.add(Restrictions.like("c.number", number + "%"));
     crit.setProjection(Projections.count("id"));
     return ((Long)crit.uniqueResult()).longValue();
   }
 
   public long getCountByChecking(Integer siteId, Integer userId, Integer departId, Integer roleId, Byte manageStatus, Boolean allChannel, boolean checking)
   {
     Byte[] status = (Byte[])null;
     if (checking) {
       status[0] = Byte.valueOf((byte) 1);
     }
     Criteria crit = byPageArticle(null, null, null, null, false, false, 
       status, siteId, userId, departId, roleId, null, manageStatus, 
       allChannel, Boolean.valueOf(false));
     crit.setProjection(Projections.count("id"));
     return ((Long)crit.uniqueResult()).longValue();
   }
 
   public long getCountByDepartSign(Integer siteId, Integer departId) {
     Criteria crit = createCriteria();
     crit.createAlias("admins", "a");
     crit.createAlias("a.departs", "d");
     crit.add(Restrictions.eq("d.id", departId));
     crit.setProjection(Projections.count("id"));
     return ((Long)crit.uniqueResult()).longValue();
   }
 
   public Article getSide(Integer id, Integer siteId, Integer channelId, boolean next)
   {
     Criteria crit = createCriteria();
     if (channelId != null)
       crit.add(Restrictions.eq("channel.id", channelId));
     else if (siteId != null) {
       crit.add(Restrictions.eq("site.id", siteId));
     }
     crit.add(Restrictions.eq("status", Byte.valueOf((byte) 2)));
     if (next) {
       crit.add(Restrictions.gt("id", id));
       crit.addOrder(Order.asc("id"));
     } else {
       crit.add(Restrictions.lt("id", id));
       crit.addOrder(Order.desc("id"));
     }
     return (Article)findUnique(crit);
   }
 
   public List<Article> getListTagByIds(Integer[] ids, int orderBy) {
     Criteria crit = createCriteria();
     crit.add(Restrictions.in("id", ids));
     appendOrder(crit, orderBy);
     return findByCriteria(crit);
   }
 
   public Page<Article> getPageTagByChannelIds(Integer[] channelIds, Integer siteId, Integer[] modelIds, Integer[] typeIds, Integer inputDepartId, Integer userId, String chnlNumber, Boolean recommend, Date date, int orderBy, int callLevel, int pageNo, int pageSize)
   {
     Criteria crit = byChannelIds(channelIds, siteId, modelIds, typeIds, 
       inputDepartId, userId, chnlNumber, recommend, date, orderBy, 
       callLevel);
     return findByCriteria(crit, pageNo, pageSize);
   }
 
   public List<Article> getListTagByChannelIds(Integer[] channelIds, Integer siteId, Integer[] modelIds, Integer[] typeIds, Integer inputDepartId, Integer userId, String chnlNumber, Boolean recommend, Date date, int orderBy, int callLevel, Integer first, Integer count)
   {
     Criteria crit = byChannelIds(channelIds, siteId, modelIds, typeIds, 
       inputDepartId, userId, chnlNumber, recommend, date, orderBy, 
       callLevel);
     if (first != null) {
       crit.setFirstResult(first.intValue());
     }
     if (count != null) {
       crit.setMaxResults(count.intValue());
     }
     return findByCriteria(crit);
   }
 
   public Page<Article> getPageTagByModelIds(Integer[] modelIds, Integer[] typeIds, Integer siteId, Boolean recommend, int orderBy, int pageNo, int pageSize)
   {
     Criteria crit = byModelIds(modelIds, typeIds, siteId, recommend, 
       orderBy);
     return findByCriteria(crit, pageNo, pageSize);
   }
 
   public List<Article> getListTagByModelIds(Integer[] modelIds, Integer[] typeIds, Integer siteId, Boolean recommend, int orderBy, Integer first, Integer count)
   {
     Criteria crit = byModelIds(modelIds, typeIds, siteId, recommend, 
       orderBy);
     if (first != null) {
       crit.setFirstResult(first.intValue());
     }
     if (count != null) {
       crit.setMaxResults(count.intValue());
     }
     return findByCriteria(crit);
   }
 
   public List<Article> getListByUp(Integer siteId, Integer cid, Integer start, Integer end)
   {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("site.id", siteId));
     if (cid != null) {
       crit.add(Restrictions.eq("channel.id", cid));
     }
     crit.add(Restrictions.ge("id", start));
     crit.add(Restrictions.le("id", end));
     return findByCriteria(crit);
   }
 
   public List<Object> getCountByDepart(Integer siteId, String treeNumber, Integer dId, Date start, Date end)
   {
     Criteria crit = createCriteria();
     crit.createAlias("inputDepart", "d");
     crit.createAlias("channel", "c");
     crit.add(Restrictions.eq("site.id", siteId));
     if (!StringUtils.isBlank(treeNumber)) {
       crit.add(Restrictions.like("c.number", treeNumber + "%"));
     }
     if (dId != null) {
       crit.add(Restrictions.eq("d.id", dId));
     }
     if (start != null) {
       crit.add(Restrictions.gt("releaseDate", start));
     }
     if (end != null) {
       crit.add(Restrictions.gt("releaseDate", end));
     }
     crit.add(Restrictions.eq("d.show", Boolean.valueOf(true)));
     crit.add(Restrictions.eq("status", Byte.valueOf((byte) 2)));
     ProjectionList projList = Projections.projectionList();
     projList.add(Projections.groupProperty("d.name"));
     projList.add(Projections.alias(Projections.count("inputDepart"), "c"));
     crit.setProjection(projList);
     crit.addOrder(Order.desc("c"));
     return crit.list();
   }
 
   public int getAllArtiCount(Integer siteId, boolean check) {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("site.id", siteId));
     if (check)
       crit.add(Restrictions.eq("status", Byte.valueOf((byte) 1)));
     else {
       crit.add(Restrictions.ne("status", Byte.valueOf((byte) 3)));
     }
     crit.setProjection(Projections.count("id"));
     return ((Number)crit.uniqueResult()).intValue();
   }
 
   public int updateMoveDoc(String treeNumber, Integer modelId, Channel c) {
     String hql = "update Article bean set bean.channel=? where bean.model.id=? and bean.channel.id in (select c.id from Channel c where c.number like ?)";
 
     return executeQuery(hql, new Object[] { c, modelId, treeNumber + "%" });
   }
 
   public int updateDocByInputUser(Integer userId) {
     String hql = "update Article bean set bean.user=null where bean.user.id=?";
     return executeQuery(hql, new Object[] { userId });
   }
 
   public int updateDocByCheckUser(Integer userId) {
     String hql = "update Article bean set bean.checkUser=null where bean.checkUser.id=?";
     return executeQuery(hql, new Object[] { userId });
   }
 
   public int updateDocByInputDepart(Integer departId) {
     String hql = "update Article bean set bean.inputDepart=null where bean.inputDepart.id=?";
     return executeQuery(hql, new Object[] { departId });
   }
 
   public int updateDocByRole(Integer roleId) {
     String hql = "update Article bean set bean.role=null where bean.role.id=?";
     return executeQuery(hql, new Object[] { roleId });
   }
 
   public int delDocByInputUser(Integer userId) {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("user.id", userId));
     Session session = getSession();
     ScrollableResults articles = crit.setCacheMode(CacheMode.IGNORE)
       .scroll(ScrollMode.FORWARD_ONLY);
 
     int count = 0;
     while (articles.next()) {
       Article article = (Article)articles.get(0);
       session.delete(article);
 
       count++; if (count % 20 == 0) {
         session.clear();
       }
     }
     return count;
   }
 
   public int delDocBySite(Integer siteId) {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("site.id", siteId));
     Session session = getSession();
     ScrollableResults articles = crit.setCacheMode(CacheMode.IGNORE)
       .scroll(ScrollMode.FORWARD_ONLY);
 
     int count = 0;
     while (articles.next()) {
       Article article = (Article)articles.get(0);
       session.delete(article);
 
       count++; if (count % 20 == 0) {
         session.clear();
       }
     }
     return count;
   }
 
   private Criteria byChannelIds(Integer[] channelIds, Integer siteId, Integer[] modelIds, Integer[] typeIds, Integer departId, Integer userId, String number, Boolean recommend, Date date, int orderBy, int callLevel)
   {
     Criteria crit = createCriteria();
     if (channelIds != null) {
       if (callLevel == 1) {
         crit.createAlias("channel", "c");
         if (!StringUtils.isBlank(number))
           crit.add(Restrictions.like("c.number", number + "%"));
       }
       else if (callLevel == 2) {
         crit.createAlias("channels", "c");
         crit.add(Restrictions.eq("c.id", channelIds[0]));
       } else {
         appendChannelIds(crit, channelIds);
       }
     }
     else crit.add(Restrictions.eq("site.id", siteId));
 
     if (departId != null) {
       crit.add(Restrictions.eq("inputDepart.id", departId));
     }
     if (userId != null) {
       crit.add(Restrictions.eq("user.id", userId));
     }
     if (recommend != null) {
       crit.add(Restrictions.eq("recommend", recommend));
     }
     if (date != null) {
       Calendar c = Calendar.getInstance();
       c.setTime(date);
       c.add(5, 1);
       crit.add(Restrictions.between("releaseDate", date, c.getTime()));
     }
     appendModelIds(crit, modelIds);
     appendTypeIds(crit, typeIds);
     crit.add(Restrictions.eq("status", Byte.valueOf((byte) 2)));
     appendOrder(crit, orderBy);
     return crit;
   }
 
   private Criteria byPageArticle(String title, Integer[] typeIds, Integer[] modelIds, Integer inputDepartId, boolean top, boolean recommend, Byte[] statuss, Integer siteId, Integer userId, Integer departId, Integer roleId, String number, Byte manageStatus, Boolean allChannel, Boolean takeDepart)
   {
     Criteria crit = createCriteria();
     crit.createAlias("channel", "c");
     if (!StringUtils.isBlank(number))
       crit.add(Restrictions.like("c.number", number + "%"));
     else {
       crit.add(Restrictions.eq("site.id", siteId));
     }
     if (!allChannel.booleanValue()) {
       if (!takeDepart.booleanValue()) {
         crit.createAlias("c.checks", "check");
         crit.add(Restrictions.eq("check.admin.id", userId));
       } else {
         crit.createAlias("c.departs", "depart");
         crit.add(Restrictions.eq("depart.id", departId));
       }
     }
     if (inputDepartId != null) {
       crit.add(Restrictions.eq("inputDepart.id", inputDepartId));
     }
     if (manageStatus.equals(Byte.valueOf((byte) 1))) {
       crit.add(Restrictions.eq("user.id", userId));
     } else if (manageStatus.equals(Byte.valueOf((byte) 2))) {
       crit.add(Restrictions.eq("inputDepart.id", departId));
       crit.add(Restrictions.or(Restrictions.eq("user.id", userId), 
         Restrictions.eq("role.id", roleId)));
     } else if (manageStatus.equals(Byte.valueOf((byte) 3))) {
       crit.add(Restrictions.eq("inputDepart.id", departId));
     }
     if (!StringUtils.isBlank(title)) {
       crit.add(Restrictions.like("title", "%" + title + "%"));
     }
     if (typeIds != null) {
       appendTypeIds(crit, typeIds);
     }
     if (modelIds != null) {
       appendModelIds(crit, modelIds);
     }
     if (top) {
       crit.add(Restrictions.eq("top", Boolean.valueOf(true)));
     }
     if (recommend) {
       crit.add(Restrictions.eq("recommend", Boolean.valueOf(true)));
     }
     if (statuss != null)
       appendStatuss(crit, statuss);
     else {
       crit.add(Restrictions.ne("status", Byte.valueOf((byte) 3)));
     }
     return crit;
   }
 
   private Criteria byModelIds(Integer[] modelIds, Integer[] typeIds, Integer siteId, Boolean recommend, int orderBy)
   {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("site.id", siteId));
     appendModelIds(crit, modelIds);
     if (recommend != null) {
       crit.add(Restrictions.eq("recommend", recommend));
     }
     appendTypeIds(crit, typeIds);
     crit.add(Restrictions.eq("status", Byte.valueOf((byte) 2)));
     appendOrder(crit, orderBy);
     return crit;
   }
 
   private void appendChannelIds(Criteria crit, Integer[] channelIds) {
     if (channelIds != null) {
       int len = channelIds.length;
       if (len == 1)
         crit.add(Restrictions.eq("channel.id", channelIds[0]));
       else
         crit.add(Restrictions.in("channel.id", channelIds));
     }
   }
 
   private void appendModelIds(Criteria crit, Integer[] modelIds)
   {
     if (modelIds != null) {
       int len = modelIds.length;
       if (len == 1)
         crit.add(Restrictions.eq("model.id", modelIds[0]));
       else
         crit.add(Restrictions.in("model.id", modelIds));
     }
   }
 
   private void appendStatuss(Criteria crit, Byte[] statuss)
   {
     if (statuss != null) {
       int len = statuss.length;
       if (len == 1) {
         crit.add(Restrictions.eq("status", statuss[0]));
       } else if (len > 1) {
         Disjunction dis = Restrictions.disjunction();
         for (Byte status : statuss) {
           dis.add(Restrictions.eq("status", status));
         }
         crit.add(dis);
       }
     }
   }
 
   private void appendTypeIds(Criteria crit, Integer[] typeIds)
   {
     if (typeIds != null) {
       int len = typeIds.length;
       if (len == 1) {
         crit.add(Restrictions.like("style", "%," + typeIds[0] + ",%"));
       } else if (len > 1) {
         Disjunction dis = Restrictions.disjunction();
         for (Integer typeId : typeIds) {
           dis.add(Restrictions.like("style", "%," + typeId + ",%"));
         }
         crit.add(dis);
       }
     }
   }
 
   private void appendOrder(Criteria crit, int orderBy) {
     switch (orderBy) {
     case 1:
       crit.addOrder(Order.desc("releaseDate"));
       break;
     case 2:
       crit.addOrder(Order.desc("top"));
       crit.addOrder(Order.desc("releaseDate"));
       break;
     case 3:
       crit.addOrder(Order.desc("recommend"));
       crit.addOrder(Order.desc("releaseDate"));
       break;
     case 4:
       crit.createAlias("docStatis", "docStatis");
       crit.addOrder(Order.desc("docStatis.viewsCount"));
       crit.addOrder(Order.desc("releaseDate"));
       break;
     case 5:
       crit.createAlias("docStatis", "docStatis");
       crit.addOrder(Order.desc("docStatis.commentCount"));
       crit.addOrder(Order.desc("releaseDate"));
       break;
     case 6:
       crit.addOrder(Order.desc("top"));
       crit.addOrder(Order.desc("recommend"));
       crit.addOrder(Order.desc("releaseDate"));
       break;
     case 7:
       crit.addOrder(Order.asc("releaseDate"));
       break;
     default:
       crit.addOrder(Order.desc("releaseDate"));
     }
   }
 
   protected Class<Article> getEntityClass()
   {
     return Article.class;
   }
 }


 
 
 
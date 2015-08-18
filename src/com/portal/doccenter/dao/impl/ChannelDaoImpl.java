 package com.portal.doccenter.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.doccenter.dao.ChannelDao;
 import com.portal.doccenter.entity.Channel;
 import java.util.List;
 import org.apache.commons.lang.StringUtils;
 import org.hibernate.Criteria;
 import org.hibernate.criterion.Order;
 import org.hibernate.criterion.Projections;
 import org.hibernate.criterion.Restrictions;
 import org.hibernate.type.IntegerType;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class ChannelDaoImpl extends QueryDaoImpl<Channel, Integer>
   implements ChannelDao
 {
   public List<Channel> getChannelListByTag(Integer siteId, Integer parentId, Boolean alone, boolean show, int count)
   {
     Criteria crit = getChannelByTag(siteId, parentId, alone, show);
     crit.setMaxResults(count);
     crit.setCacheable(true);
     return findByCriteria(crit);
   }
 
   public Page<Channel> getChannelPageByTag(Integer siteId, Integer parentId, Boolean alone, boolean show, int pageNo, int pageSize)
   {
     Criteria crit = getChannelByTag(siteId, parentId, alone, show);
     crit.setCacheable(true);
     return findByCriteria(crit, pageNo, pageSize);
   }
 
   public List<Channel> getChannelBySite(Integer siteId, Integer parentId, String key, String sortname, String sortorder, Boolean alone)
   {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("site.id", siteId));
     if (parentId != null)
       crit.add(Restrictions.eq("parent.id", parentId));
     else {
       crit.add(Restrictions.isNull("parent.id"));
     }
     if (!StringUtils.isBlank(key)) {
       crit.add(Restrictions.or(
         Restrictions.like("name", "%" + key + "%"), 
         Restrictions.like("path", "%" + key + "%")));
     }
     if (alone != null) {
       crit.add(Restrictions.eq("alone", alone));
     }
     if (!StringUtils.isBlank(sortname)) {
       if ("asc".equals(sortorder))
         crit.addOrder(Order.asc(sortname));
       else
         crit.addOrder(Order.desc(sortname));
     }
     else {
       crit.addOrder(Order.asc("priority"));
       crit.addOrder(Order.asc("id"));
     }
     return findByCriteria(crit);
   }
 
   public List<Channel> getChannelByAdmin(Integer siteId, Integer departId, Integer parentId, String key, String sortname, String sortorder, Boolean alone)
   {
     Criteria crit = createCriteria();
     if (departId != null) {
       crit.createAlias("departs", "depart");
       crit.add(Restrictions.eq("depart.id", departId));
     } else {
       crit.add(Restrictions.eq("site.id", siteId));
     }
     if (parentId != null)
       crit.add(Restrictions.eq("parent.id", parentId));
     else {
       crit.add(Restrictions.isNull("parent.id"));
     }
     if (!StringUtils.isBlank(key)) {
       crit.add(Restrictions.or(
         Restrictions.like("name", "%" + key + "%"), 
         Restrictions.like("path", "%" + key + "%")));
     }
     if (alone != null) {
       crit.add(Restrictions.eq("alone", alone));
     }
     if (!StringUtils.isBlank(sortname)) {
       if ("asc".equals(sortorder))
         crit.addOrder(Order.asc(sortname));
       else
         crit.addOrder(Order.desc(sortname));
     }
     else {
       crit.addOrder(Order.asc("priority"));
       crit.addOrder(Order.asc("id"));
     }
     return findByCriteria(crit);
   }
 
   public List<Channel> getChannelByAdminAndTake(Integer siteId, Integer adminId, Integer parentId, String key, String sortname, String sortorder, Boolean alone)
   {
     Criteria crit = createCriteria();
     if (adminId != null) {
       crit.createAlias("checks", "check");
       crit.add(Restrictions.eq("check.admin.id", adminId));
     } else {
       crit.add(Restrictions.eq("site.id", siteId));
     }
     if (parentId != null)
       crit.add(Restrictions.eq("parent.id", parentId));
     else {
       crit.add(Restrictions.isNull("parent.id"));
     }
     if (!StringUtils.isBlank(key)) {
       crit.add(Restrictions.or(
         Restrictions.like("name", "%" + key + "%"), 
         Restrictions.like("path", "%" + key + "%")));
     }
     if (alone != null) {
       crit.add(Restrictions.eq("alone", alone));
     }
     if (!StringUtils.isBlank(sortname)) {
       if ("asc".equals(sortorder))
         crit.addOrder(Order.asc(sortname));
       else
         crit.addOrder(Order.desc(sortname));
     }
     else {
       crit.addOrder(Order.asc("priority"));
       crit.addOrder(Order.asc("id"));
     }
     return findByCriteria(crit);
   }
 
   public List<Channel> getChannelByModel(Integer parentId, Integer modelId, Integer departId, Integer siteId)
   {
     StringBuffer sb = new StringBuffer("{alias}.channel_id in ");
     sb.append("(select e.chnl_id from tq_chnl_tpl_selection e where e.model_id=?)");
     Criteria crit = createCriteria();
     crit.add(Restrictions.sqlRestriction(sb.toString(), modelId, 
       IntegerType.INSTANCE));
     if (departId != null) {
       crit.createAlias("departs", "depart");
       crit.add(Restrictions.eq("depart.id", departId));
     }
     crit.add(Restrictions.eq("site.id", siteId));
     if (parentId != null)
       crit.add(Restrictions.eq("parent.id", parentId));
     else {
       crit.add(Restrictions.isNull("parent.id"));
     }
     crit.add(Restrictions.eq("alone", Boolean.valueOf(false)));
     crit.addOrder(Order.asc("priority"));
     crit.addOrder(Order.asc("id"));
     return findByCriteria(crit);
   }
 
   public List<Channel> getChannelByModelAndTake(Integer parentId, Integer modelId, Integer adminId, Integer siteId)
   {
     StringBuffer sb = new StringBuffer("{alias}.channel_id in ");
     sb.append("(select e.chnl_id from tq_chnl_tpl_selection e where e.model_id=?)");
     Criteria crit = createCriteria();
     crit.add(Restrictions.sqlRestriction(sb.toString(), modelId, 
       IntegerType.INSTANCE));
     if (adminId != null) {
       crit.createAlias("checks", "check");
       crit.add(Restrictions.eq("check.admin.id", adminId));
     }
     crit.add(Restrictions.eq("site.id", siteId));
     if (parentId != null)
       crit.add(Restrictions.eq("parent.id", parentId));
     else {
       crit.add(Restrictions.isNull("parent.id"));
     }
     crit.add(Restrictions.eq("alone", Boolean.valueOf(false)));
     crit.addOrder(Order.asc("priority"));
     crit.addOrder(Order.asc("id"));
     return findByCriteria(crit);
   }
 
   public List<Channel> getChannelByModelAndMember(Integer parentId, Integer modelId, Integer groupId, Integer siteId)
   {
     StringBuffer sb = new StringBuffer("{alias}.channel_id in ");
     sb.append("(select e.chnl_id from tq_chnl_tpl_selection e where e.model_id=?)");
     Criteria crit = createCriteria();
     crit.add(Restrictions.sqlRestriction(sb.toString(), modelId, 
       IntegerType.INSTANCE));
     if (groupId != null) {
       crit.createAlias("contriGroups", "g");
       crit.add(Restrictions.eq("g.id", groupId));
     }
     crit.add(Restrictions.eq("site.id", siteId));
     if (parentId != null)
       crit.add(Restrictions.eq("parent.id", parentId));
     else {
       crit.add(Restrictions.isNull("parent.id"));
     }
     crit.add(Restrictions.eq("alone", Boolean.valueOf(false)));
     crit.addOrder(Order.asc("priority"));
     crit.addOrder(Order.asc("id"));
     return findByCriteria(crit);
   }
 
   public Channel findByNumber(String number) {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("chnlNumber", number));
     return (Channel)findUnique(crit);
   }
 
   public Channel findByPath(String path, Integer siteId, boolean cache) {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("path", path));
     crit.add(Restrictions.eq("site.id", siteId));
     crit.setCacheable(cache);
     return (Channel)findUnique(crit);
   }
 
   public Channel findByName(String name) {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("name", name));
     return (Channel)findUnique(crit);
   }
 
   public int getAllChannelCount(Integer siteId) {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("site.id", siteId));
     crit.setProjection(Projections.count("id"));
     return ((Number)crit.uniqueResult()).intValue();
   }
 
   public int updateChannelByInputDepart(Integer departId) {
     String hql = "update Channel bean set bean.inputDepart=null where bean.inputDepart.id=?";
     return executeQuery(hql, new Object[] { departId });
   }
 
   public int delChannelByInputDepart(Integer departId) {
     String hql = "delete from Channel bean where bean.inputDepart.id=?";
     return executeQuery(hql, new Object[] { departId });
   }
 
   public int updateChannelByWorkFlow(Integer flowId) {
     String hql = "update Channel bean set bean.flow=null where bean.flow.id=?";
     return executeQuery(hql, new Object[] { flowId });
   }
 
   private Criteria getChannelByTag(Integer siteId, Integer parentId, Boolean alone, boolean show)
   {
     Criteria crit = createCriteria();
     if (parentId != null) {
       crit.add(Restrictions.eq("parent.id", parentId));
     } else {
       crit.add(Restrictions.eq("site.id", siteId));
       crit.add(Restrictions.isNull("parent.id"));
     }
     if (show) {
       crit.add(Restrictions.eq("show", Boolean.valueOf(true)));
     }
     if (alone != null) {
       crit.add(Restrictions.eq("alone", alone));
     }
     crit.addOrder(Order.asc("priority"));
     crit.addOrder(Order.asc("id"));
     return crit;
   }
 
   protected Class<Channel> getEntityClass()
   {
     return Channel.class;
   }
 }


 
 
 
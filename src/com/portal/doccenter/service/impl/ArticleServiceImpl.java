 package com.portal.doccenter.service.impl;
 
 import com.portal.datacenter.lucene.LuceneDocService;
 import com.portal.doccenter.dao.ArticleDao;
 import com.portal.doccenter.dao.ArticleQueryDao;
 import com.portal.doccenter.entity.Article;
 import com.portal.doccenter.entity.ArticleExt;
 import com.portal.doccenter.entity.ArticleTxt;
 import com.portal.doccenter.entity.ArticleType;
 import com.portal.doccenter.entity.Channel;
 import com.portal.doccenter.entity.FlowDetail;
 import com.portal.doccenter.service.ArticleExtService;
 import com.portal.doccenter.service.ArticleService;
 import com.portal.doccenter.service.ArticleTxtService;
 import com.portal.doccenter.service.ArticleTypeService;
 import com.portal.doccenter.service.ChannelService;
 import com.portal.doccenter.service.DocStatisService;
 import com.portal.doccenter.service.FlowDetailService;
 import com.portal.doccenter.service.ModelService;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.event.AddArticleEvent;
 import com.portal.sysmgr.event.DelArticleEvent;
 import com.portal.sysmgr.event.EmptyArticleEvent;
 import com.portal.sysmgr.event.UpdateArticleEvent;
 import com.portal.usermgr.entity.Admin;
 import com.portal.usermgr.entity.Depart;
 import com.portal.usermgr.entity.Role;
 import com.portal.usermgr.entity.User;
 import com.portal.usermgr.service.GroupService;
 import java.util.ArrayList;
 import java.util.Date;
 import java.util.List;
 import java.util.Map;
 import java.util.Map.Entry;
 import java.util.Set;
 import org.apache.commons.lang.StringUtils;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.context.ApplicationContext;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class ArticleServiceImpl
   implements ArticleService
 {
   private ChannelService channelService;
   private ArticleExtService articleExtService;
   private ArticleTxtService articleTxtService;
   private DocStatisService statisService;
   private ArticleTypeService articleTypeService;
   private GroupService groupService;
   private ModelService modelService;
   private LuceneDocService luceneDocService;
   private FlowDetailService detailService;
   private ArticleDao dao;
   private ArticleQueryDao articleQueryDao;
   private ApplicationContext applicationContext;
 
   @Transactional(readOnly=true)
   public Page<Article> getPageArticle(String title, Integer[] typeIds, Integer[] modelIds, 
		   Integer inputDepartId, boolean top, boolean recommend,
		   Byte[] statuss, Integer siteId, User user, Integer chnlId, int orderBy, String sortname, String sortorder, int pageNo, int pageSize)
   {
     return
    		 this.dao.getPageArticle(title, typeIds, modelIds, inputDepartId, top, 
            recommend, statuss, siteId, user.getId(), 
       user.getDepartId(siteId), user.getRoleId(siteId), 
       getTreeNumber(chnlId), user.getManageStatus(siteId), 
       Boolean.valueOf(user.getAllChannel(siteId)), user.getTakeDepart(siteId), 
       orderBy, sortname, sortorder, pageNo, pageSize);
   }
 
   public Page<Article> getPageDocByMember(String title, Integer[] typeIds, Integer[] modelIds, boolean top, boolean recommend, Integer siteId, Integer userId, Integer chnlId, int pageNo, int pageSize)
   {
     return this.dao.getPageDocByMember(title, typeIds, modelIds, top, recommend, 
       siteId, userId, getTreeNumber(chnlId), pageNo, pageSize);
   }
   @Transactional(readOnly=true)
   public long getCountByChecking(Integer siteId, User user, boolean checking) {
     return this.dao.getCountByChecking(siteId, user.getId(), 
       user.getDepartId(siteId), user.getRoleId(siteId), 
       user.getManageStatus(siteId), Boolean.valueOf(user.getAllChannel(siteId)), 
       checking);
   }
   @Transactional(readOnly=true)
   public int getAllArtiCount(Integer siteId, boolean check) {
     return this.dao.getAllArtiCount(siteId, check);
   }
   @Transactional(readOnly=true)
   public long getCountDoc(Integer chnlId) {
     return this.dao.getCountDoc(getTreeNumber(chnlId));
   }
   @Transactional(readOnly=true)
   public long getCountByDepartSign(Integer siteId, Integer departId) {
     return this.dao.getCountByDepartSign(siteId, departId);
   }
 
   @Transactional(readOnly=true)
   public Article getSide(Integer id, Integer siteId, Integer channelId, boolean next) {
     return this.dao.getSide(id, siteId, channelId, next);
   }
   @Transactional(readOnly=true)
   public List<Article> getListTagByIds(Integer[] ids, int orderBy) {
     if (ids.length == 1) {
       Article article = findById(ids[0]);
       List<Article> list;
       if (article != null) {
          list = new ArrayList(1);
         list.add(article);
       } else {
         list = new ArrayList(0);
       }
       return list;
     }
     return this.dao.getListTagByIds(ids, orderBy);
   }
 
   @Transactional(readOnly=true)
   public Page<Article> getPageTagByChannelIds(Integer[] channelIds, Integer siteId, Integer[] modelIds, Integer[] typeIds, Integer inputDepartId, Integer userId, Boolean recommend, Date date, int orderBy, int callLevel, int pageNo, int pageSize)
   {
     return this.dao.getPageTagByChannelIds(channelIds, siteId, modelIds, 
       typeIds, inputDepartId, userId, getTreeNumber(channelIds), 
       recommend, date, orderBy, callLevel, pageNo, pageSize);
   }
 
   @Transactional(readOnly=true)
   public List<Article> getListTagByChannelIds(Integer[] channelIds, Integer siteId, Integer[] modelIds, Integer[] typeIds, Integer inputDepartId, Integer userId, Boolean recommend, Date date, int orderBy, int callLevel, Integer first, Integer count)
   {
     return this.dao.getListTagByChannelIds(channelIds, siteId, modelIds, 
       typeIds, inputDepartId, userId, getTreeNumber(channelIds), 
       recommend, date, orderBy, callLevel, first, count);
   }
 
   @Transactional(readOnly=true)
   public Page<Article> getPageTagByModelIds(Integer[] modelIds, Integer[] typeIds, Integer siteId, Boolean recommend, int orderBy, int pageNo, int pageSize)
   {
     return this.dao.getPageTagByModelIds(modelIds, typeIds, siteId, recommend, 
       orderBy, pageNo, pageSize);
   }
 
   @Transactional(readOnly=true)
   public List<Article> getListTagByModelIds(Integer[] modelIds, Integer[] typeIds, Integer siteId, Boolean recommend, int orderBy, Integer first, Integer count)
   {
     return this.dao.getListTagByModelIds(modelIds, typeIds, siteId, recommend, 
       orderBy, first, count);
   }
 
   @Transactional(readOnly=true)
   public List<Object> getCountByDepart(Integer siteId, Integer cid, Integer dId, Date start, Date end) {
     return this.dao
       .getCountByDepart(siteId, getTreeNumber(cid), dId, start, end);
   }
   @Transactional(readOnly=true)
   public Article findById(Integer id) {
     Article entity = (Article)this.dao.findById(id);
     return entity;
   }
 
   public Article save(Article bean, ArticleExt ext, ArticleTxt txt, Site site, User user, Integer[] viewGroupIds, String[] attPaths, String[] attNames, String[] imgPaths, String[] imgDescs, Boolean[] thumbs, String[] imgStyles, Integer channelId, Integer modelId, boolean isMember)
   {
     bean.setChannel(this.channelService.findById(channelId));
     if (StringUtils.isBlank(bean.getStyle()))
       bean.setStyle("," + this.articleTypeService.getDef().getId() + ",");
     else {
       bean.setStyle("," + bean.getStyle() + ",");
     }
     if (modelId != null) {
       bean.setModel(this.modelService.findById(modelId));
     }
     bean.setUser(user);
     bean.setSite(site);
     if (bean.getStatus() == null) {
       if (!isMember)
         flowstep(bean, site, user);
       else {
         bean.setStatus(Byte.valueOf((byte) 1));
       }
     }
     bean.init();
     this.dao.save(bean);
     this.articleExtService.save(ext, bean);
     this.articleTxtService.save(txt, bean);
     this.statisService.save(bean);
     if ((viewGroupIds != null) && (viewGroupIds.length > 0)) {
       for (Integer gid : viewGroupIds) {
         bean.addToGroups(this.groupService.findById(gid));
       }
     }
     if ((attPaths != null) && (attPaths.length > 0)) {
       int i = 0; for (int len = attPaths.length; i < len; i++) {
         if (!StringUtils.isBlank(attPaths[i])) {
           bean.addToAttachmemts(attPaths[i], attNames[i]);
         }
       }
     }
     if ((imgPaths != null) && (imgPaths.length > 0)) {
       for (int i = 0; i < imgPaths.length; i++) {
         if (!StringUtils.isBlank(imgPaths[i])) {
           bean.addToPictures(imgPaths[i], imgDescs[i], thumbs[i], 
             imgStyles[i]);
         }
       }
     }
     this.luceneDocService.addDoc(bean);
     this.applicationContext.publishEvent(new AddArticleEvent(bean));
     return bean;
   }
 
   public Article update(Article bean, ArticleExt ext, ArticleTxt txt, Integer[] channelIds, Integer[] viewGroupIds, String[] attPaths, String[] attNames, String[] imgPaths, String[] imgDescs, Boolean[] thumbs, String[] imgStyles, Map<String, String> attr, Integer channelId, User user, boolean isMember)
   {
     bean = (Article)this.dao.update(bean);
     if (StringUtils.isBlank(bean.getStyle()))
       bean.setStyle("," + this.articleTypeService.getDef().getId() + ",");
     else {
       bean.setStyle("," + bean.getStyle() + ",");
     }
     if (channelId != null) {
       bean.setChannel(this.channelService.findById(channelId));
     }
     if (isMember) {
       bean.setStatus(Byte.valueOf((byte) 1));
     }
     this.articleExtService.update(ext);
     this.articleTxtService.update(txt, bean);
     if (attr != null) {
       bean.getAttr().clear();
       bean.getAttr().putAll(attr);
     }
     Set groups = bean.getViewGroups();
     groups.clear();
     if ((viewGroupIds != null) && (viewGroupIds.length > 0)) {
       for (Integer gid : viewGroupIds) {
         groups.add(this.groupService.findById(gid));
       }
     }
     bean.getAtts().clear();
     if ((attPaths != null) && (attPaths.length > 0)) {
       int i = 0; for (int len = attPaths.length; i < len; i++) {
         if (!StringUtils.isBlank(attPaths[i])) {
           bean.addToAttachmemts(attPaths[i], attNames[i]);
         }
       }
     }
     bean.getPics().clear();
     if ((imgPaths != null) && (imgPaths.length > 0)) {
       for (int i = 0; i < imgPaths.length; i++) {
         if (!StringUtils.isBlank(imgPaths[i])) {
           bean.addToPictures(imgPaths[i], imgDescs[i], thumbs[i], 
             imgStyles[i]);
         }
       }
     }
     this.luceneDocService.updateDoc(bean);
     this.applicationContext.publishEvent(new UpdateArticleEvent(bean));
     return bean;
   }
 
   public int moveDoc(Integer chnlId, Map<String, String> channels) {
     int count = 0;
     Channel c = this.channelService.findById(chnlId);
     for (Map.Entry entry : channels.entrySet()) {
       if (StringUtils.isNotBlank((String)entry.getValue())) {
         this.dao.updateMoveDoc(c.getNumber(), Integer.valueOf(Integer.parseInt(
           (String)entry
           .getKey())), this.channelService.findById(
           Integer.valueOf(Integer.parseInt((String)entry.getValue()))));
         this.luceneDocService.updateChannel(c.getSite().getId(), 
           Integer.valueOf(Integer.parseInt((String)entry.getValue())));
         count++;
       }
     }
     this.luceneDocService.updateChannel(c.getSite().getId(), chnlId);
     c.getSite().initTime();
     return count;
   }
 
   public int emptyDoc(Integer chnlId) {
     Channel c = this.channelService.findById(chnlId);
     this.applicationContext.publishEvent(new EmptyArticleEvent(c));
     int count = this.articleQueryDao.emptyArticlePage(c.getNumber());
     this.luceneDocService.deleteSearchIndex(c.getSite().getId(), chnlId);
     return count;
   }
 
   public Article check(Integer id, User user) {
     Article article = findById(id);
     Integer siteId = article.getSite().getId();
     Role role = user.getRole(siteId);
     Integer roleId = role.getId();
     if (!article.isChecked()) {
       if (article.getChannel().getFlow() != null) {
         if (article.getChannel().isDocChecked(user.getAdmin())) {
           article.setStatus(Byte.valueOf((byte) 2));
           article.setCheckUser(user);
           article.setRole(role);
           this.luceneDocService.addDoc(article);
           this.applicationContext.publishEvent(
             new UpdateArticleEvent(article));
         } else {
           article.setStatus(Byte.valueOf((byte) 1));
           article.setCheckUser(user);
           article.setRole(article.getChannel().getNextRole(roleId));
         }
         return article;
       }
       article.setStatus(Byte.valueOf((byte) 2));
       article.setCheckUser(user);
       this.luceneDocService.addDoc(article);
       this.applicationContext.publishEvent(new UpdateArticleEvent(article));
     } else {
       article.setStatus(Byte.valueOf((byte) 1));
       this.luceneDocService.deleteDoc(article.getId());
       this.applicationContext.publishEvent(new DelArticleEvent(article, Integer.valueOf(1)));
     }
     return article;
   }
 
   public Article back(Integer id, User user, String reason, boolean initial) {
     Article article = findById(id);
     Integer siteId = article.getSite().getId();
     Role role = user.getAdmin().getRole(siteId);
     if ((article.getStatus().equals(Byte.valueOf((byte) 1))) || 
       (article.getStatus().equals(Byte.valueOf((byte) -1)))) {
       if (article.getChannel().getFlow() != null) {
         if (initial) {
           article.setRole(null);
         } else {
           FlowDetail fd = this.detailService.getLastFlowDetail(id);
           if (fd != null)
             article.setRole(role);
           else
             article.setRole(null);
         }
       }
       else {
         article.setRole(null);
       }
       article.setStatus(Byte.valueOf((byte) -1));
       this.detailService.saveReturn(article, user, role, reason, initial);
       return article;
     }
     return article;
   }
 
   public Article[] check(Integer[] ids, User user) {
     Article[] beans = new Article[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = check(ids[i], user);
     }
     return beans;
   }
 
   public Article cycle(Integer id) {
     Article article = findById(id);
     article.setStatus(Byte.valueOf((byte) 3));
     this.luceneDocService.deleteDoc(id);
     this.applicationContext.publishEvent(new DelArticleEvent(article, Integer.valueOf(1)));
     return article;
   }
 
   public Article[] cycle(Integer[] ids) {
     Article[] beans = new Article[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = cycle(ids[i]);
     }
     return beans;
   }
 
   public Article reduct(Integer id) {
     Article article = findById(id);
     article.setStatus(Byte.valueOf((byte) 2));
     this.luceneDocService.addDoc(article);
     this.applicationContext.publishEvent(new AddArticleEvent(article));
     return article;
   }
 
   public Article[] reduct(Integer[] ids) {
     Article[] beans = new Article[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = reduct(ids[i]);
     }
     return beans;
   }
 
   public int updateDocByInputUser(Integer userId) {
     return this.dao.updateDocByInputUser(userId);
   }
 
   public int updateDocByCheckUser(Integer userId) {
     return this.dao.updateDocByCheckUser(userId);
   }
 
   public int updateDocByInputDepart(Integer departId) {
     return this.dao.updateDocByInputDepart(departId);
   }
 
   public int updateDocByRole(Integer roleId) {
     return this.dao.updateDocByRole(roleId);
   }
 
   public int delDocByInputUser(Integer userId) {
     return this.dao.delDocByInputUser(userId);
   }
 
   public int delDocBySite(Integer siteId) {
     return this.dao.delDocBySite(siteId);
   }
 
   public Article deleteById(Integer id) {
     Article bean = (Article)this.dao.deleteById(id);
     this.applicationContext.publishEvent(new DelArticleEvent(bean, Integer.valueOf(0)));
     this.luceneDocService.deleteDoc(id);
     return bean;
   }
 
   public Article[] deleteByIds(Integer[] ids) {
     Article[] beans = new Article[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   private void flowstep(Article bean, Site site, User user) {
     if (bean.getStatus() == null) {
       Depart depart = user.getDepart(site.getId());
       Role role = user.getRole(site.getId());
       bean.setCheckUser(user);
       bean.setInputDepart(depart);
       if (bean.getChannel().getFlow() != null) {
         if (bean.getChannel().getFlowStep(role.getId()) != null) {
           if (bean.getChannel().isLastStep(role)) {
             bean.setStatus(Byte.valueOf((byte) 2));
             bean.setRole(role);
           } else {
             bean.setStatus(Byte.valueOf((byte) 1));
             bean.setRole(bean.getChannel()
               .getNextRole(role.getId()));
           }
         }
         else bean.setStatus(Byte.valueOf((byte) 2));
       }
       else
         bean.setStatus(Byte.valueOf((byte) 2));
     }
   }
 
   private String getTreeNumber(Integer cId)
   {
     if (cId == null) {
       return null;
     }
     Channel c = this.channelService.findById(cId);
     if (c != null) {
       return c.getNumber();
     }
     return null;
   }
 
   private String getTreeNumber(Integer[] cIds) {
     if ((cIds == null) || (cIds.length == 0)) {
       return null;
     }
     Channel c = this.channelService.findById(cIds[0]);
     if (c != null) {
       return c.getNumber();
     }
     return null;
   }
 
   @Autowired
   public void setChannelService(ChannelService channelService)
   {
     this.channelService = channelService;
   }
   @Autowired
   public void setArticleExtService(ArticleExtService articleExtService) {
     this.articleExtService = articleExtService;
   }
   @Autowired
   public void setArticleTxtService(ArticleTxtService articleTxtService) {
     this.articleTxtService = articleTxtService;
   }
   @Autowired
   public void setStatisService(DocStatisService statisService) {
     this.statisService = statisService;
   }
   @Autowired
   public void setArticleTypeService(ArticleTypeService articleTypeService) {
     this.articleTypeService = articleTypeService;
   }
   @Autowired
   public void setGroupService(GroupService groupService) {
     this.groupService = groupService;
   }
   @Autowired
   public void setModelService(ModelService modelService) {
     this.modelService = modelService;
   }
   @Autowired
   public void setLuceneDocService(LuceneDocService luceneDocService) {
     this.luceneDocService = luceneDocService;
   }
   @Autowired
   public void setDetailService(FlowDetailService detailService) {
     this.detailService = detailService;
   }
   @Autowired
   public void setDao(ArticleDao dao) {
     this.dao = dao;
   }
   @Autowired
   public void setArticleQueryDao(ArticleQueryDao articleQueryDao) {
     this.articleQueryDao = articleQueryDao;
   }
   @Autowired
   public void setApplicationContext(ApplicationContext applicationContext) {
     this.applicationContext = applicationContext;
   }
 }


 
 
 
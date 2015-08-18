package com.portal.datacenter.operatedata.service.impl;

import com.javassf.basic.plugin.springmvc.MessageResolver;
import com.javassf.basic.utils.ServicesUtils;
import com.portal.datacenter.operatedata.dao.LogDao;
import com.portal.datacenter.operatedata.entity.Log;
import com.portal.datacenter.operatedata.service.LogService;
import com.portal.sysmgr.entity.Site;
import com.portal.sysmgr.utils.ContextTools;
import com.portal.usermgr.entity.User;
import com.portal.usermgr.service.UserService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UrlPathHelper;

@Service
@Transactional
public class LogServiceImpl
  implements LogService
{
  private UserService userService;
  private LogDao dao;

  @Transactional(readOnly=true)
  public Page<Log> getPage(Integer category, Integer siteId, String username, String title, String ip, int pageNo, int pageSize)
  {
    Page page;
   // Page page;
    if (StringUtils.isBlank(username)) {
      page = this.dao.getPage(category, siteId, null, title, ip, pageNo, 
        pageSize);
    } else {
      User user = this.userService.findByUsername(username);
     // Page page;
      if (user != null) {
        page = this.dao.getPage(category, siteId, user.getId(), title, ip, 
          pageNo, pageSize);
      } else {
        Pageable p = new PageRequest(pageNo - 1, pageSize);
        page = new PageImpl(new ArrayList(0), p, 0L);
      }
    }
    return page;
  }
  @Transactional(readOnly=true)
  public Log findById(Integer id) {
    Log entity = (Log)this.dao.findById(id);
    return entity;
  }

  public Log save(Integer category, Site site, User user, String url, String ip, Date date, String title, String content)
  {
    Log log = new Log();
    log.setSite(site);
    log.setUser(user);
    log.setCategory(category);
    log.setIp(ip);
    log.setTime(date);
    log.setUrl(url);
    log.setTitle(title);
    log.setContent(content);
    save(log);
    return log;
  }

  public Log loginSuccess(HttpServletRequest request, User user, String title) {
    String ip = ServicesUtils.getIpAddr(request);
    UrlPathHelper helper = new UrlPathHelper();
    String uri = helper.getOriginatingRequestUri(request);
    Date date = new Date();
    Log log = save(Integer.valueOf(1), null, user, uri, ip, date, 
      MessageResolver.getMessage(request, title, new Object[0]), null);
    return log;
  }

  public Log loginFailure(HttpServletRequest request, String title, String content)
  {
    String ip = ServicesUtils.getIpAddr(request);
    UrlPathHelper helper = new UrlPathHelper();
    String uri = helper.getOriginatingRequestUri(request);
    Date date = new Date();
    Log log = save(Integer.valueOf(2), null, null, uri, ip, date, 
      MessageResolver.getMessage(request, title, new Object[0]), content);
    return log;
  }

  public Log operating(HttpServletRequest request, String title, String content)
  {
    Site site = ContextTools.getSite(request);
    User user = ContextTools.getUser(request);
    String ip = ServicesUtils.getIpAddr(request);
    UrlPathHelper helper = new UrlPathHelper();
    String uri = helper.getOriginatingRequestUri(request);
    Date date = new Date();
    Log log = save(Integer.valueOf(3), site, user, uri, ip, date, title, content);
    return log;
  }

  public Log save(Log bean) {
    this.dao.save(bean);
    return bean;
  }

  public int deleteBatch(Integer category, Integer siteId, Integer days) {
    Date date = null;
    if ((days != null) && (days.intValue() > 0)) {
      Calendar cal = Calendar.getInstance();
      cal.add(5, -days.intValue());
      date = cal.getTime();
    }
    return this.dao.deleteBatch(category, siteId, date);
  }

  public Log deleteById(Integer id) {
    Log bean = (Log)this.dao.deleteById(id);
    return bean;
  }

  public Log[] deleteByIds(Integer[] ids) {
    Log[] beans = new Log[ids.length];
    int i = 0; for (int len = ids.length; i < len; i++) {
      beans[i] = deleteById(ids[i]);
    }
    return beans;
  }

  @Autowired
  public void setUserService(UserService userService)
  {
    this.userService = userService;
  }
  @Autowired
  public void setDao(LogDao dao) {
    this.dao = dao;
  }
}
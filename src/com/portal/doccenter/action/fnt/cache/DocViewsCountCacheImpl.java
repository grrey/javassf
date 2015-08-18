package com.portal.doccenter.action.fnt.cache;

import com.portal.doccenter.entity.Article;
import com.portal.doccenter.entity.DocStatis;
import com.portal.doccenter.service.ArticleService;
import com.portal.doccenter.service.DocStatisService;
import java.util.List;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class DocViewsCountCacheImpl
  implements DocViewsCountCache, DisposableBean
{
  private ArticleService articleService;
  private DocStatisService statisService;
  private Ehcache cache;

  public Integer viewsCount(Integer docId)
  {
    DocStatis statis = this.statisService.findById(docId);
    if (statis == null) {
      Article doc = this.articleService.findById(docId);
      if (doc == null) {
        return null;
      }
      statis = this.statisService.save(doc);
    }
    Element e = this.cache.get(docId);
    Integer views;
    //Integer views;
    if (e != null)
      views = Integer.valueOf(((Integer)e.getValue()).intValue() + 1);
    else {
      views = statis.getViewsCount();
    }
    this.cache.put(new Element(docId, views));
    return views;
  }

  public void viewsToDB()
  {
    List<Integer> keys = this.cache.getKeys();
    if (keys.size() <= 0) {
      return;
    }
    for (Integer id : keys) {
      Element e = this.cache.get(id);
      Integer viewsCount = (Integer)e.getValue();
      DocStatis statis = this.statisService.findById(id);
      if (statis == null) {
        Article doc = this.articleService.findById(id);
        if (doc == null) {
          return;
        }
        statis = this.statisService.save(doc);
      }
      this.statisService.update(id, viewsCount);
    }
    this.cache.removeAll();
  }

  public void destroy()
    throws Exception
  {
    List<Integer> keys = this.cache.getKeys();
    if (keys.size() <= 0) {
      return;
    }
    for (Integer id : keys) {
      Element e = this.cache.get(id);
      Integer viewsCount = (Integer)e.getValue();
      this.statisService.update(id, viewsCount);
    }
    this.cache.removeAll();
  }

  @Autowired
  public void setArticleService(ArticleService articleService)
  {
    this.articleService = articleService;
  }
  @Autowired
  public void setStatisService(DocStatisService statisService) {
    this.statisService = statisService;
  }
  @Autowired
  public void setCache(@Qualifier("docViews") Ehcache cache) {
    this.cache = cache;
  }
}
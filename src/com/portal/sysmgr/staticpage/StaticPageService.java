package com.portal.sysmgr.staticpage;

import com.portal.doccenter.entity.Article;
import com.portal.doccenter.entity.Channel;
import com.portal.sysmgr.entity.Site;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.util.List;

public abstract interface StaticPageService
{
  public abstract int staticIndex(Site paramSite);

  public abstract int staticIndexCheck(Site paramSite);

  public abstract List<String> staticChannelPage(Channel paramChannel);

  public abstract void staticChannel(Channel paramChannel);

  public abstract void staticChannelAlone(Channel paramChannel);

  public abstract void staticChannelCheck(Channel paramChannel, Integer paramInteger);

  public abstract void deleteStaticChannel(Channel paramChannel);

  public abstract void deleteAllStaticArticle(Channel paramChannel);

  public abstract String staticArticlePage(Channel paramChannel)
    throws IOException, TemplateException;

  public abstract void staticArticle(Article paramArticle);

  public abstract void staticArticleCheck(Article paramArticle, Integer paramInteger);

  public abstract void deleteStaticArticle(Article paramArticle);
}


 
 
 
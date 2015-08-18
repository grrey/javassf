package com.portal.datacenter.docdata.service.impl;

import com.portal.datacenter.docdata.dao.KeywordDao;
import com.portal.datacenter.docdata.entity.Keyword;
import com.portal.datacenter.docdata.service.KeywordService;
import com.portal.sysmgr.entity.Site;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.htmlparser.Node;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.util.ParserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class KeywordServiceImpl
  implements KeywordService
{
  private KeywordDao dao;

  @Transactional(readOnly=true)
  public List<Keyword> getListBySiteId(Integer siteId, boolean enable, boolean cacheable, String sortname, String sortorder)
  {
    List list = this.dao.getList(siteId, enable, cacheable, sortname, 
      sortorder);
    return list;
  }
  @Transactional(readOnly=true)
  public String attachKeyword(Integer siteId, String txt) {
    if (StringUtils.isBlank(txt)) {
      return txt;
    }
    List<Keyword> list = getListBySiteId(siteId, true, true, null, null);
    int len = list.size();
    if (len <= 0) {
      return txt;
    }
    String[] searchArr = new String[len];
    String[] replacementArr = new String[len];
    int i = 0;
    for (Keyword k : list) {
      searchArr[i] = k.getName();
      String style = "";
      if (k.getBold().booleanValue()) {
        style = "font-weight:bold;";
      }
      if (k.getUnderline().booleanValue()) {
        style = style + "text-decoration:underline;";
      }
      StringBuffer sb = new StringBuffer();
      sb.append("<a href=\"");
      sb.append(k.getUrl()).append("\" target=\"_blank\">");
      if (!StringUtils.isBlank(style)) {
        sb.append("<span style=\"");
        sb.append(style).append("\">");
      }
      sb.append(searchArr[i]);
      if (!StringUtils.isBlank(style)) {
        sb.append("</span>");
      }
      sb.append("</a>");
      replacementArr[i] = sb.toString();
      i++;
    }
    try {
      Lexer lexer = new Lexer(txt);

      StringBuilder sb = new StringBuilder((int)(txt.length() * 1.2D));
      Node node;
      while ((node = lexer.nextNode()) != null)
      {
        //Node node;
        if ((node instanceof TextNode))
          sb.append(StringUtils.replaceEach(node.toHtml(), searchArr, 
            replacementArr));
        else {
          sb.append(node.toHtml());
        }
      }
      return sb.toString(); } catch (ParserException e) {
    	  throw new RuntimeException(e);
    }
  }

  @Transactional(readOnly=true)
  public Keyword findById(Integer id) {
    Keyword entity = (Keyword)this.dao.findById(id);
    return entity;
  }

  public Keyword save(Keyword bean, Site site) {
    bean.setSite(site);
    bean.init();
    this.dao.save(bean);
    return bean;
  }

  public Keyword update(Keyword bean) {
    bean = (Keyword)this.dao.update(bean);
    return bean;
  }

  public Keyword deleteById(Integer id) {
    Keyword bean = (Keyword)this.dao.deleteById(id);
    return bean;
  }

  public Keyword[] deleteByIds(Integer[] ids) {
    Keyword[] beans = new Keyword[ids.length];
    int i = 0; for (int len = ids.length; i < len; i++) {
      beans[i] = deleteById(ids[i]);
    }
    return beans;
  }

  @Autowired
  public void setDao(KeywordDao dao)
  {
    this.dao = dao;
  }
}
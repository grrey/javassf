package com.portal.datacenter.lucene;

import com.portal.doccenter.entity.Article;
import com.portal.doccenter.entity.Channel;
import com.portal.doccenter.entity.Model;
import com.portal.sysmgr.entity.Site;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.DateTools.Resolution;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.NumericUtils;
import org.apache.lucene.util.Version;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class LuceneCommon
{
  public static final String ID = "id";
  public static final String SITE_ID = "siteId";
  public static final String MODEL_ID = "modelId";
  public static final String CHANNEL_ID = "channelId";
  public static final String CHANNEL_NUMBER = "number";
  public static final String RELEASE_DATE = "releaseDate";
  public static final String TITLE = "title";
  public static final String CONTENT = "content";
  public static final String ATTR_VALUE = "attr_value";
  public static final String[] QUERY_FIELD = { "title", "content", "attr_value" };
  public static final BooleanClause.Occur[] QUERY_FLAGS = { 
    BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD, 
    BooleanClause.Occur.SHOULD };

  public static Term term(Integer id)
  {
    return new Term("id", NumericUtils.intToPrefixCoded(id.intValue()));
  }

  public static Document createDoc(Article a) {
    Document doc = new Document();
    doc.add(
      new Field("id", a.getId().toString(), Field.Store.YES, 
      Field.Index.NOT_ANALYZED));
    doc.add(
      new Field("siteId", a.getSite().getId().toString(), 
      Field.Store.NO, Field.Index.NOT_ANALYZED));
    Channel channel = a.getChannel();
    while (channel != null) {
      doc.add(
        new Field("channelId", channel.getId().toString(), 
        Field.Store.NO, Field.Index.NOT_ANALYZED));
      channel = channel.getParent();
    }
    doc.add(
      new Field("modelId", a.getModel().getId().toString(), 
      Field.Store.NO, Field.Index.NOT_ANALYZED));
    doc.add(
      new Field("releaseDate", DateTools.dateToString(
      a.getReleaseDate(), DateTools.Resolution.DAY), Field.Store.NO, 
      Field.Index.NOT_ANALYZED));
    doc.add(
      new Field("title", a.getTitle(), Field.Store.NO, 
      Field.Index.ANALYZED));
    if (!StringUtils.isBlank(a.getTxtValue())) {
      doc.add(
        new Field("content", a.getTxtValue(), Field.Store.NO, 
        Field.Index.ANALYZED));
    }
    if (a.getAttr() != null) {
      Iterator iter = a.getAttr().entrySet()
        .iterator();
      String value = "";
      while (iter.hasNext()) {
        Map.Entry entry = 
          (Map.Entry)iter
          .next();
        if ((StringUtils.isBlank((String)entry.getValue())) || 
          (StringUtils.isBlank((String)entry.getKey()))) continue;
        value = value + (String)entry.getValue();
        doc.add(
          new Field((String)entry.getKey(), (String)entry.getValue(), 
          Field.Store.NO, Field.Index.ANALYZED));
      }

      if (!StringUtils.isBlank(value)) {
        doc.add(
          new Field("attr_value", value, Field.Store.NO, 
          Field.Index.ANALYZED));
      }
    }
    return doc;
  }

  public static Query createQuery(String queryKey, String attrqueryKey, Integer siteId, Integer modelId, Integer channelId, Date startDate, Date endDate)
    throws ParseException
  {
    Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
    BooleanQuery bq = new BooleanQuery();

    if (!StringUtils.isBlank(queryKey)) {
      Query q = MultiFieldQueryParser.parse(Version.LUCENE_36, queryKey, 
        QUERY_FIELD, QUERY_FLAGS, analyzer);
      bq.add(q, BooleanClause.Occur.MUST);
    }
    if (!StringUtils.isBlank(attrqueryKey)) {
      String[] attrs = attrqueryKey.split("!~!");
      for (String attr : attrs) {
        if (attr.indexOf(":") > -1) {
          String[] attrinfo = attr.split(":");
          if ((attrinfo.length != 2) || 
            (StringUtils.isBlank(attrinfo[1]))) continue;
          String[] sq = { attrinfo[1] };
          String[] sf = { attrinfo[0] };
          BooleanClause.Occur[] sfla = { BooleanClause.Occur.MUST };
          Query q = MultiFieldQueryParser.parse(Version.LUCENE_36, sq, 
            sf, sfla, analyzer);
          bq.add(q, BooleanClause.Occur.MUST);
        }
      }
    }

    if (siteId != null) {
      Query q = new TermQuery(new Term("siteId", siteId.toString()));
      bq.add(q, BooleanClause.Occur.MUST);
    }
    if (channelId != null) {
      Query q = new TermQuery(new Term("channelId", channelId.toString()));
      bq.add(q, BooleanClause.Occur.MUST);
    }
    if (modelId != null) {
      Query q = new TermQuery(new Term("modelId", modelId.toString()));
      bq.add(q, BooleanClause.Occur.MUST);
    }
    if ((startDate != null) || (endDate != null)) {
      String start = null;
      String end = null;
      if (startDate != null) {
        start = DateTools.dateToString(startDate, DateTools.Resolution.DAY);
      }
      if (endDate != null) {
        end = DateTools.dateToString(endDate, DateTools.Resolution.DAY);
      }
      Query q = new TermRangeQuery("releaseDate", start, end, true, true);
      bq.add(q, BooleanClause.Occur.MUST);
    }
    return bq;
  }

  public static Page<Integer> getResultPage(IndexSearcher searcher, TopDocs docs, int pageNo, int pageSize)
    throws CorruptIndexException, IOException
  {
    Pageable p = new PageRequest(pageNo - 1, pageSize);
    List list = new ArrayList(pageSize);
    ScoreDoc[] hits = docs.scoreDocs;
    int endIndex = pageNo * pageSize;
    int len = hits.length;
    if (endIndex > len) {
      endIndex = len;
    }
    for (int i = (pageNo - 1) * pageSize; i < endIndex; i++) {
      Document d = searcher.doc(hits[i].doc);
      list.add(Integer.valueOf(d.get("id")));
    }
    return new PageImpl(list, p, docs.totalHits);
  }

  public static List<Integer> getResultList(IndexSearcher searcher, TopDocs docs, int first, int max)
    throws CorruptIndexException, IOException
  {
    List list = new ArrayList(max);
    ScoreDoc[] hits = docs.scoreDocs;
    if (first < 0) {
      first = 0;
    }
    if (max < 0) {
      max = 0;
    }
    int last = first + max;
    int len = hits.length;
    if (last > len) {
      last = len;
    }
    for (int i = first; i < last; i++) {
      Document d = searcher.doc(hits[i].doc);
      list.add(Integer.valueOf(d.get("id")));
    }
    return list;
  }
}
package com.portal.datacenter.lucene;

import com.portal.doccenter.entity.Article;
import com.portal.doccenter.service.ArticleService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NRTManager;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LuceneDocPageServiceImpl implements LuceneDocPageService {
	private ArticleService articleService;
	private NRTManagerService nrtManager;

	@Transactional(readOnly = true)
	public Page<Article> searchArticle(String queryKey, String attrqueryKey, Integer siteId, Integer modelId, Integer channelId, Date startDate, Date endDate, int pageNo, int pageSize) {
		IndexSearcher searcher = null;
		try {
			searcher = (IndexSearcher) this.nrtManager.getNrtManager().acquire();
			Query query = LuceneCommon.createQuery(queryKey, attrqueryKey, siteId, modelId, channelId, startDate, endDate);
			Scorer fragmentScore = new QueryScorer(query);
			Fragmenter fragmenter = new SimpleFragmenter(100);
			Formatter formatter = new SimpleHTMLFormatter("<font color='red'>", "</font>");
			Highlighter highlighter = new Highlighter(formatter, fragmentScore);
			highlighter.setTextFragmenter(fragmenter);
			TopDocs topdocs = searcher.search(query, pageNo * pageSize);

			Page<Integer> p = LuceneCommon.getResultPage(searcher, topdocs, pageNo, pageSize);
			Pageable pa = new PageRequest(pageNo - 1, pageSize);
			List<Integer> ids = p.getContent();
			List<Article > docs = new ArrayList<Article >(ids.size());
			for (Integer id : ids) {
				docs.add(this.articleService.findById(id));
			}
			Page<Article> page = new PageImpl<Article>(docs, pa, p.getTotalElements());
			Page<Article> localPage1 = page;
			return localPage1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				this.nrtManager.getNrtManager().release(searcher);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Autowired
	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}

	@Autowired
	public void setNrtManager(NRTManagerService nrtManager) {
		this.nrtManager = nrtManager;
	}
}
package com.portal.datacenter.lucene;

import com.portal.doccenter.entity.Article;
import com.portal.doccenter.entity.Channel;
import com.portal.doccenter.service.ChannelService;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LuceneDocServiceImpl implements LuceneDocService {
	private LuceneDocDao dao;
	private NRTManagerService nrtManager;
	private ChannelService channelService;

	public Integer createSearchIndex(Integer siteId, Integer channelId, Date startDate, Date endDate, Integer startId, Integer max, boolean delete) throws IOException {
		String number = "";
		if (channelId != null) {
			Channel c = this.channelService.findById(channelId);
			if (c != null) {
				number = c.getNumber();
			}
		}
		if (delete) {
			try {
				Query query = LuceneCommon.createQuery(null, null, siteId, null, channelId, startDate, endDate);
				this.nrtManager.deleteDocuments(query);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		Integer docId = this.dao.index(this.nrtManager, siteId, number, startDate, endDate, startId, max);
		return docId;
	}

	public void deleteSearchIndex(Integer siteId, Integer channelId) {
		try {
			Query query = LuceneCommon.createQuery(null, null, siteId, null, channelId, null, null);
			this.nrtManager.deleteDocuments(query);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public void updateChannel(Integer siteId, Integer channelId) {
		try {
			Query query = LuceneCommon.createQuery(null, null, siteId, null, channelId, null, null);
			this.nrtManager.deleteDocuments(query);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
			this.dao.index(this.nrtManager, siteId, getTreeNumber(channelId), null, null, null, Integer.valueOf(1000));
		} catch (CorruptIndexException e) {
			System.out.println("生成全文检索异常!");
		} catch (IOException e) {
			System.out.println("生成全文检索异常!");
		}
	}

	public void addDoc(Article doc) {
		this.nrtManager.addDoc(LuceneCommon.createDoc(doc));
	}

	public void updateDoc(Article doc) {
		this.nrtManager.updateDocument(LuceneCommon.term(doc.getId()), LuceneCommon.createDoc(doc));
	}

	public void deleteDoc(Integer docId) {
		this.nrtManager.deleteDocuments(LuceneCommon.term(docId));
	}

	private String getTreeNumber(Integer cId) {
		if (cId == null) {
			return null;
		}
		Channel c = this.channelService.findById(cId);
		if (c != null) {
			return c.getNumber();
		}
		return null;
	}

	@Autowired
	public void setDao(LuceneDocDao dao) {
		this.dao = dao;
	}

	@Autowired
	public void setNrtManager(NRTManagerService nrtManager) {
		this.nrtManager = nrtManager;
	}

	@Autowired
	public void setChannelService(ChannelService channelService) {
		this.channelService = channelService;
	}
}

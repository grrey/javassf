package com.portal.datacenter.lucene;

import com.javassf.basic.plugin.springmvc.RealPathResolver;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.NRTManager;
import org.apache.lucene.search.NRTManager.TrackingIndexWriter;
import org.apache.lucene.search.NRTManagerReopenThread;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.SearcherFactory;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.DisposableBean;

public class NRTManagerServiceImpl implements NRTManagerService, DisposableBean {
	private IndexWriter indexWriter;
	private NRTManager nrtManager;
	private NRTManager.TrackingIndexWriter trackingIndexWriter;
	private NRTManagerReopenThread reopenThread;
	private Directory dir;

	public NRTManagerServiceImpl(RealPathResolver realPathResolver) throws IOException {
		try {
			this.dir = FSDirectory.open(new File(realPathResolver.get("/WEB-INF/lucene")));
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
			IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_36, analyzer);
			this.indexWriter = new IndexWriter(this.dir, conf);
			SearcherFactory searcherFactory = new SearcherFactory();
			this.trackingIndexWriter = new NRTManager.TrackingIndexWriter(this.indexWriter);
			this.nrtManager = new NRTManager(this.trackingIndexWriter, searcherFactory, true);
			this.reopenThread = new NRTManagerReopenThread(this.nrtManager, 5.0D, 0.1D);
			this.reopenThread.setName("nrt reopen thread");
			this.reopenThread.setDaemon(true);
			this.reopenThread.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addDoc(Document doc) {
		try {
			this.trackingIndexWriter.addDocument(doc);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addDocuments(Collection<Document> documents) {
		try {
			this.trackingIndexWriter.addDocuments(documents);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateDocument(Term term, Document document) {
		try {
			this.trackingIndexWriter.updateDocument(term, document);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteDocuments(Term term) {
		try {
			this.trackingIndexWriter.deleteDocuments(term);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteDocuments(Query query) {
		try {
			this.trackingIndexWriter.deleteDocuments(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void commit() {
		try {
			this.indexWriter.commit();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void destroy() throws Exception {
		if (this.reopenThread != null) {
			this.reopenThread.close();
		}
		if (this.nrtManager != null) {
			this.nrtManager.close();
		}
		if (this.indexWriter != null)
			this.indexWriter.close();
	}

	public IndexWriter getIndexWriter() {
		return this.indexWriter;
	}

	public NRTManager getNrtManager() {
		return this.nrtManager;
	}

	public NRTManager.TrackingIndexWriter getTrackingIndexWriter() {
		return this.trackingIndexWriter;
	}

	public NRTManagerReopenThread getReopenThread() {
		return this.reopenThread;
	}

	public Directory getDir() {
		return this.dir;
	}
}

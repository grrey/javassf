package com.portal.datacenter.lucene;

import java.util.Collection;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.NRTManager;
import org.apache.lucene.search.Query;

public abstract interface NRTManagerService {
	public abstract void addDoc(Document paramDocument);

	public abstract void addDocuments(Collection<Document> paramCollection);

	public abstract void updateDocument(Term paramTerm, Document paramDocument);

	public abstract void deleteDocuments(Term paramTerm);

	public abstract void deleteDocuments(Query paramQuery);

	public abstract NRTManager getNrtManager();
}
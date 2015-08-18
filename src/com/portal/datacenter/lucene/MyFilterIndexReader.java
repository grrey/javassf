package com.portal.datacenter.lucene;

import java.io.IOException;
import java.util.List;
import org.apache.lucene.index.FilterIndexReader;
import org.apache.lucene.index.FilterIndexReader.FilterTermDocs;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermDocs;
import org.apache.lucene.util.OpenBitSet;

public class MyFilterIndexReader extends FilterIndexReader
{
  OpenBitSet dels;

  public MyFilterIndexReader(IndexReader in)
  {
    super(in);
    this.dels = new OpenBitSet(in.maxDoc());
  }

  public MyFilterIndexReader(IndexReader in, List<String> idToDelete) throws IOException
  {
    super(in);
    this.dels = new OpenBitSet(in.maxDoc());
    for (String id : idToDelete) {
      TermDocs td = in.termDocs(new Term("id", id));
      if (td.next())
        this.dels.set(td.doc());
    }
  }

  public int numDocs()
  {
    return this.in.numDocs() - (int)this.dels.cardinality();
  }

  public TermDocs termDocs(Term term) throws IOException {
    return new FilterIndexReader.FilterTermDocs(this.in.termDocs(term))
    {
      public boolean next()
        throws IOException
      {
        boolean res;
        while ((res = super.next()))
        {
         // boolean res;
          if (!MyFilterIndexReader.this.dels.get(doc())) {
            break;
          }
        }
        return res;
      } } ;
  }

  public TermDocs termDocs() throws IOException {
    return new FilterIndexReader.FilterTermDocs(this.in.termDocs())
    {
      public boolean next()
        throws IOException
      {
        boolean res;
        while ((res = super.next()))
        {
          //boolean res;
          if (!MyFilterIndexReader.this.dels.get(doc())) {
            break;
          }
        }
        return res;
      }
    };
  }
}
package com.portal.datacenter.lucene;

import java.io.IOException;
import java.util.Date;
import org.apache.lucene.index.CorruptIndexException;

public abstract interface LuceneDocDao
{
  public abstract Integer index(NRTManagerService paramNRTManagerService, Integer paramInteger1, String paramString, Date paramDate1, Date paramDate2, Integer paramInteger2, Integer paramInteger3)
    throws CorruptIndexException, IOException;
}

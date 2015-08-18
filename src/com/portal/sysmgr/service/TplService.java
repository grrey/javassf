package com.portal.sysmgr.service;

import com.portal.sysmgr.utils.Tpl;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public abstract interface TplService
{
  public abstract List<? extends Tpl> getListByPrefix(String paramString);

  public abstract List<String> getNameListByPrefix(String paramString);

  public abstract List<? extends Tpl> getChild(String paramString1, String paramString2);

  public abstract List<Tpl> getDirChild(String paramString1, String paramString2);

  public abstract List<Tpl> getFileChild(String paramString1, String paramString2);

  public abstract void save(String paramString1, String paramString2, boolean paramBoolean);

  public abstract void save(String paramString, MultipartFile paramMultipartFile);

  public abstract Tpl get(String paramString1, String paramString2);

  public abstract void update(String paramString1, String paramString2);

  public abstract void rename(String paramString1, String paramString2);

  public abstract int delete(String paramString, String[] paramArrayOfString);
}


 
 
 
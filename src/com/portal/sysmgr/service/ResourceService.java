package com.portal.sysmgr.service;

import com.javassf.basic.file.FileWrap;
import com.portal.sysmgr.entity.Site;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public abstract interface ResourceService
{
  public abstract List<FileWrap> listFile(String paramString1, String paramString2, boolean paramBoolean);

  public abstract void saveFile(String paramString, MultipartFile paramMultipartFile)
    throws IllegalStateException, IOException;

  public abstract boolean createDir(String paramString1, String paramString2);

  public abstract void createFile(String paramString1, String paramString2, String paramString3)
    throws IOException;

  public abstract String readFile(String paramString)
    throws IOException;

  public abstract void updateFile(String paramString1, String paramString2)
    throws IOException;

  public abstract void rename(String paramString1, String paramString2);

  public abstract int delete(String paramString, String[] paramArrayOfString);

  public abstract void copyTplAndRes(Site paramSite1, Site paramSite2)
    throws IOException;

  public abstract void delTplAndRes(Site paramSite)
    throws IOException;

  public abstract String[] getStyles(String paramString);
}


 
 
 
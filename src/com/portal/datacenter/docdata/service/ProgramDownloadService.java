package com.portal.datacenter.docdata.service;

import com.portal.datacenter.docdata.entity.ProgramDownload;
import org.springframework.data.domain.Page;

public abstract interface ProgramDownloadService {
	public abstract Page<ProgramDownload> getPage(int paramInt1, int paramInt2);

	public abstract ProgramDownload findUnique();

	public abstract ProgramDownload findById(Integer paramInteger);

	public abstract ProgramDownload save();

	public abstract ProgramDownload updateCount();

	public abstract ProgramDownload update(ProgramDownload paramProgramDownload);

	public abstract ProgramDownload deleteById(Integer paramInteger);

	public abstract ProgramDownload[] deleteByIds(Integer[] paramArrayOfInteger);
}

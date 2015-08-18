package com.portal.datacenter.commdata.service;

import com.portal.datacenter.commdata.entity.Metier;
import java.util.List;
import org.springframework.data.domain.Page;

public abstract interface MetierService {
	public abstract Page<Metier> getPage(String paramString, int paramInt1, int paramInt2);

	public abstract List<Metier> getMetierList(Integer paramInteger);

	public abstract List<Metier> getMetierChild(Integer paramInteger);

	public abstract Metier findByCode(String paramString);

	public abstract Metier findById(Integer paramInteger);

	public abstract Metier save(Metier paramMetier, Integer paramInteger);

	public abstract Metier update(Metier paramMetier, Integer paramInteger);

	public abstract Metier deleteById(Integer paramInteger);

	public abstract Metier[] deleteByIds(Integer[] paramArrayOfInteger);
}

package com.portal.datacenter.commdata.service;

import com.portal.datacenter.commdata.entity.Industry;
import java.util.List;
import org.springframework.data.domain.Page;

public abstract interface IndustryService {
	public abstract Page<Industry> getPage(String paramString, int paramInt1, int paramInt2);

	public abstract List<Industry> getIndustryList(Integer paramInteger);

	public abstract List<Industry> getIndustryChild(Integer paramInteger);

	public abstract Industry findByCode(String paramString);

	public abstract Industry findById(Integer paramInteger);

	public abstract Industry save(Industry paramIndustry, Integer paramInteger);

	public abstract Industry update(Industry paramIndustry, Integer paramInteger);

	public abstract Industry deleteById(Integer paramInteger);

	public abstract Industry[] deleteByIds(Integer[] paramArrayOfInteger);
}

package com.portal.datacenter.commdata.service;

import com.portal.datacenter.commdata.entity.EconomyType;
import java.util.List;
import org.springframework.data.domain.Page;

public abstract interface EconomyTypeService {
	public abstract Page<EconomyType> getPage(int paramInt1, int paramInt2);

	public abstract List<EconomyType> getEconomyTypeList();

	public abstract EconomyType findByCode(String paramString);

	public abstract EconomyType findById(Integer paramInteger);

	public abstract EconomyType save(EconomyType paramEconomyType);

	public abstract EconomyType update(EconomyType paramEconomyType);

	public abstract EconomyType deleteById(Integer paramInteger);

	public abstract EconomyType[] deleteByIds(Integer[] paramArrayOfInteger);
}

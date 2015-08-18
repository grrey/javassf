package com.portal.datacenter.commdata.service;

import com.portal.datacenter.commdata.entity.UnitType;
import org.springframework.data.domain.Page;

public abstract interface UnitTypeService {
	public abstract Page<UnitType> getPage(int paramInt1, int paramInt2);

	public abstract UnitType findById(Integer paramInteger);

	public abstract UnitType save(UnitType paramUnitType);

	public abstract UnitType update(UnitType paramUnitType);

	public abstract UnitType deleteById(Integer paramInteger);

	public abstract UnitType[] deleteByIds(Integer[] paramArrayOfInteger);
}

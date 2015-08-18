package com.portal.datacenter.commdata.service;

import com.portal.datacenter.commdata.entity.Specialty;
import java.util.List;
import org.springframework.data.domain.Page;

public abstract interface SpecialtyService {
	public abstract Page<Specialty> getPage(String paramString, int paramInt1, int paramInt2);

	public abstract List<Specialty> getSpecialtyList(Integer paramInteger);

	public abstract List<Specialty> getSpecialtyChild(Integer paramInteger);

	public abstract Specialty findByCode(String paramString);

	public abstract Specialty findById(Integer paramInteger);

	public abstract Specialty save(Specialty paramSpecialty, Integer paramInteger);

	public abstract Specialty update(Specialty paramSpecialty, Integer paramInteger);

	public abstract Specialty deleteById(Integer paramInteger);

	public abstract Specialty[] deleteByIds(Integer[] paramArrayOfInteger);
}

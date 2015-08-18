package com.portal.datacenter.operatedata.service;

import com.portal.datacenter.operatedata.entity.Log;
import com.portal.usermgr.entity.User;
import javax.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;

public abstract interface LogService {
	public abstract Page<Log> getPage(Integer paramInteger1, Integer paramInteger2, String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2);

	public abstract Log findById(Integer paramInteger);

	public abstract Log operating(HttpServletRequest paramHttpServletRequest, String paramString1, String paramString2);

	public abstract Log loginFailure(HttpServletRequest paramHttpServletRequest, String paramString1, String paramString2);

	public abstract Log loginSuccess(HttpServletRequest paramHttpServletRequest, User paramUser, String paramString);

	public abstract Log save(Log paramLog);

	public abstract Log deleteById(Integer paramInteger);

	public abstract Log[] deleteByIds(Integer[] paramArrayOfInteger);

	public abstract int deleteBatch(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3);
}

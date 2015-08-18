package com.javassf.basic.hibernate4;

import org.springframework.data.domain.Page;
/**
 * 
 *Dao接口类
 *
 * @param <T>
 */
public abstract interface QueryDao<T>
{
  public abstract T findById(Integer paramInteger);

  public abstract T save(T paramT);

  public abstract T update(T paramT);

  public abstract T deleteById(Integer paramInteger);

  public abstract Page<T> getPage(int paramInt1, int paramInt2);
}
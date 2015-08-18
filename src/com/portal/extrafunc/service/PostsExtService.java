package com.portal.extrafunc.service;

import com.portal.extrafunc.entity.Posts;
import com.portal.extrafunc.entity.PostsExt;
import org.springframework.data.domain.Page;

public abstract interface PostsExtService
{
  public abstract Page<PostsExt> getPage(int paramInt1, int paramInt2);

  public abstract PostsExt findById(Integer paramInteger);

  public abstract PostsExt save(String paramString, Posts paramPosts);

  public abstract PostsExt update(Integer paramInteger1, Integer paramInteger2, String paramString);

  public abstract PostsExt deleteById(Integer paramInteger);

  public abstract PostsExt[] deleteByIds(Integer[] paramArrayOfInteger);
}


 
 
 
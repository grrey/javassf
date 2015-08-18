package com.portal.extrafunc.dao;

import com.javassf.basic.hibernate4.QueryDao;
import com.portal.extrafunc.entity.Posts;
import org.springframework.data.domain.Page;

public abstract interface PostsDao extends QueryDao<Posts>
{
  public abstract Page<Posts> getPage(int paramInt1, int paramInt2);

  public abstract Page<Posts> getPostsPageForTag(Integer paramInteger, int paramInt1, int paramInt2);

  public abstract Posts getPostsOneFloor(Integer paramInteger);

  public abstract Posts getLastPostsByUserAndForum(Integer paramInteger1, Integer paramInteger2);

  public abstract Posts getLastPostsByUserAndTheme(Integer paramInteger1, Integer paramInteger2);

  public abstract Integer getAllPostCount(Integer paramInteger);

  public abstract Integer getFloorByTheme(Integer paramInteger);

  public abstract int updatePostsBySite(Integer paramInteger);

  public abstract int updatePostsByCategory(Integer paramInteger);

  public abstract int updatePostsByForum(Integer paramInteger);

  public abstract int updatePostsByTheme(Integer paramInteger);

  public abstract int updatePostsByUser(Integer paramInteger);

  public abstract int deletePosts(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4, Integer paramInteger5);
}


 
 
 
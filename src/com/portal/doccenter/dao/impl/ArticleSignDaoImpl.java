 package com.portal.doccenter.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.doccenter.dao.ArticleSignDao;
 import com.portal.doccenter.entity.ArticleSign;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class ArticleSignDaoImpl extends QueryDaoImpl<ArticleSign, Integer>
   implements ArticleSignDao
 {
   protected Class<ArticleSign> getEntityClass()
   {
     return ArticleSign.class;
   }
 }


 
 
 
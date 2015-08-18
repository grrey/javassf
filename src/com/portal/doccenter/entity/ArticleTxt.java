 package com.portal.doccenter.entity;
 
 import java.io.Serializable;
 import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.GeneratedValue;
 import javax.persistence.Id;
 import javax.persistence.Lob;
 import javax.persistence.OneToOne;
 import javax.persistence.PrimaryKeyJoinColumn;
 import javax.persistence.Table;
 import javax.persistence.Transient;
 import org.apache.commons.lang.StringUtils;
 import org.hibernate.annotations.GenericGenerator;
 
 @Entity
 @Table(name="tq_article_txt")
 public class ArticleTxt
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   public static String PAGE_STRING = "<hr class=\"ke-pagebreak\" style=\"page-break-after:always;\" />";
   private Integer id;
   private String txt;
   private Article article;
 
   @Transient
   public int getTxtCount()
   {
     String txt = getTxt();
     if (StringUtils.isBlank(txt)) {
       return 1;
     }
     return StringUtils.countMatches(txt, PAGE_STRING) + 1;
   }
 
   @Transient
   public String getTxtByNo(int pageNo) {
     String txt = getTxt();
     if ((StringUtils.isBlank(txt)) || (pageNo < 1)) {
       return null;
     }
     int start = 0; int end = 0;
     for (int i = 0; i < pageNo; i++)
     {
       if (i != 0) {
         start = txt.indexOf(PAGE_STRING, end);
         if (start == -1) {
           return null;
         }
         start += PAGE_STRING.length();
       }
 
       end = txt.indexOf(PAGE_STRING, start);
       if (end == -1) {
         end = txt.length();
       }
     }
     return txt.substring(start, end);
   }
   @Transient
   public String getTitleByNo(int pageNo) {
     if (pageNo < 1) {
       return null;
     }
     String title = getArticle().getTitle();
     if (pageNo == 1) {
       return title;
     }
     String txt = getTxt();
     int start = 0; int end = 0;
     for (int i = 1; i < pageNo; i++) {
       start = txt.indexOf(PAGE_STRING, end);
       if (start == -1) {
         return title;
       }
       start += PAGE_STRING.length();
 
       end = txt.indexOf(PAGE_STRING, start);
       if (end == -1) {
         return title;
       }
     }
     String result = txt.substring(start, end);
     if (!StringUtils.isBlank(result)) {
       return result;
     }
     return title;
   }
 
   public void init()
   {
     blankToNull();
   }
 
   public void blankToNull() {
     if (StringUtils.isBlank(getTxt()))
       setTxt(null);
   }
 
   @Transient
   public boolean isAllBlank()
   {
     return StringUtils.isBlank(getTxt());
   }
 
   @Id
   @Column(name="article_id", unique=true, nullable=false)
   @GenericGenerator(name="copy", strategy="foreign", parameters={@org.hibernate.annotations.Parameter(name="property", value="article")})
   @GeneratedValue(generator="copy")
   public Integer getId()
   {
     return this.id;
   }
 
   public void setId(Integer id) {
     this.id = id;
   }
   @Lob
   @Column(name="txt", nullable=true)
   public String getTxt() { return this.txt; }
 
   public void setTxt(String txt)
   {
     this.txt = txt;
   }
   @OneToOne
   @PrimaryKeyJoinColumn
   public Article getArticle() { return this.article; }
 
   public void setArticle(Article article)
   {
     this.article = article;
   }
 }


 
 
 
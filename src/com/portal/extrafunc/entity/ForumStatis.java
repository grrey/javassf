 package com.portal.extrafunc.entity;
 
 import com.portal.sysmgr.entity.Site;
 import java.io.Serializable;
 import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.GeneratedValue;
 import javax.persistence.Id;
 import javax.persistence.OneToOne;
 import javax.persistence.PrimaryKeyJoinColumn;
 import javax.persistence.Table;
 import org.hibernate.annotations.GenericGenerator;
 
 @Entity
 @Table(name="tq_forum_statis")
 public class ForumStatis
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private Integer id;
   private Integer postsToday;
   private Integer postsYestoday;
   private Integer highestDay;
   private Integer postsTotal;
   private Site site;
 
   @Id
   @Column(name="statis_id", unique=true, nullable=false)
   @GenericGenerator(name="copy", strategy="foreign", parameters={@org.hibernate.annotations.Parameter(name="property", value="site")})
   @GeneratedValue(generator="copy")
   public Integer getId()
   {
     return this.id;
   }
 
   public void setId(Integer id) {
     this.id = id;
   }
   @Column(name="posts_today", nullable=false, length=11)
   public Integer getPostsToday() {
     return this.postsToday;
   }
 
   public void setPostsToday(Integer postsToday) {
     this.postsToday = postsToday;
   }
   @Column(name="posts_yestoday", nullable=false, length=11)
   public Integer getPostsYestoday() {
     return this.postsYestoday;
   }
 
   public void setPostsYestoday(Integer postsYestoday) {
     this.postsYestoday = postsYestoday;
   }
   @Column(name="highest_day", nullable=false, length=11)
   public Integer getHighestDay() {
     return this.highestDay;
   }
 
   public void setHighestDay(Integer highestDay) {
     this.highestDay = highestDay;
   }
   @Column(name="posts_total", nullable=false, length=11)
   public Integer getPostsTotal() {
     return this.postsTotal;
   }
 
   public void setPostsTotal(Integer postsTotal) {
     this.postsTotal = postsTotal;
   }
   @OneToOne
   @PrimaryKeyJoinColumn
   public Site getSite() { return this.site; }
 
   public void setSite(Site site)
   {
     this.site = site;
   }
 }


 
 
 
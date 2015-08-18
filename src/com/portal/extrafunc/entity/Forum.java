 package com.portal.extrafunc.entity;
 
 import com.portal.sysmgr.entity.Site;
 import com.portal.usermgr.entity.User;
 import java.io.Serializable;
 import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.FetchType;
 import javax.persistence.GeneratedValue;
 import javax.persistence.GenerationType;
 import javax.persistence.Id;
 import javax.persistence.JoinColumn;
 import javax.persistence.ManyToOne;
 import javax.persistence.OneToOne;
 import javax.persistence.PrimaryKeyJoinColumn;
 import javax.persistence.Table;
 import javax.persistence.TableGenerator;
 import javax.persistence.Transient;
 
 @Entity
 @Table(name="tq_forum")
 public class Forum
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   public static final String HTEME_LIST = "themeList";
   private Integer id;
   private String name;
   private Integer priority;
   private Integer themeTotal;
   private Integer replyTotal;
   private Integer themeToday;
   private Integer replyToday;
   private String moderators;
   private ForumExt ext;
   private Theme lastTheme;
   private Site site;
   private Category category;
   private User lastReplyer;
 
   public void init()
   {
     if (getPriority() == null) {
       setPriority(Integer.valueOf(10));
     }
     if (getThemeTotal() == null) {
       setThemeTotal(Integer.valueOf(0));
     }
     if (getThemeToday() == null) {
       setThemeToday(Integer.valueOf(0));
     }
     if (getReplyTotal() == null) {
       setReplyTotal(Integer.valueOf(0));
     }
     if (getReplyToday() == null)
       setReplyToday(Integer.valueOf(0));
   }
 
   @Transient
   public String getUrl() {
     StringBuilder sb = new StringBuilder(getSite().getUrl());
     sb.append("themeList");
     sb.append("-");
     sb.append(getId());
     sb.append(".jsp");
     return sb.toString();
   }
   @Transient
   public String getUrl(Integer pn) {
     StringBuilder sb = new StringBuilder(getSite().getUrl());
     sb.append("themeList");
     sb.append("-");
     sb.append(getId());
     sb.append("_");
     sb.append(pn);
     sb.append(".jsp");
     return sb.toString();
   }
   @Transient
   public String getKeywords() {
     ForumExt ext = getExt();
     if (ext != null) {
       return ext.getKeywords();
     }
     return null;
   }
   @Transient
   public String getDescription() {
     ForumExt ext = getExt();
     if (ext != null) {
       return ext.getDescription();
     }
     return null;
   }
   @Transient
   public String getRule() {
     ForumExt ext = getExt();
     if (ext != null) {
       return ext.getRule();
     }
     return null;
   }
   @Transient
   public String getTplContent() {
     ForumExt ext = getExt();
     if (ext != null) {
       return ext.getTplContent();
     }
     return null;
   }
 
   @Id
   @Column(name="forum_id", unique=true, nullable=false)
   @TableGenerator(name="tg_forum", pkColumnValue="tq_forum", table="tq_gen_table", pkColumnName="tg_gen_name", valueColumnName="tq_gen_value", initialValue=1, allocationSize=1)
   @GeneratedValue(strategy=GenerationType.TABLE, generator="tg_forum")
   public Integer getId()
   {
     return this.id;
   }
 
   public void setId(Integer id) {
     this.id = id;
   }
   @Column(name="name", nullable=false, length=50)
   public String getName() {
     return this.name;
   }
 
   public void setName(String name) {
     this.name = name;
   }
   @Column(name="priority", nullable=false, length=10)
   public Integer getPriority() {
     return this.priority;
   }
 
   public void setPriority(Integer priority) {
     this.priority = priority;
   }
   @Column(name="theme_total", nullable=false, length=10)
   public Integer getThemeTotal() {
     return this.themeTotal;
   }
 
   public void setThemeTotal(Integer themeTotal) {
     this.themeTotal = themeTotal;
   }
   @Column(name="reply_total", nullable=false, length=10)
   public Integer getReplyTotal() {
     return this.replyTotal;
   }
 
   public void setReplyTotal(Integer replyTotal) {
     this.replyTotal = replyTotal;
   }
   @Column(name="theme_today", nullable=false, length=10)
   public Integer getThemeToday() {
     return this.themeToday;
   }
 
   public void setThemeToday(Integer themeToday) {
     this.themeToday = themeToday;
   }
   @Column(name="reply_today", nullable=false, length=10)
   public Integer getReplyToday() {
     return this.replyToday;
   }
 
   public void setReplyToday(Integer replyToday) {
     this.replyToday = replyToday;
   }
   @Column(name="moderators", nullable=true, length=50)
   public String getModerators() {
     return this.moderators;
   }
 
   public void setModerators(String moderators) {
     this.moderators = moderators;
   }
   @OneToOne(cascade={javax.persistence.CascadeType.REMOVE}, fetch=FetchType.LAZY)
   @PrimaryKeyJoinColumn
   public ForumExt getExt() { return this.ext; }
 
   public void setExt(ForumExt ext)
   {
     this.ext = ext;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="last_theme_id", nullable=true)
   public Theme getLastTheme() { return this.lastTheme; }
 
   public void setLastTheme(Theme lastTheme)
   {
     this.lastTheme = lastTheme;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="site_id", nullable=false)
   public Site getSite() { return this.site; }
 
   public void setSite(Site site)
   {
     this.site = site;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="category_id", nullable=false)
   public Category getCategory() { return this.category; }
 
   public void setCategory(Category category)
   {
     this.category = category;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="last_replyer_id", nullable=true)
   public User getLastReplyer() { return this.lastReplyer; }
 
   public void setLastReplyer(User lastReplyer)
   {
     this.lastReplyer = lastReplyer;
   }
 }


 
 
 
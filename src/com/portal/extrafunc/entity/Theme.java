 package com.portal.extrafunc.entity;
 
 import com.portal.sysmgr.entity.Site;
 import com.portal.usermgr.entity.User;
 import java.io.Serializable;
 import java.sql.Timestamp;
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
 import javax.persistence.Temporal;
 import javax.persistence.TemporalType;
 import javax.persistence.Transient;
 
 @Entity
 @Table(name="tq_theme")
 public class Theme
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   public static final int TOP_THREE = 3;
   public static final int TOP_TWO = 2;
   public static final int TOP_ONE = 1;
   public static final int NORMAL = 0;
   public static final int SHIELD = -1;
   public static final int DELETE = -2;
   public static final String HTEME_DETAIL = "themeDetail";
   private Integer id;
   private String title;
   private Integer viewsCount;
   private Integer replyCount;
   private Boolean lock;
   private Boolean essena;
   private Boolean bold;
   private Boolean italic;
   private String color;
   private java.util.Date topTime;
   private java.util.Date essenaTime;
   private java.util.Date lockTime;
   private java.util.Date lightTime;
   private Integer status;
   private Boolean affix;
   private Boolean img;
   private Boolean moderReply;
   private java.util.Date lastReplyTime;
   private java.util.Date createTime;
   private ThemeTxt txt;
   private User creater;
   private Forum forum;
   private Site site;
   private User lastReplyer;
 
   public void init()
   {
     if (getViewsCount() == null) {
       setViewsCount(Integer.valueOf(0));
     }
     if (getReplyCount() == null) {
       setReplyCount(Integer.valueOf(0));
     }
     if (getLock() == null) {
       setLock(Boolean.valueOf(false));
     }
     if (getBold() == null) {
       setBold(Boolean.valueOf(false));
     }
     if (getEssena() == null) {
       setEssena(Boolean.valueOf(false));
     }
     if (getItalic() == null) {
       setItalic(Boolean.valueOf(false));
     }
     if (getModerReply() == null) {
       setModerReply(Boolean.valueOf(false));
     }
     if (getStatus() == null) {
       setStatus(Integer.valueOf(0));
     }
     if (getCreateTime() == null) {
       setCreateTime(new Timestamp(System.currentTimeMillis()));
     }
     if (getLastReplyTime() == null)
       setLastReplyTime(new Timestamp(System.currentTimeMillis()));
   }
 
   @Transient
   public String getUrl() {
     StringBuilder sb = new StringBuilder(getSite().getUrl());
     sb.append("themeDetail");
     sb.append("-");
     sb.append(getId());
     sb.append(".jsp");
     return sb.toString();
   }
   @Transient
   public String getUrl(Integer pn) {
     StringBuilder sb = new StringBuilder(getSite().getUrl());
     sb.append("themeDetail");
     sb.append("-");
     sb.append(getId());
     sb.append("_");
     sb.append(pn);
     sb.append(".jsp");
     return sb.toString();
   }
 
   @Transient
   public boolean getTop() {
     return getStatus().intValue() > 0;
   }
 
   @Transient
   public boolean getCheckTopTime()
   {
     if (getTopTime() == null) {
       return false;
     }
     java.sql.Date d = new java.sql.Date(System.currentTimeMillis());
     java.sql.Date d1 = new java.sql.Date(getTopTime().getTime());
 
     return d.toString().equals(d1.toString());
   }
 
   @Transient
   public boolean getCheckLightTime()
   {
     if (getLightTime() == null) {
       return false;
     }
     java.sql.Date d = new java.sql.Date(System.currentTimeMillis());
     java.sql.Date d1 = new java.sql.Date(getLightTime().getTime());
 
     return d.toString().equals(d1.toString());
   }
 
   @Transient
   public boolean getCheckLockTime()
   {
     if (getLockTime() == null) {
       return false;
     }
     java.sql.Date d = new java.sql.Date(System.currentTimeMillis());
     java.sql.Date d1 = new java.sql.Date(getLockTime().getTime());
 
     return d.toString().equals(d1.toString());
   }
 
   @Transient
   public boolean getNewTheme()
   {
     java.sql.Date d = new java.sql.Date(System.currentTimeMillis());
     java.sql.Date d1 = new java.sql.Date(getCreateTime().getTime());
 
     return d.toString().equals(d1.toString());
   }
 
   @Transient
   public String getReplyTimeString()
   {
     java.util.Date d = new java.util.Date();
     long s = d.getTime() - getLastReplyTime().getTime();
     s /= 1000L;
     if (s < 60L)
       return s + "秒前";
     if ((s > 60L) && (s < 3600L)) {
       s /= 60L;
       return s + "分钟前";
     }if ((s > 3600L) && (s < 86400L)) {
       s /= 3600L;
       return s + "小时前";
     }
     return getLastReplyTime().toString().substring(0, 
       getLastReplyTime().toString().length() - 2);
   }
 
   @Transient
   public String getReplyUser() {
     ThemeTxt txt = getTxt();
     if (txt != null) {
       return txt.getContent();
     }
     return ",";
   }
 
   @Id
   @Column(name="theme_id", unique=true, nullable=false)
   @TableGenerator(name="tg_theme", pkColumnValue="tq_theme", table="tq_gen_table", pkColumnName="tg_gen_name", valueColumnName="tq_gen_value", initialValue=1, allocationSize=1)
   @GeneratedValue(strategy=GenerationType.TABLE, generator="tg_theme")
   public Integer getId()
   {
     return this.id;
   }
 
   public void setId(Integer id) {
     this.id = id;
   }
   @Column(name="title", nullable=false, length=150)
   public String getTitle() {
     return this.title;
   }
 
   public void setTitle(String title) {
     this.title = title;
   }
   @Column(name="views_count", nullable=false, length=10)
   public Integer getViewsCount() {
     return this.viewsCount;
   }
 
   public void setViewsCount(Integer viewsCount) {
     this.viewsCount = viewsCount;
   }
   @Column(name="reply_count", nullable=false, length=10)
   public Integer getReplyCount() {
     return this.replyCount;
   }
 
   public void setReplyCount(Integer replyCount) {
     this.replyCount = replyCount;
   }
   @Column(name="is_lock", nullable=false, length=1)
   public Boolean getLock() {
     return this.lock;
   }
 
   public void setLock(Boolean lock) {
     this.lock = lock;
   }
   @Column(name="is_essena", nullable=true, length=1)
   public Boolean getEssena() {
     return this.essena;
   }
 
   public void setEssena(Boolean essena) {
     this.essena = essena;
   }
   @Column(name="is_bold", nullable=true, length=1)
   public Boolean getBold() {
     return this.bold;
   }
 
   public void setBold(Boolean bold) {
     this.bold = bold;
   }
   @Column(name="is_italic", nullable=true, length=1)
   public Boolean getItalic() {
     return this.italic;
   }
 
   public void setItalic(Boolean italic) {
     this.italic = italic;
   }
   @Column(name="color", nullable=true, length=50)
   public String getColor() {
     return this.color;
   }
 
   public void setColor(String color) {
     this.color = color;
   }
   @Temporal(TemporalType.DATE)
   @Column(name="top_time", nullable=true, length=19)
   public java.util.Date getTopTime() { return this.topTime; }
 
   public void setTopTime(java.util.Date topTime)
   {
     this.topTime = topTime;
   }
   @Temporal(TemporalType.DATE)
   @Column(name="essena_time", nullable=true, length=19)
   public java.util.Date getEssenaTime() { return this.essenaTime; }
 
   public void setEssenaTime(java.util.Date essenaTime)
   {
     this.essenaTime = essenaTime;
   }
   @Temporal(TemporalType.DATE)
   @Column(name="lock_time", nullable=true, length=19)
   public java.util.Date getLockTime() { return this.lockTime; }
 
   public void setLockTime(java.util.Date lockTime)
   {
     this.lockTime = lockTime;
   }
   @Temporal(TemporalType.DATE)
   @Column(name="light_time", nullable=true, length=19)
   public java.util.Date getLightTime() { return this.lightTime; }
 
   public void setLightTime(java.util.Date lightTime)
   {
     this.lightTime = lightTime;
   }
   @Column(name="status", nullable=false, length=10)
   public Integer getStatus() {
     return this.status;
   }
 
   public void setStatus(Integer status) {
     this.status = status;
   }
   @Column(name="is_affix", nullable=false, length=1)
   public Boolean getAffix() {
     return this.affix;
   }
 
   public void setAffix(Boolean affix) {
     this.affix = affix;
   }
   @Column(name="is_img", nullable=false, length=1)
   public Boolean getImg() {
     return this.img;
   }
 
   public void setImg(Boolean img) {
     this.img = img;
   }
   @Column(name="is_moder_reply", nullable=false, length=1)
   public Boolean getModerReply() {
     return this.moderReply;
   }
 
   public void setModerReply(Boolean moderReply) {
     this.moderReply = moderReply;
   }
   @Temporal(TemporalType.TIMESTAMP)
   @Column(name="last_reply_time", nullable=true, length=19)
   public java.util.Date getLastReplyTime() { return this.lastReplyTime; }
 
   public void setLastReplyTime(java.util.Date lastReplyTime)
   {
     this.lastReplyTime = lastReplyTime;
   }
   @Temporal(TemporalType.TIMESTAMP)
   @Column(name="create_time", nullable=false, length=19)
   public java.util.Date getCreateTime() { return this.createTime; }
 
   public void setCreateTime(java.util.Date createTime)
   {
     this.createTime = createTime;
   }
   @OneToOne(cascade={javax.persistence.CascadeType.REMOVE}, fetch=FetchType.LAZY)
   @PrimaryKeyJoinColumn
   public ThemeTxt getTxt() { return this.txt; }
 
   public void setTxt(ThemeTxt txt)
   {
     this.txt = txt;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="creater_id", nullable=true)
   public User getCreater() { return this.creater; }
 
   public void setCreater(User creater)
   {
     this.creater = creater;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="forum_id", nullable=false)
   public Forum getForum() { return this.forum; }
 
   public void setForum(Forum forum)
   {
     this.forum = forum;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="site_id", nullable=false)
   public Site getSite() { return this.site; }
 
   public void setSite(Site site)
   {
     this.site = site;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="last_replyer_id", nullable=true)
   public User getLastReplyer() { return this.lastReplyer; }
 
   public void setLastReplyer(User lastReplyer)
   {
     this.lastReplyer = lastReplyer;
   }
 }


 
 
 
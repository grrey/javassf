 package com.portal.datacenter.operatedata.entity.base;
 
 import com.portal.datacenter.operatedata.entity.Log;
 import com.portal.sysmgr.entity.Site;
 import com.portal.usermgr.entity.User;
 import java.io.Serializable;
 import java.util.Date;
 import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.FetchType;
 import javax.persistence.GeneratedValue;
 import javax.persistence.GenerationType;
 import javax.persistence.Id;
 import javax.persistence.JoinColumn;
 import javax.persistence.ManyToOne;
 import javax.persistence.Table;
 import javax.persistence.TableGenerator;
 import javax.persistence.Temporal;
 import javax.persistence.TemporalType;
 
 @Entity
 @Table(name="tq_log")
 public abstract class BaseLog
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private int hashCode = -2147483648;
   private Integer id;
   private Integer category;
   private Date time;
   private String ip;
   private String url;
   private String title;
   private String content;
   private User user;
   private Site site;
 
   public BaseLog()
   {
     initialize();
   }
 
   public BaseLog(Integer id) {
     setId(id);
     initialize();
   }
 
   public BaseLog(Integer id, Integer category, Date time)
   {
     setId(id);
     setCategory(category);
     setTime(time);
     initialize();
   }
 
   protected void initialize()
   {
   }
 
   @Id
   @Column(name="log_id", unique=true, nullable=false)
   @TableGenerator(name="tg_log", pkColumnValue="tq_log", table="tq_gen_table", pkColumnName="tg_gen_name", valueColumnName="tq_gen_value", initialValue=1, allocationSize=1)
   @GeneratedValue(strategy=GenerationType.TABLE, generator="tg_log")
   public Integer getId()
   {
     return this.id;
   }
 
   public void setId(Integer id) {
     this.id = id;
   }
   @Column(name="category", nullable=false, length=10)
   public Integer getCategory() {
     return this.category;
   }
 
   public void setCategory(Integer category) {
     this.category = category;
   }
   @Temporal(TemporalType.TIMESTAMP)
   @Column(name="log_time", nullable=false, length=19)
   public Date getTime() { return this.time; }
 
   public void setTime(Date time)
   {
     this.time = time;
   }
   @Column(name="ip", nullable=true, length=50)
   public String getIp() {
     return this.ip;
   }
 
   public void setIp(String ip) {
     this.ip = ip;
   }
   @Column(name="url", nullable=true, length=255)
   public String getUrl() {
     return this.url;
   }
 
   public void setUrl(String url) {
     this.url = url;
   }
   @Column(name="title", nullable=true, length=255)
   public String getTitle() {
     return this.title;
   }
 
   public void setTitle(String title) {
     this.title = title;
   }
   @Column(name="content", nullable=true, length=255)
   public String getContent() {
     return this.content;
   }
 
   public void setContent(String content) {
     this.content = content;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="user_id", nullable=false)
   public User getUser() { return this.user; }
 
   public void setUser(User user)
   {
     this.user = user;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="site_id", nullable=false)
   public Site getSite() { return this.site; }
 
   public void setSite(Site site)
   {
     this.site = site;
   }
 
   public boolean equals(Object obj) {
     if (obj == null)
       return false;
     if (!(obj instanceof Log)) {
       return false;
     }
     Log log = (Log)obj;
     if ((getId() == null) || (log.getId() == null)) {
       return false;
     }
     return getId().equals(log.getId());
   }
 
   public int hashCode()
   {
     if (-2147483648 == this.hashCode) {
       if (getId() == null) {
         return super.hashCode();
       }
       String hashStr = getClass().getName() + ":" + 
         getId().hashCode();
       this.hashCode = hashStr.hashCode();
     }
 
     return this.hashCode;
   }
 }

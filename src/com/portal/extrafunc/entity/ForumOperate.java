 package com.portal.extrafunc.entity;
 
 import com.portal.sysmgr.entity.Site;
 import com.portal.usermgr.entity.User;
 import java.io.Serializable;
 import java.sql.Timestamp;
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
 @Table(name="tq_forum_operate")
 public class ForumOperate
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   public static final String THEME = "THEME";
   public static final String POSTS = "POSTS";
   public static final String USER = "USER";
   private Integer id;
   private Integer target;
   private String targetType;
   private String name;
   private String reason;
   private Date operateTime;
   private User admin;
   private Site site;
 
   public void init()
   {
     if (getOperateTime() == null)
       setOperateTime(new Timestamp(System.currentTimeMillis()));
   }
 
   @Id
   @Column(name="operate_id", unique=true, nullable=false)
   @TableGenerator(name="tg_forum_operate", pkColumnValue="tq_forum_operate", table="tq_gen_table", pkColumnName="tg_gen_name", valueColumnName="tq_gen_value", initialValue=1, allocationSize=1)
   @GeneratedValue(strategy=GenerationType.TABLE, generator="tg_forum_operate")
   public Integer getId()
   {
     return this.id;
   }
 
   public void setId(Integer id) {
     this.id = id;
   }
   @Column(name="operate_target", nullable=false, length=10)
   public Integer getTarget() {
     return this.target;
   }
 
   public void setTarget(Integer target) {
     this.target = target;
   }
   @Column(name="target_type", nullable=false, length=30)
   public String getTargetType() {
     return this.targetType;
   }
 
   public void setTargetType(String targetType) {
     this.targetType = targetType;
   }
   @Column(name="name", nullable=false, length=30)
   public String getName() {
     return this.name;
   }
 
   public void setName(String name) {
     this.name = name;
   }
   @Column(name="reason", nullable=true, length=100)
   public String getReason() {
     return this.reason;
   }
 
   public void setReason(String reason) {
     this.reason = reason;
   }
   @Temporal(TemporalType.TIMESTAMP)
   @Column(name="operate_time", nullable=false, length=19)
   public Date getOperateTime() { return this.operateTime; }
 
   public void setOperateTime(Date operateTime)
   {
     this.operateTime = operateTime;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="operate_admin", nullable=false)
   public User getAdmin() { return this.admin; }
 
   public void setAdmin(User admin)
   {
     this.admin = admin;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="site_id", nullable=false)
   public Site getSite() { return this.site; }
 
   public void setSite(Site site)
   {
     this.site = site;
   }
 }


 
 
 
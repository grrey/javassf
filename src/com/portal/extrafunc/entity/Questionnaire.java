 package com.portal.extrafunc.entity;
 
 import com.portal.sysmgr.entity.Site;
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
 @Table(name="tq_questionnaire")
 public class Questionnaire
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private Integer id;
   private String name;
   private String description;
   private Integer repeateTime;
   private Boolean restrictIp;
   private Boolean needLogin;
   private Date createTime;
   private Date startTime;
   private Date endTime;
   private Boolean enable;
   private Site site;
 
   public void init()
   {
     if (getRepeateTime() == null) {
       setRepeateTime(Integer.valueOf(9999999));
     }
     if (getCreateTime() == null) {
       setCreateTime(new Timestamp(System.currentTimeMillis()));
     }
     if (getStartTime() == null) {
       setStartTime(new Timestamp(System.currentTimeMillis()));
     }
     if (getRestrictIp() == null) {
       setRestrictIp(Boolean.valueOf(false));
     }
     if (getNeedLogin() == null) {
       setNeedLogin(Boolean.valueOf(false));
     }
     if (getEnable() == null)
       setEnable(Boolean.valueOf(false));
   }
 
   public void updateinit()
   {
     if (getRepeateTime() == null) {
       setRepeateTime(Integer.valueOf(0));
     }
     if (getRestrictIp() == null) {
       setRestrictIp(Boolean.valueOf(false));
     }
     if (getNeedLogin() == null) {
       setNeedLogin(Boolean.valueOf(false));
     }
     if (getEnable() == null)
       setEnable(Boolean.valueOf(false));
   }
 
   @Id
   @Column(name="naire_id", unique=true, nullable=false)
   @TableGenerator(name="tg_questionnaire", pkColumnValue="tq_questionnaire", table="tq_gen_table", pkColumnName="tg_gen_name", valueColumnName="tq_gen_value", initialValue=1, allocationSize=1)
   @GeneratedValue(strategy=GenerationType.TABLE, generator="tg_questionnaire")
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
   @Column(name="description", nullable=true, length=300)
   public String getDescription() {
     return this.description;
   }
 
   public void setDescription(String description) {
     this.description = description;
   }
   @Column(name="repeate_time", nullable=false, length=10)
   public Integer getRepeateTime() {
     return this.repeateTime;
   }
 
   public void setRepeateTime(Integer repeateTime) {
     this.repeateTime = repeateTime;
   }
   @Column(name="is_restrict_ip", nullable=false, length=1)
   public Boolean getRestrictIp() {
     return this.restrictIp;
   }
 
   public void setRestrictIp(Boolean restrictIp) {
     this.restrictIp = restrictIp;
   }
   @Column(name="is_need_login", nullable=false, length=1)
   public Boolean getNeedLogin() {
     return this.needLogin;
   }
 
   public void setNeedLogin(Boolean needLogin) {
     this.needLogin = needLogin;
   }
   @Temporal(TemporalType.TIMESTAMP)
   @Column(name="create_time", nullable=false, length=19)
   public Date getCreateTime() { return this.createTime; }
 
   public void setCreateTime(Date createTime)
   {
     this.createTime = createTime;
   }
   @Temporal(TemporalType.DATE)
   @Column(name="start_time", nullable=false, length=10)
   public Date getStartTime() { return this.startTime; }
 
   public void setStartTime(Date startTime)
   {
     this.startTime = startTime;
   }
   @Temporal(TemporalType.DATE)
   @Column(name="end_time", nullable=true, length=10)
   public Date getEndTime() { return this.endTime; }
 
   public void setEndTime(Date endTime)
   {
     this.endTime = endTime;
   }
   @Column(name="enable", nullable=false, length=1)
   public Boolean getEnable() {
     return this.enable;
   }
 
   public void setEnable(Boolean enable) {
     this.enable = enable;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="site_id", nullable=false)
   public Site getSite() { return this.site; }
 
   public void setSite(Site site)
   {
     this.site = site;
   }
 }


 
 
 
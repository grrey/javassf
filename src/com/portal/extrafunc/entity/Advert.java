 package com.portal.extrafunc.entity;
 
 import com.portal.sysmgr.entity.Site;
 import java.io.Serializable;
 import java.sql.Time;
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
 import javax.persistence.Transient;
 
 @Entity
 @Table(name="tq_advert")
 public class Advert
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   public static final String IMG = "img";
   public static final String FLASH = "flash";
   public static final String JS = "js";
   private Integer id;
   private String name;
   private String advType;
   private String attrUrl;
   private String jsstring;
   private String url;
   private Date startTime;
   private Date endTime;
   private Integer priority;
   private Integer weights;
   private Integer clicks;
   private Integer showTimes;
   private String aexplain;
   private Boolean enable;
   private AdvertSlot slot;
   private Site site;
 
   public void init()
   {
     if (getClicks() == null) {
       setClicks(Integer.valueOf(0));
     }
     if (getStartTime() == null) {
       setStartTime(new Time(System.currentTimeMillis()));
     }
     if (getPriority() == null)
       setPriority(Integer.valueOf(10));
   }
 
   @Transient
   public String getAdvTypeString() {
     if (getAdvType().equals("img"))
       return "图片";
     if (getAdvType().equals("flash")) {
       return "FLASH";
     }
     return "代码";
   }
 
   @Id
   @Column(name="advert_id", unique=true, nullable=false)
   @TableGenerator(name="tg_advert", pkColumnValue="tq_advert", table="tq_gen_table", pkColumnName="tg_gen_name", valueColumnName="tq_gen_value", initialValue=1, allocationSize=1)
   @GeneratedValue(strategy=GenerationType.TABLE, generator="tg_advert")
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
   @Column(name="adv_type", nullable=false, length=20)
   public String getAdvType() {
     return this.advType;
   }
 
   public void setAdvType(String advType) {
     this.advType = advType;
   }
   @Column(name="attr_url", nullable=true, length=80)
   public String getAttrUrl() {
     return this.attrUrl;
   }
 
   public void setAttrUrl(String attrUrl) {
     this.attrUrl = attrUrl;
   }
   @Column(name="jsstring", nullable=true, length=500)
   public String getJsstring() {
     return this.jsstring;
   }
 
   public void setJsstring(String jsstring) {
     this.jsstring = jsstring;
   }
   @Column(name="url", nullable=false, length=100)
   public String getUrl() {
     return this.url;
   }
 
   public void setUrl(String url) {
     this.url = url;
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
   @Column(name="priority", nullable=false, length=10)
   public Integer getPriority() {
     return this.priority;
   }
 
   public void setPriority(Integer priority) {
     this.priority = priority;
   }
   @Column(name="weights", nullable=true, length=10)
   public Integer getWeights() {
     return this.weights;
   }
 
   public void setWeights(Integer weights) {
     this.weights = weights;
   }
   @Column(name="clicks", nullable=false, length=10)
   public Integer getClicks() {
     return this.clicks;
   }
 
   public void setClicks(Integer clicks) {
     this.clicks = clicks;
   }
   @Column(name="show_times", nullable=true, length=10)
   public Integer getShowTimes() {
     return this.showTimes;
   }
 
   public void setShowTimes(Integer showTimes) {
     this.showTimes = showTimes;
   }
   @Column(name="aexplain", nullable=true, length=500)
   public String getAexplain() {
     return this.aexplain;
   }
 
   public void setAexplain(String aexplain) {
     this.aexplain = aexplain;
   }
   @Column(name="enable", nullable=false, length=1)
   public Boolean getEnable() {
     return this.enable;
   }
 
   public void setEnable(Boolean enable) {
     this.enable = enable;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="slot_id", nullable=false)
   public AdvertSlot getSlot() { return this.slot; }
 
   public void setSlot(AdvertSlot slot)
   {
     this.slot = slot;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="site_id", nullable=false)
   public Site getSite() { return this.site; }
 
   public void setSite(Site site)
   {
     this.site = site;
   }
 }


 
 
 
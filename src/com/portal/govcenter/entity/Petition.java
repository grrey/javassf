 package com.portal.govcenter.entity;
 
 import com.portal.sysmgr.entity.Site;
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
 import javax.persistence.OneToOne;
 import javax.persistence.PrimaryKeyJoinColumn;
 import javax.persistence.Table;
 import javax.persistence.TableGenerator;
 import javax.persistence.Temporal;
 import javax.persistence.TemporalType;
 
 @Entity
 @Table(name="tq_petition")
 public class Petition
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private Integer id;
   private String title;
   private String name;
   private String mobile;
   private String email;
   private String address;
   private String zipcode;
   private Byte status;
   private Boolean show;
   private Date createTime;
   private Date replyTime;
   private PetitionExt ext;
   private Site site;
   private PetitionType type;
 
   @Id
   @Column(name="petition_id", unique=true, nullable=false)
   @TableGenerator(name="tg_petition", pkColumnValue="tq_petition", table="tq_gen_table", pkColumnName="tg_gen_name", valueColumnName="tq_gen_value", initialValue=1, allocationSize=1)
   @GeneratedValue(strategy=GenerationType.TABLE, generator="tg_petition")
   public Integer getId()
   {
     return this.id;
   }
 
   public void setId(Integer id) {
     this.id = id;
   }
   @Column(name="title", nullable=false, length=50)
   public String getTitle() {
     return this.title;
   }
 
   public void setTitle(String title) {
     this.title = title;
   }
   @Column(name="name", nullable=false, length=20)
   public String getName() {
     return this.name;
   }
 
   public void setName(String name) {
     this.name = name;
   }
   @Column(name="mobile", nullable=false, length=20)
   public String getMobile() {
     return this.mobile;
   }
 
   public void setMobile(String mobile) {
     this.mobile = mobile;
   }
   @Column(name="email", nullable=false, length=50)
   public String getEmail() {
     return this.email;
   }
 
   public void setEmail(String email) {
     this.email = email;
   }
   @Column(name="address", nullable=true, length=150)
   public String getAddress() {
     return this.address;
   }
 
   public void setAddress(String address) {
     this.address = address;
   }
   @Column(name="zipcode", nullable=true, length=20)
   public String getZipcode() {
     return this.zipcode;
   }
 
   public void setZipcode(String zipcode) {
     this.zipcode = zipcode;
   }
   @Column(name="status", nullable=false, length=5)
   public Byte getStatus() {
     return this.status;
   }
 
   public void setStatus(Byte status) {
     this.status = status;
   }
   @Column(name="is_show", nullable=false, length=1)
   public Boolean getShow() {
     return this.show;
   }
 
   public void setShow(Boolean show) {
     this.show = show;
   }
   @Temporal(TemporalType.TIMESTAMP)
   @Column(name="create_time", nullable=false, length=19)
   public Date getCreateTime() { return this.createTime; }
 
   public void setCreateTime(Date createTime)
   {
     this.createTime = createTime;
   }
   @Temporal(TemporalType.TIMESTAMP)
   @Column(name="reply_time", nullable=true, length=19)
   public Date getReplyTime() { return this.replyTime; }
 
   public void setReplyTime(Date replyTime)
   {
     this.replyTime = replyTime;
   }
   @OneToOne(cascade={javax.persistence.CascadeType.REMOVE}, fetch=FetchType.LAZY)
   @PrimaryKeyJoinColumn
   public PetitionExt getExt() { return this.ext; }
 
   public void setExt(PetitionExt ext)
   {
     this.ext = ext;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="site_id", nullable=false)
   public Site getSite() { return this.site; }
 
   public void setSite(Site site)
   {
     this.site = site;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="type_id", nullable=false)
   public PetitionType getType() { return this.type; }
 
   public void setType(PetitionType type)
   {
     this.type = type;
   }
 }


 
 
 
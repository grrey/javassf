 package com.portal.govcenter.entity;
 
 import com.portal.sysmgr.entity.Site;
 import com.portal.usermgr.entity.Depart;
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
 import javax.persistence.OneToOne;
 import javax.persistence.PrimaryKeyJoinColumn;
 import javax.persistence.Table;
 import javax.persistence.TableGenerator;
 import javax.persistence.Temporal;
 import javax.persistence.TemporalType;
 import javax.persistence.Transient;
 
 @Entity
 @Table(name="tq_mailbox")
 public class Mailbox
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   public static final Byte DELETED = Byte.valueOf((byte) -2);
 
   public static final Byte BACK = Byte.valueOf((byte) -1);
 
   public static final Byte DEALING = Byte.valueOf((byte) 1);
 
   public static final Byte FORWARD = Byte.valueOf((byte) 2);
 
   public static final Byte DEALED = Byte.valueOf((byte) 3);
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
   private MailboxExt ext;
   private Site site;
   private Depart depart;
   private MailboxType type;
 
   public void init()
   {
     if (getStatus() == null) {
       setStatus(DEALING);
     }
     if (getShow() == null) {
       setShow(Boolean.valueOf(false));
     }
     if (getCreateTime() == null)
       setCreateTime(new Timestamp(System.currentTimeMillis()));
   }
 
   @Transient
   public String getStatusString() {
     if (getStatus().equals(DEALING))
       return "<span style='color:red'>受理中</span>";
     if (getStatus().equals(DEALED))
       return "<span style='color:blue'>已办结</span>";
     if (getStatus().equals(FORWARD))
       return "<span style='color:red'>批转</span>";
     if (getStatus().equals(BACK)) {
       return "<span style='color:red'>退回</span>";
     }
     return "<span style='color:red'>已删除</span>";
   }
 
   @Transient
   public String getContent() {
     if (getExt() != null) {
       return getExt().getContent();
     }
     return null;
   }
   @Transient
   public String getReply() {
     if (getExt() != null) {
       return getExt().getReply();
     }
     return null;
   }
 
   @Id
   @Column(name="mailbox_id", unique=true, nullable=false)
   @TableGenerator(name="tg_mailbox", pkColumnValue="tq_mailbox", table="tq_gen_table", pkColumnName="tg_gen_name", valueColumnName="tq_gen_value", initialValue=1, allocationSize=1)
   @GeneratedValue(strategy=GenerationType.TABLE, generator="tg_mailbox")
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
   public MailboxExt getExt() { return this.ext; }
 
   public void setExt(MailboxExt ext)
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
   @JoinColumn(name="depart_id", nullable=true)
   public Depart getDepart() { return this.depart; }
 
   public void setDepart(Depart depart)
   {
     this.depart = depart;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="type_id", nullable=false)
   public MailboxType getType() { return this.type; }
 
   public void setType(MailboxType type)
   {
     this.type = type;
   }
 }


 
 
 
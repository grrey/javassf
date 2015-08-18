 package com.portal.usermgr.entity;
 
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
 @Table(name="tq_site_message")
 public class SiteMessage
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   public static final Integer DEL = Integer.valueOf(-2);
 
   public static final Integer RECYCLE = Integer.valueOf(-1);
 
   public static final Integer NORMAL = Integer.valueOf(0);
   private Integer id;
   private String title;
   private String content;
   private Boolean group;
   private Integer status;
   private Date createTime;
   private MessageReceive receive;
   private User send;
 
   public void init()
   {
     if (getGroup() == null) {
       setGroup(Boolean.valueOf(false));
     }
     if (getStatus() == null) {
       setStatus(NORMAL);
     }
     if (getCreateTime() == null)
       setCreateTime(new Timestamp(System.currentTimeMillis()));
   }
 
   @Transient
   public String getReceContent() {
     MessageReceive ext = getReceive();
     if (ext != null) {
       return ext.getContent();
     }
     return null;
   }
 
   @Id
   @Column(name="message_id", unique=true, nullable=false)
   @TableGenerator(name="tg_site_message", pkColumnValue="tq_site_message", table="tq_gen_table", pkColumnName="tg_gen_name", valueColumnName="tq_gen_value", initialValue=1, allocationSize=1)
   @GeneratedValue(strategy=GenerationType.TABLE, generator="tg_site_message")
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
   @Column(name="content", nullable=false, length=1000)
   public String getContent() {
     return this.content;
   }
 
   public void setContent(String content) {
     this.content = content;
   }
   @Column(name="is_group", nullable=false, length=1)
   public Boolean getGroup() {
     return this.group;
   }
 
   public void setGroup(Boolean group) {
     this.group = group;
   }
   @Column(name="status", nullable=false, length=10)
   public Integer getStatus() {
     return this.status;
   }
 
   public void setStatus(Integer status) {
     this.status = status;
   }
   @Temporal(TemporalType.TIMESTAMP)
   @Column(name="create_time", nullable=false, length=19)
   public Date getCreateTime() { return this.createTime; }
 
   public void setCreateTime(Date createTime)
   {
     this.createTime = createTime;
   }
   @OneToOne(cascade={javax.persistence.CascadeType.REMOVE}, fetch=FetchType.LAZY)
   @PrimaryKeyJoinColumn
   public MessageReceive getReceive() { return this.receive; }
 
   public void setReceive(MessageReceive receive)
   {
     this.receive = receive;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="send_id", nullable=false)
   public User getSend() { return this.send; }
 
   public void setSend(User send)
   {
     this.send = send;
   }
 }


 
 
 
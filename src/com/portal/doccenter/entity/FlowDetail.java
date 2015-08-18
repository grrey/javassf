 package com.portal.doccenter.entity;
 
 import com.portal.usermgr.entity.Role;
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
 @Table(name="tq_flow_detail")
 public class FlowDetail
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private Integer id;
   private Boolean backInitial;
   private Boolean checked;
   private String reason;
   private Integer priority;
   private Date createTime;
   private Article doc;
   private User user;
   private Role role;
 
   public void init()
   {
     if (getBackInitial() == null) {
       setBackInitial(Boolean.valueOf(false));
     }
     if (getChecked() == null) {
       setChecked(Boolean.valueOf(true));
     }
     if (getPriority() == null) {
       setPriority(Integer.valueOf(1));
     }
     if (getCreateTime() == null)
       setCreateTime(new Timestamp(System.currentTimeMillis()));
   }
 
   @Id
   @Column(name="detail_id", unique=true, nullable=false)
   @TableGenerator(name="tg_flow_detail", pkColumnValue="tq_flow_detail", table="tq_gen_table", pkColumnName="tg_gen_name", valueColumnName="tq_gen_value", initialValue=1, allocationSize=1)
   @GeneratedValue(strategy=GenerationType.TABLE, generator="tg_flow_detail")
   public Integer getId()
   {
     return this.id;
   }
 
   public void setId(Integer id) {
     this.id = id;
   }
   @Column(name="back_initial", nullable=false, length=1)
   public Boolean getBackInitial() {
     return this.backInitial;
   }
 
   public void setBackInitial(Boolean backInitial) {
     this.backInitial = backInitial;
   }
   @Column(name="is_checked", nullable=false, length=1)
   public Boolean getChecked() {
     return this.checked;
   }
 
   public void setChecked(Boolean checked) {
     this.checked = checked;
   }
   @Column(name="reason", nullable=true, length=100)
   public String getReason() {
     return this.reason;
   }
 
   public void setReason(String reason) {
     this.reason = reason;
   }
   @Column(name="priority", nullable=false, length=10)
   public Integer getPriority() {
     return this.priority;
   }
 
   public void setPriority(Integer priority) {
     this.priority = priority;
   }
   @Temporal(TemporalType.TIMESTAMP)
   @Column(name="create_time", nullable=false, length=19)
   public Date getCreateTime() { return this.createTime; }
 
   public void setCreateTime(Date createTime)
   {
     this.createTime = createTime;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="doc_id", nullable=false)
   public Article getDoc() { return this.doc; }
 
   public void setDoc(Article doc)
   {
     this.doc = doc;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="user_id", nullable=false)
   public User getUser() { return this.user; }
 
   public void setUser(User user)
   {
     this.user = user;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="role_id", nullable=false)
   public Role getRole() { return this.role; }
 
   public void setRole(Role role)
   {
     this.role = role;
   }
 }


 
 
 
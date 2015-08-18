package com.portal.usermgr.entity;

import com.portal.extrafunc.entity.UserForum;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
 import javax.persistence.Transient;
 
 @Entity
 @Table(name="tq_user")
 public class User
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   public static final byte NORMAL = 0;
   public static final byte NO_CHECK = -2;
   public static final byte DISABLED = -1;
   private Integer id;
   private String username;
   private String password;
   private String email;
   private String realName;
   private String phone;
   private String mobile;
   private Byte status;
   private Integer failCount;
   private Date lastFailTime;
   private Member member;
   private Admin admin;
   private UserForum userForum;
 
   public void init()
   {
     if (getStatus() == null) {
       setStatus(Byte.valueOf((byte) 0));
     }
     if (getFailCount() == null)
       setFailCount(Integer.valueOf(0));
   }
 
   @Transient
   public Boolean getTakeDepart(Integer siteId) {
     if (getAdmin() == null) {
       return Boolean.valueOf(true);
     }
     if (getAdmin().getAdminCheck(siteId) == null) {
       return Boolean.valueOf(true);
     }
     return getAdmin().getAdminCheck(siteId).getTakeDepart();
   }
   @Transient
   public Depart getDepart(Integer siteId) {
     if (getAdmin() == null) {
       return null;
     }
     if (getAdmin().getDepart(siteId) == null) {
       return null;
     }
     return getAdmin().getDepart(siteId);
   }
   @Transient
   public Integer getDepartId(Integer siteId) {
     if (getDepart(siteId) == null) {
       return null;
     }
     return getDepart(siteId).getId();
   }
   @Transient
   public Role getRole(Integer siteId) {
     if (getAdmin() == null) {
       return null;
     }
     if (getAdmin().getRole(siteId) == null) {
       return null;
     }
     return getAdmin().getRole(siteId);
   }
   @Transient
   public Integer getRoleId(Integer siteId) {
     if (getRole(siteId) == null) {
       return null;
     }
     return getRole(siteId).getId();
   }
   @Transient
   public boolean getAllChannel(Integer siteId) {
     if (getAdmin() == null) {
       return false;
     }
     return getAdmin().getAllChannel(siteId).booleanValue();
   }
   @Transient
   public Byte getManageStatus(Integer siteId) {
     if (getAdmin() == null) {
       return null;
     }
     return getAdmin().getManageStatus(siteId);
   }
 
   public static Integer[] fetchIds(Collection<User> users) {
     if (users == null) {
       return null;
     }
     Integer[] ids = new Integer[users.size()];
     int i = 0;
     for (User u : users) {
       ids[(i++)] = u.getId();
     }
     return ids;
   }
   @Transient
   public static boolean isToday(Date date) {
     long day = date.getTime() / 1000L / 60L / 60L / 24L;
     long currentDay = System.currentTimeMillis() / 1000L / 60L / 60L / 24L;
     return day == currentDay;
   }
 
   @Id
   @Column(name="user_id", unique=true, nullable=false)
   @TableGenerator(name="tg_user", pkColumnValue="tq_user", table="tq_gen_table", pkColumnName="tg_gen_name", valueColumnName="tq_gen_value", initialValue=1, allocationSize=1)
   @GeneratedValue(strategy=GenerationType.TABLE, generator="tg_user")
   public Integer getId()
   {
     return this.id;
   }
 
   public void setId(Integer id) {
     this.id = id;
   }
   @Column(name="username", nullable=false, length=50)
   public String getUsername() {
     return this.username;
   }
 
   public void setUsername(String username) {
     this.username = username;
   }
   @Column(name="t_password", nullable=false, length=50)
   public String getPassword() {
     return this.password;
   }
 
   public void setPassword(String password) {
     this.password = password;
   }
   @Column(name="email", nullable=true, length=50)
   public String getEmail() {
     return this.email;
   }
 
   public void setEmail(String email) {
     this.email = email;
   }
   @Column(name="real_name", nullable=true, length=50)
   public String getRealName() {
     return this.realName;
   }
 
   public void setRealName(String realName) {
     this.realName = realName;
   }
   @Column(name="phone", nullable=true, length=20)
   public String getPhone() {
     return this.phone;
   }
 
   public void setPhone(String phone) {
     this.phone = phone;
   }
   @Column(name="mobile", nullable=true, length=20)
   public String getMobile() {
     return this.mobile;
   }
 
   public void setMobile(String mobile) {
     this.mobile = mobile;
   }
   @Column(name="t_status", nullable=true, length=5)
   public Byte getStatus() {
     return this.status;
   }
 
   public void setStatus(Byte status) {
     this.status = status;
   }
   @Column(name="fail_count", nullable=false, length=10)
   public Integer getFailCount() {
     return this.failCount;
   }
 
   public void setFailCount(Integer failCount) {
     this.failCount = failCount;
   }
   @Temporal(TemporalType.TIMESTAMP)
   @Column(name="last_fail_time", nullable=true, length=19)
   public Date getLastFailTime() { return this.lastFailTime; }
 
   public void setLastFailTime(Date lastFailTime)
   {
     this.lastFailTime = lastFailTime;
   }
   @OneToOne(cascade={javax.persistence.CascadeType.REMOVE}, fetch=FetchType.LAZY)
   @PrimaryKeyJoinColumn
   public Member getMember() { return this.member; }
 
   public void setMember(Member member)
   {
     this.member = member;
   }
   @OneToOne(cascade={javax.persistence.CascadeType.REMOVE}, fetch=FetchType.LAZY)
   @PrimaryKeyJoinColumn
   public Admin getAdmin() { return this.admin; }
 
   public void setAdmin(Admin admin)
   {
     this.admin = admin;
   }
   @OneToOne(cascade={javax.persistence.CascadeType.REMOVE}, fetch=FetchType.LAZY)
   @PrimaryKeyJoinColumn
   public UserForum getUserForum() { return this.userForum; }
 
   public void setUserForum(UserForum userForum)
   {
     this.userForum = userForum;
   }
 }

 package com.portal.usermgr.entity;
 
 import com.portal.sysmgr.entity.Site;
 import java.io.Serializable;
 import java.sql.Timestamp;
 import java.util.Date;
 import java.util.HashSet;
 import java.util.Set;
 import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.FetchType;
 import javax.persistence.GeneratedValue;
 import javax.persistence.Id;
 import javax.persistence.JoinTable;
 import javax.persistence.OneToMany;
 import javax.persistence.OneToOne;
 import javax.persistence.PrimaryKeyJoinColumn;
 import javax.persistence.Table;
 import javax.persistence.Temporal;
 import javax.persistence.TemporalType;
 import javax.persistence.Transient;
 import org.hibernate.annotations.GenericGenerator;
 
 @Entity
 @Table(name="tq_member")
 public class Member
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private Integer id;
   private String avatar;
   private Byte gender;
   private Date birthday;
   private String address;
   private String signature;
   private Date registerTime;
   private String registerIp;
   private Date lastLoginTime;
   private String lastLoginIp;
   private Integer loginCount;
   private Byte status;
   private User user;
   private Set<Group> groups;
 
   public void init()
   {
     if (getRegisterTime() == null) {
       setRegisterTime(new Timestamp(System.currentTimeMillis()));
     }
     if (getLastLoginTime() == null) {
       setLastLoginTime(new Timestamp(System.currentTimeMillis()));
     }
     if (getLoginCount() == null) {
       setLoginCount(Integer.valueOf(1));
     }
     if (getStatus() == null)
       setStatus(Byte.valueOf((byte) 0));
   }
 
   @Transient
   public Group getGroup(Integer siteId) {
     for (Group group : getGroups()) {
       if (group.getSite().getId().equals(siteId)) {
         return group;
       }
     }
     return null;
   }
   @Transient
   public Integer getGroupId(Integer siteId) {
     if (getGroup(siteId) != null) {
       return getGroup(siteId).getId();
     }
     return null;
   }
   @Transient
   public Group getGroup() {
     return getGroup(Integer.valueOf(1));
   }
   @Transient
   public Set<String> getPerms(Integer siteId) {
     Group group = getGroup(siteId);
     if (group == null) {
       return null;
     }
     Set perms = new HashSet();
     if (group.getPerms() != null) {
       for (String perm : group.getPerms().split(",")) {
         perms.add(perm);
       }
     }
     return perms;
   }
 
   public void addToGroups(Group group) {
     if (group == null) {
       return;
     }
     Set set = getGroups();
     if (set == null) {
       set = new HashSet();
       setGroups(set);
     }
     set.add(group);
   }
   @Transient
   public String getStatusString() {
     if (getStatus().equals(Byte.valueOf((byte) 0)))
       return "<span style='color:blue'>正常</span>";
     if (getStatus().equals(Byte.valueOf((byte) -2))) {
       return "<span style='color:red'>未审核</span>";
     }
     return "<span style='color:red'>禁用</span>";
   }
 
   @Id
   @Column(name="member_id", unique=true, nullable=false)
   @GenericGenerator(name="copy", strategy="foreign", parameters={@org.hibernate.annotations.Parameter(name="property", value="user")})
   @GeneratedValue(generator="copy")
   public Integer getId()
   {
     return this.id;
   }
 
   public void setId(Integer id) {
     this.id = id;
   }
   @Column(name="avatar", nullable=true, length=100)
   public String getAvatar() {
     return this.avatar;
   }
 
   public void setAvatar(String avatar) {
     this.avatar = avatar;
   }
   @Column(name="gender", nullable=true, length=5)
   public Byte getGender() {
     return this.gender;
   }
 
   public void setGender(Byte gender) {
     this.gender = gender;
   }
   @Temporal(TemporalType.DATE)
   @Column(name="birthday", nullable=true, length=19)
   public Date getBirthday() { return this.birthday; }
 
   public void setBirthday(Date birthday)
   {
     this.birthday = birthday;
   }
   @Column(name="address", nullable=true, length=100)
   public String getAddress() {
     return this.address;
   }
 
   public void setAddress(String address) {
     this.address = address;
   }
   @Column(name="signature", nullable=true, length=255)
   public String getSignature() {
     return this.signature;
   }
 
   public void setSignature(String signature) {
     this.signature = signature;
   }
   @Temporal(TemporalType.TIMESTAMP)
   @Column(name="registe_time", nullable=false, length=19)
   public Date getRegisterTime() { return this.registerTime; }
 
   public void setRegisterTime(Date registerTime)
   {
     this.registerTime = registerTime;
   }
   @Column(name="registe_ip", nullable=true, length=20)
   public String getRegisterIp() {
     return this.registerIp;
   }
 
   public void setRegisterIp(String registerIp) {
     this.registerIp = registerIp;
   }
   @Temporal(TemporalType.TIMESTAMP)
   @Column(name="last_login_time", nullable=true, length=19)
   public Date getLastLoginTime() { return this.lastLoginTime; }
 
   public void setLastLoginTime(Date lastLoginTime)
   {
     this.lastLoginTime = lastLoginTime;
   }
   @Column(name="last_login_ip", nullable=true, length=20)
   public String getLastLoginIp() {
     return this.lastLoginIp;
   }
 
   public void setLastLoginIp(String lastLoginIp) {
     this.lastLoginIp = lastLoginIp;
   }
   @Column(name="login_count", nullable=false, length=10)
   public Integer getLoginCount() {
     return this.loginCount;
   }
 
   public void setLoginCount(Integer loginCount) {
     this.loginCount = loginCount;
   }
   @Column(name="t_status", nullable=false, length=5)
   public Byte getStatus() {
     return this.status;
   }
 
   public void setStatus(Byte status) {
     this.status = status;
   }
   @OneToOne
   @PrimaryKeyJoinColumn
   public User getUser() { return this.user; }
 
   public void setUser(User user)
   {
     this.user = user;
   }
   @OneToMany(fetch=FetchType.LAZY)
   @JoinTable(name="tq_member_group", joinColumns={@javax.persistence.JoinColumn(name="member_id")}, inverseJoinColumns={@javax.persistence.JoinColumn(name="group_id")})
   public Set<Group> getGroups() { return this.groups; }
 
   public void setGroups(Set<Group> groups)
   {
     this.groups = groups;
   }
 }


 
 
 
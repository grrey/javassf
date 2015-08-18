 package com.portal.usermgr.entity;
 
 import com.portal.doccenter.entity.Channel;
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
 @Table(name="tq_admin")
 public class Admin
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private Integer id;
   private Date registerTime;
   private String registerIp;
   private Date lastLoginTime;
   private String lastLoginIp;
   private Integer loginCount;
   private Byte status;
   private User user;
   private Set<AdminCheck> adminChecks;
   private Set<Role> roles;
   private Set<Depart> departs;
 
   public void init()
   {
     if (getRegisterTime() == null) {
       setRegisterTime(new Timestamp(System.currentTimeMillis()));
     }
     if (getLoginCount() == null) {
       setLoginCount(Integer.valueOf(0));
     }
     if (getStatus() == null)
       setStatus(Byte.valueOf((byte) 0));
   }
 
   @Transient
   public Byte getManageStatus(Integer siteId) {
     AdminCheck uc = getAdminCheck(siteId);
     if (uc != null) {
       return uc.getManageStatus();
     }
     return null;
   }
 
   @Transient
   public boolean haveAllManage(Integer siteId) {
     if (getManageStatus(siteId) == null) {
       return false;
     }
 
     return getManageStatus(siteId).equals(Byte.valueOf((byte) 4));
   }
 
   @Transient
   public AdminCheck getAdminCheck(Integer siteId)
   {
     Set<AdminCheck> set = getAdminChecks();
     for (AdminCheck uc : set) {
       if (uc.getSite().getId().equals(siteId)) {
         return uc;
       }
     }
     return null;
   }
   @Transient
   public Boolean getTakeDepart(Integer siteId) {
     if (getAdminCheck(siteId) == null) {
       return null;
     }
     return getAdminCheck(siteId).getTakeDepart();
   }
   @Transient
   public Role getRole(Integer siteId) {
     for (Role role : getRoles()) {
       if (role.getSite().getId().equals(siteId)) {
         return role;
       }
     }
     return null;
   }
   @Transient
   public Depart getDepart(Integer siteId) {
     for (Depart depart : getDeparts()) {
       if (depart.getSite().getId().equals(siteId)) {
         return depart;
       }
     }
     return null;
   }
   @Transient
   public Set<Channel> getChannels(Integer siteId) {
     if ((getAdminCheck(siteId) != null) && 
       (!getAdminCheck(siteId).getTakeDepart().booleanValue())) {
       return getAdminCheck(siteId).getChannels();
     }
 
     return null;
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
 
   @Transient
   public Boolean getAllChannel(Integer siteId) {
     if ((getAdminCheck(siteId) != null) && 
       (!getAdminCheck(siteId).getTakeDepart().booleanValue())) {
       return Boolean.valueOf(false);
     }
 
     Depart depart = getDepart(siteId);
     if (depart == null) {
       return Boolean.valueOf(true);
     }
     if (depart.getAllChannel().booleanValue()) {
       return Boolean.valueOf(true);
     }
     return Boolean.valueOf(false);
   }
   @Transient
   public String getUsername() {
     return getUser().getUsername();
   }
   @Transient
   public Role getRole() {
     return getRole(Integer.valueOf(1));
   }
   @Transient
   public Depart getDepart() {
     return getDepart(Integer.valueOf(1));
   }
 
   public void addToRoles(Role role) {
     if (role == null) {
       return;
     }
     Set set = getRoles();
     if (set == null) {
       set = new HashSet();
       setRoles(set);
     }
     set.add(role);
   }
 
   public void addToDeparts(Depart depart) {
     if (depart == null) {
       return;
     }
     Set set = getDeparts();
     if (set == null) {
       set = new HashSet();
       setDeparts(set);
     }
     set.add(depart);
   }
 
   public void addToAdminChecks(AdminCheck adminCheck) {
     if (adminCheck == null) {
       return;
     }
     Set set = getAdminChecks();
     if (set == null) {
       set = new HashSet();
       setAdminChecks(set);
     }
     set.add(adminCheck);
   }
   @Transient
   public Set<Site> getSites() {
     Set<AdminCheck> adminSites = getAdminChecks();
     Set<Site> sites = new HashSet<Site>(adminSites.size());
     for (AdminCheck us : adminSites) {
       sites.add(us.getSite());
     }
     return sites;
   }
   @Transient
   public Boolean getAllPerm(Integer siteId) {
     Role role = getRole(siteId);
     if (role == null) {
       return Boolean.valueOf(false);
     }
     if (role.getAllPerm() != null) {
       return role.getAllPerm();
     }
     return Boolean.valueOf(false);
   }
   @Transient
   public Set<String> getPerms(Integer siteId) {
     Role role = getRole(siteId);
     if (role == null) {
       return null;
     }
     Set perms = new HashSet();
     if (role.getAllPerm().booleanValue()) {
       perms.add("*");
     }
     else if (role.getPerms() != null) {
       for (String perm : role.getPerms().split(",")) {
         perms.add(perm);
       }
     }
 
     return perms;
   }
 
   @Id
   @Column(name="admin_id", unique=true, nullable=false)
   @GenericGenerator(name="copy", strategy="foreign", parameters={@org.hibernate.annotations.Parameter(name="property", value="user")})
   @GeneratedValue(generator="copy")
   public Integer getId()
   {
     return this.id;
   }
 
   public void setId(Integer id) {
     this.id = id;
   }
   @Temporal(TemporalType.TIMESTAMP)
   @Column(name="registe_time", nullable=false, length=19)
   public Date getRegisterTime() { return this.registerTime; }
 
   public void setRegisterTime(Date registerTime)
   {
     this.registerTime = registerTime;
   }
   @Column(name="registe_ip", nullable=false, length=20)
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
   @OneToMany(fetch=FetchType.LAZY, cascade={javax.persistence.CascadeType.REMOVE}, mappedBy="admin")
   public Set<AdminCheck> getAdminChecks() {
     return this.adminChecks;
   }
 
   public void setAdminChecks(Set<AdminCheck> adminChecks)
   {
     this.adminChecks = adminChecks;
   }
   @OneToMany(fetch=FetchType.LAZY)
   @JoinTable(name="tq_admin_role", joinColumns={@javax.persistence.JoinColumn(name="admin_id")}, inverseJoinColumns={@javax.persistence.JoinColumn(name="role_id")})
   public Set<Role> getRoles() { return this.roles; }
 
   public void setRoles(Set<Role> roles)
   {
     this.roles = roles;
   }
   @OneToMany(fetch=FetchType.LAZY)
   @JoinTable(name="tq_admin_depart", joinColumns={@javax.persistence.JoinColumn(name="admin_id")}, inverseJoinColumns={@javax.persistence.JoinColumn(name="depart_id")})
   public Set<Depart> getDeparts() { return this.departs;
   }
 
   public void setDeparts(Set<Depart> departs)
   {
     this.departs = departs;
   }
 }


 
 
 
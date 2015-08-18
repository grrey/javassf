 package com.portal.usermgr.entity;
 
 import com.portal.sysmgr.entity.Site;
 import java.io.Serializable;
 import java.util.Collection;
 import java.util.HashSet;
 import java.util.Set;
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
 import javax.persistence.Transient;
 
 @Entity
 @Table(name="tq_role")
 public class Role
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private Integer id;
   private String name;
   private Integer priority;
   private Boolean allPerm;
   private RolePerm rolePerm;
   private Site site;
 
   public void init()
   {
     if (getPriority() == null)
       setPriority(Integer.valueOf(10));
   }
 
   public static Integer[] fetchIds(Collection<Role> roles)
   {
     if (roles == null) {
       return null;
     }
     Integer[] ids = new Integer[roles.size()];
     int i = 0;
     for (Role r : roles) {
       ids[(i++)] = r.getId();
     }
     return ids;
   }
   @Transient
   public String getPerms() {
     RolePerm rolePerm = getRolePerm();
     if (rolePerm != null) {
       return rolePerm.getPerms();
     }
     return null;
   }
   @Transient
   public Set<String> getPermsSet() {
     Set perms = new HashSet();
     RolePerm rolePerm = getRolePerm();
     if ((rolePerm != null) && 
       (rolePerm.getPerms() != null)) {
       for (String perm : rolePerm.getPerms().split(",")) {
         perms.add(perm);
       }
     }
 
     return perms;
   }
 
   @Id
   @Column(name="role_id", unique=true, nullable=false)
   @TableGenerator(name="tg_role", pkColumnValue="tq_role", table="tq_gen_table", pkColumnName="tg_gen_name", valueColumnName="tq_gen_value", initialValue=1, allocationSize=1)
   @GeneratedValue(strategy=GenerationType.TABLE, generator="tg_role")
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
   @Column(name="priority", nullable=false, length=11)
   public Integer getPriority() {
     return this.priority;
   }
 
   public void setPriority(Integer priority) {
     this.priority = priority;
   }
   @Column(name="is_all_perm", nullable=false, length=1)
   public Boolean getAllPerm() {
     return this.allPerm;
   }
 
   public void setAllPerm(Boolean allPerm) {
     this.allPerm = allPerm;
   }
   @OneToOne(cascade={javax.persistence.CascadeType.REMOVE}, fetch=FetchType.LAZY)
   @PrimaryKeyJoinColumn
   public RolePerm getRolePerm() { return this.rolePerm; }
 
   public void setRolePerm(RolePerm rolePerm)
   {
     this.rolePerm = rolePerm;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="site_id", nullable=false)
   public Site getSite() { return this.site; }
 
   public void setSite(Site site)
   {
     this.site = site;
   }
 }


 
 
 
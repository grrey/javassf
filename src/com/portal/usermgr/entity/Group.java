 package com.portal.usermgr.entity;
 
 import com.portal.doccenter.entity.Channel;
 import com.portal.sysmgr.entity.Site;
 import java.io.Serializable;
 import java.util.Collection;
 import java.util.Set;
 import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.FetchType;
 import javax.persistence.GeneratedValue;
 import javax.persistence.GenerationType;
 import javax.persistence.Id;
 import javax.persistence.JoinColumn;
 import javax.persistence.ManyToMany;
 import javax.persistence.ManyToOne;
 import javax.persistence.OneToOne;
 import javax.persistence.PrimaryKeyJoinColumn;
 import javax.persistence.Table;
 import javax.persistence.TableGenerator;
 import javax.persistence.Transient;
 
 @Entity
 @Table(name="tq_group")
 public class Group
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private Integer id;
   private String name;
   private Integer priority;
   private Boolean allPerm;
   private Boolean registShow;
   private GroupPerm groupPerm;
   private Site site;
   private Set<Channel> viewChannels;
   private Set<Channel> contriChannels;
 
   public void init()
   {
     if (getPriority() == null) {
       setPriority(Integer.valueOf(10));
     }
     if (getRegistShow() == null)
       setRegistShow(Boolean.valueOf(true));
   }
 
   public static Integer[] fetchIds(Collection<Group> groups)
   {
     Integer[] ids = new Integer[groups.size()];
     int i = 0;
     for (Group g : groups) {
       ids[(i++)] = g.getId();
     }
     return ids;
   }
   @Transient
   public String getPerms() {
     GroupPerm groupPerm = getGroupPerm();
     if (groupPerm != null) {
       return groupPerm.getPerms();
     }
     return null;
   }
 
   @Id
   @Column(name="group_id", unique=true, nullable=false)
   @TableGenerator(name="tg_group", pkColumnValue="tq_group", table="tq_gen_table", pkColumnName="tg_gen_name", valueColumnName="tq_gen_value", initialValue=1, allocationSize=1)
   @GeneratedValue(strategy=GenerationType.TABLE, generator="tg_group")
   public Integer getId()
   {
     return this.id;
   }
 
   public void setId(Integer id) {
     this.id = id;
   }
   @Column(name="name", nullable=false, length=30)
   public String getName() {
     return this.name;
   }
 
   public void setName(String name) {
     this.name = name;
   }
   @Column(name="priority", nullable=false, length=10)
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
   @Column(name="is_regist_show", nullable=false, length=1)
   public Boolean getRegistShow() {
     return this.registShow;
   }
 
   public void setRegistShow(Boolean registShow) {
     this.registShow = registShow;
   }
   @OneToOne(cascade={javax.persistence.CascadeType.REMOVE}, fetch=FetchType.LAZY)
   @PrimaryKeyJoinColumn
   public GroupPerm getGroupPerm() { return this.groupPerm; }
 
   public void setGroupPerm(GroupPerm groupPerm)
   {
     this.groupPerm = groupPerm;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="site_id", nullable=false)
   public Site getSite() { return this.site; }
 
   public void setSite(Site site)
   {
     this.site = site;
   }
   @ManyToMany(mappedBy="viewGroups", fetch=FetchType.LAZY)
   public Set<Channel> getViewChannels() {
     return this.viewChannels;
   }
 
   public void setViewChannels(Set<Channel> viewChannels)
   {
     this.viewChannels = viewChannels;
   }
   @ManyToMany(mappedBy="contriGroups", fetch=FetchType.LAZY)
   public Set<Channel> getContriChannels() {
     return this.contriChannels;
   }
 
   public void setContriChannels(Set<Channel> contriChannels)
   {
     this.contriChannels = contriChannels;
   }
 }


 
 
 
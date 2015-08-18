 package com.portal.usermgr.entity;
 
 import java.io.Serializable;
 import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.GeneratedValue;
 import javax.persistence.Id;
 import javax.persistence.Lob;
 import javax.persistence.OneToOne;
 import javax.persistence.PrimaryKeyJoinColumn;
 import javax.persistence.Table;
 import org.hibernate.annotations.GenericGenerator;
 
 @Entity
 @Table(name="tq_group_perm")
 public class GroupPerm
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private Integer id;
   private String perms;
   private Group group;
 
   @Id
   @Column(name="group_id", unique=true, nullable=false)
   @GenericGenerator(name="copy", strategy="foreign", parameters={@org.hibernate.annotations.Parameter(name="property", value="group")})
   @GeneratedValue(generator="copy")
   public Integer getId()
   {
     return this.id;
   }
 
   public void setId(Integer id) {
     this.id = id;
   }
   @Lob
   @Column(name="perms", nullable=true)
   public String getPerms() { return this.perms; }
 
   public void setPerms(String perms)
   {
     this.perms = perms;
   }
   @OneToOne
   @PrimaryKeyJoinColumn
   public Group getGroup() { return this.group; }
 
   public void setGroup(Group group)
   {
     this.group = group;
   }
 }


 
 
 
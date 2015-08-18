 package com.portal.extrafunc.entity;
 
 import com.portal.usermgr.entity.User;
 import java.io.Serializable;
 import java.util.Date;
 import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.GeneratedValue;
 import javax.persistence.Id;
 import javax.persistence.OneToOne;
 import javax.persistence.PrimaryKeyJoinColumn;
 import javax.persistence.Table;
 import javax.persistence.Temporal;
 import javax.persistence.TemporalType;
 import org.hibernate.annotations.GenericGenerator;
 
 @Entity
 @Table(name="tq_user_forum")
 public class UserForum
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   public static final int NORMAL = 0;
   public static final int NOT_POSTS = -1;
   public static final int SHIELD = -2;
   private Integer id;
   private String avatar;
   private Integer essenaCount;
   private Integer themeCount;
   private Integer replyCount;
   private Integer point;
   private Integer status;
   private Date statusTime;
   private String signature;
   private User user;
 
   public void init()
   {
     if (getThemeCount() == null) {
       setThemeCount(Integer.valueOf(0));
     }
     if (getReplyCount() == null) {
       setReplyCount(Integer.valueOf(0));
     }
     if (getPoint() == null) {
       setPoint(Integer.valueOf(0));
     }
     if (getStatus() == null)
       setStatus(Integer.valueOf(0));
   }
 
   @Id
   @Column(name="user_id", unique=true, nullable=false)
   @GenericGenerator(name="copy", strategy="foreign", parameters={@org.hibernate.annotations.Parameter(name="property", value="user")})
   @GeneratedValue(generator="copy")
   public Integer getId()
   {
     return this.id;
   }
 
   public void setId(Integer id) {
     this.id = id;
   }
   @Column(name="avatar", nullable=true, length=50)
   public String getAvatar() {
     return this.avatar;
   }
 
   public void setAvatar(String avatar) {
     this.avatar = avatar;
   }
   @Column(name="essena_count", nullable=false, length=10)
   public Integer getEssenaCount() {
     return this.essenaCount;
   }
 
   public void setEssenaCount(Integer essenaCount) {
     this.essenaCount = essenaCount;
   }
   @Column(name="theme_count", nullable=false, length=10)
   public Integer getThemeCount() {
     return this.themeCount;
   }
 
   public void setThemeCount(Integer themeCount) {
     this.themeCount = themeCount;
   }
   @Column(name="reply_count", nullable=false, length=10)
   public Integer getReplyCount() {
     return this.replyCount;
   }
 
   public void setReplyCount(Integer replyCount) {
     this.replyCount = replyCount;
   }
   @Column(name="point", nullable=false, length=10)
   public Integer getPoint() {
     return this.point;
   }
 
   public void setPoint(Integer point) {
     this.point = point;
   }
   @Column(name="status", nullable=false, length=10)
   public Integer getStatus() {
     return this.status;
   }
 
   public void setStatus(Integer status) {
     this.status = status;
   }
   @Temporal(TemporalType.DATE)
   @Column(name="status_time", nullable=true, length=10)
   public Date getStatusTime() { return this.statusTime; }
 
   public void setStatusTime(Date statusTime)
   {
     this.statusTime = statusTime;
   }
   @Column(name="signature", nullable=true, length=255)
   public String getSignature() {
     return this.signature;
   }
 
   public void setSignature(String signature) {
     this.signature = signature;
   }
   @OneToOne
   @PrimaryKeyJoinColumn
   public User getUser() { return this.user; }
 
   public void setUser(User user)
   {
     this.user = user;
   }
 }


 
 
 
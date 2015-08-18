 package com.portal.sysmgr.entity;
 
 import java.io.Serializable;
 import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.GeneratedValue;
 import javax.persistence.Id;
 import javax.persistence.OneToOne;
 import javax.persistence.PrimaryKeyJoinColumn;
 import javax.persistence.Table;
 import javax.persistence.Transient;
 import org.hibernate.annotations.GenericGenerator;
 
 @Entity
 @Table(name="tq_site_config")
 public class SiteConfig
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private Integer id;
   private Boolean commentCheck;
   private Boolean commentLogin;
   private Boolean messageCheck;
   private Boolean messageLogin;
   private Boolean messageName;
   private Boolean messageMobile;
   private Boolean messageEmail;
   private Boolean messageAddress;
   private Boolean messageZipcode;
   private Boolean regOpen;
   private Integer regMin;
   private Integer regMax;
   private Boolean regCheck;
   private Integer loginCount;
   private Site site;
 
   public void init()
   {
     if (getCommentCheck() == null) {
       setCommentCheck(Boolean.valueOf(true));
     }
     if (getCommentLogin() == null) {
       setCommentLogin(Boolean.valueOf(false));
     }
     if (getMessageCheck() == null) {
       setMessageCheck(Boolean.valueOf(true));
     }
     if (getMessageLogin() == null) {
       setMessageLogin(Boolean.valueOf(false));
     }
     if (getMessageName() == null) {
       setMessageName(Boolean.valueOf(false));
     }
     if (getMessageMobile() == null) {
       setMessageMobile(Boolean.valueOf(false));
     }
     if (getMessageEmail() == null) {
       setMessageEmail(Boolean.valueOf(false));
     }
     if (getMessageAddress() == null) {
       setMessageAddress(Boolean.valueOf(false));
     }
     if (getMessageZipcode() == null) {
       setMessageZipcode(Boolean.valueOf(false));
     }
     if (getRegOpen() == null) {
       setRegOpen(Boolean.valueOf(true));
     }
     if (getRegCheck() == null)
       setRegCheck(Boolean.valueOf(false));
   }
 
   @Transient
   public boolean getNeedCheck()
   {
     return (getLoginCount() != null) && (getLoginCount().intValue() > 0);
   }
 
   @Id
   @Column(name="config_id", unique=true, nullable=false)
   @GenericGenerator(name="copy", strategy="foreign", parameters={@org.hibernate.annotations.Parameter(name="property", value="site")})
   @GeneratedValue(generator="copy")
   public Integer getId()
   {
     return this.id;
   }
 
   public void setId(Integer id) {
     this.id = id;
   }
   @Column(name="comment_check", nullable=false)
   public Boolean getCommentCheck() {
     return this.commentCheck;
   }
 
   public void setCommentCheck(Boolean commentCheck) {
     this.commentCheck = commentCheck;
   }
   @Column(name="comment_login", nullable=false)
   public Boolean getCommentLogin() {
     return this.commentLogin;
   }
 
   public void setCommentLogin(Boolean commentLogin) {
     this.commentLogin = commentLogin;
   }
   @Column(name="message_check", nullable=false)
   public Boolean getMessageCheck() {
     return this.messageCheck;
   }
 
   public void setMessageCheck(Boolean messageCheck) {
     this.messageCheck = messageCheck;
   }
   @Column(name="message_login", nullable=false)
   public Boolean getMessageLogin() {
     return this.messageLogin;
   }
 
   public void setMessageLogin(Boolean messageLogin) {
     this.messageLogin = messageLogin;
   }
   @Column(name="message_name", nullable=false)
   public Boolean getMessageName() {
     return this.messageName;
   }
 
   public void setMessageName(Boolean messageName) {
     this.messageName = messageName;
   }
   @Column(name="message_mobile", nullable=false)
   public Boolean getMessageMobile() {
     return this.messageMobile;
   }
 
   public void setMessageMobile(Boolean messageMobile) {
     this.messageMobile = messageMobile;
   }
   @Column(name="message_email", nullable=false)
   public Boolean getMessageEmail() {
     return this.messageEmail;
   }
 
   public void setMessageEmail(Boolean messageEmail) {
     this.messageEmail = messageEmail;
   }
   @Column(name="message_address", nullable=false)
   public Boolean getMessageAddress() {
     return this.messageAddress;
   }
 
   public void setMessageAddress(Boolean messageAddress) {
     this.messageAddress = messageAddress;
   }
   @Column(name="message_zipcode", nullable=false)
   public Boolean getMessageZipcode() {
     return this.messageZipcode;
   }
 
   public void setMessageZipcode(Boolean messageZipcode) {
     this.messageZipcode = messageZipcode;
   }
   @Column(name="reg_open", nullable=false)
   public Boolean getRegOpen() {
     return this.regOpen;
   }
 
   public void setRegOpen(Boolean regOpen) {
     this.regOpen = regOpen;
   }
   @Column(name="reg_min", nullable=true, length=10)
   public Integer getRegMin() {
     return this.regMin;
   }
 
   public void setRegMin(Integer regMin) {
     this.regMin = regMin;
   }
   @Column(name="reg_max", nullable=true, length=10)
   public Integer getRegMax() {
     return this.regMax;
   }
 
   public void setRegMax(Integer regMax) {
     this.regMax = regMax;
   }
   @Column(name="reg_check", nullable=false)
   public Boolean getRegCheck() {
     return this.regCheck;
   }
 
   public void setRegCheck(Boolean regCheck) {
     this.regCheck = regCheck;
   }
   @Column(name="login_count", nullable=true, length=10)
   public Integer getLoginCount() {
     return this.loginCount;
   }
 
   public void setLoginCount(Integer loginCount) {
     this.loginCount = loginCount;
   }
   @OneToOne
   @PrimaryKeyJoinColumn
   public Site getSite() { return this.site; }
 
   public void setSite(Site site)
   {
     this.site = site;
   }
 }


 
 
 
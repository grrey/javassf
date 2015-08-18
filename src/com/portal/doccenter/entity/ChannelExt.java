 package com.portal.doccenter.entity;
 
 import java.io.Serializable;
 import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.GeneratedValue;
 import javax.persistence.Id;
 import javax.persistence.OneToOne;
 import javax.persistence.PrimaryKeyJoinColumn;
 import javax.persistence.Table;
 import org.apache.commons.lang.StringUtils;
 import org.hibernate.annotations.GenericGenerator;
 
 @Entity
 @Table(name="tq_channel_ext")
 public class ChannelExt
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private Integer id;
   private String link;
   private String tplChannel;
   private Boolean staticChannel;
   private Boolean staticDoc;
   private Boolean commentControl;
   private Boolean updownControl;
   private Boolean blank;
   private Boolean sign;
   private String title;
   private String keywords;
   private String description;
   private String imgSrc;
   private Channel channel;
 
   public void init()
   {
     if (getBlank() == null) {
       setBlank(Boolean.valueOf(false));
     }
     if (getSign() == null) {
       setSign(Boolean.valueOf(false));
     }
     if (getCommentControl() == null) {
       setCommentControl(Boolean.valueOf(true));
     }
     if (getUpdownControl() == null) {
       setUpdownControl(Boolean.valueOf(true));
     }
     if (getStaticChannel() == null) {
       setStaticChannel(Boolean.valueOf(true));
     }
     if (getStaticDoc() == null) {
       setStaticDoc(Boolean.valueOf(true));
     }
     blankToNull();
   }
 
   public void blankToNull() {
     if (StringUtils.isBlank(getLink())) {
       setLink(null);
     }
     if (StringUtils.isBlank(getTplChannel())) {
       setTplChannel(null);
     }
     if (StringUtils.isBlank(getTitle())) {
       setTitle(null);
     }
     if (StringUtils.isBlank(getKeywords())) {
       setKeywords(null);
     }
     if (StringUtils.isBlank(getDescription()))
       setDescription(null);
   }
 
   @Id
   @Column(name="channel_id", unique=true, nullable=false)
   @GenericGenerator(name="copy", strategy="foreign", parameters={@org.hibernate.annotations.Parameter(name="property", value="channel")})
   @GeneratedValue(generator="copy")
   public Integer getId()
   {
     return this.id;
   }
 
   public void setId(Integer id) {
     this.id = id;
   }
   @Column(name="link", nullable=true, length=150)
   public String getLink() {
     return this.link;
   }
 
   public void setLink(String link) {
     this.link = link;
   }
   @Column(name="tpl_channel", nullable=true, length=100)
   public String getTplChannel() {
     return this.tplChannel;
   }
 
   public void setTplChannel(String tplChannel) {
     this.tplChannel = tplChannel;
   }
   @Column(name="is_static_channel", nullable=false, length=1)
   public Boolean getStaticChannel() {
     return this.staticChannel;
   }
 
   public void setStaticChannel(Boolean staticChannel) {
     this.staticChannel = staticChannel;
   }
   @Column(name="is_static_doc", nullable=false, length=1)
   public Boolean getStaticDoc() {
     return this.staticDoc;
   }
 
   public void setStaticDoc(Boolean staticDoc) {
     this.staticDoc = staticDoc;
   }
   @Column(name="comment_control", nullable=false, length=1)
   public Boolean getCommentControl() {
     return this.commentControl;
   }
 
   public void setCommentControl(Boolean commentControl) {
     this.commentControl = commentControl;
   }
   @Column(name="updown_control", nullable=true, length=1)
   public Boolean getUpdownControl() {
     return this.updownControl;
   }
 
   public void setUpdownControl(Boolean updownControl) {
     this.updownControl = updownControl;
   }
   @Column(name="is_blank", nullable=false, length=1)
   public Boolean getBlank() {
     return this.blank;
   }
 
   public void setBlank(Boolean blank) {
     this.blank = blank;
   }
   @Column(name="is_sign", nullable=false, length=1)
   public Boolean getSign() {
     return this.sign;
   }
 
   public void setSign(Boolean sign) {
     this.sign = sign;
   }
   @Column(name="title", nullable=true, length=100)
   public String getTitle() {
     return this.title;
   }
 
   public void setTitle(String title) {
     this.title = title;
   }
   @Column(name="keywords", nullable=true, length=100)
   public String getKeywords() {
     return this.keywords;
   }
 
   public void setKeywords(String keywords) {
     this.keywords = keywords;
   }
   @Column(name="description", nullable=true, length=255)
   public String getDescription() {
     return this.description;
   }
 
   public void setDescription(String description) {
     this.description = description;
   }
   @Column(name="img_src", nullable=true, length=100)
   public String getImgSrc() {
     return this.imgSrc;
   }
 
   public void setImgSrc(String imgSrc) {
     this.imgSrc = imgSrc;
   }
   @OneToOne
   @PrimaryKeyJoinColumn
   public Channel getChannel() { return this.channel; }
 
   public void setChannel(Channel channel)
   {
     this.channel = channel;
   }
 }


 
 
 
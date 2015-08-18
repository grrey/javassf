 package com.portal.doccenter.entity;
 
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
 import org.apache.commons.lang.StringUtils;
 import org.hibernate.annotations.GenericGenerator;
 
 @Entity
 @Table(name="tq_article_ext")
 public class ArticleExt
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private Integer id;
   private String subTitle;
   private String author;
   private String origin;
   private String originUrl;
   private String tagStr;
   private Boolean showIndex;
   private Boolean redTape;
   private String redtapeOrigin;
   private String description;
   private String tplContent;
   private String link;
   private Boolean commentControl;
   private Boolean updownControl;
   private Date timeDay;
   private Date timeHour;
   private Article article;
 
   public void init()
   {
     if (getShowIndex() == null) {
       setShowIndex(Boolean.valueOf(true));
     }
     if (getRedTape() == null) {
       setRedTape(Boolean.valueOf(false));
     }
     if (getCommentControl() == null) {
       setCommentControl(Boolean.valueOf(true));
     }
     if (getUpdownControl() == null) {
       setUpdownControl(Boolean.valueOf(true));
     }
     if (StringUtils.isBlank(getAuthor())) {
       setAuthor(null);
     }
     if (StringUtils.isBlank(getOrigin())) {
       setOrigin(null);
     }
     if (StringUtils.isBlank(getOriginUrl())) {
       setOriginUrl(null);
     }
     if (StringUtils.isBlank(getDescription())) {
       setDescription(null);
     }
     if (StringUtils.isBlank(getLink())) {
       setLink(null);
     }
     if (StringUtils.isBlank(getTplContent()))
       setTplContent(null);
   }
 
   @Id
   @Column(name="article_id", unique=true, nullable=false)
   @GenericGenerator(name="copy", strategy="foreign", parameters={@org.hibernate.annotations.Parameter(name="property", value="article")})
   @GeneratedValue(generator="copy")
   public Integer getId()
   {
     return this.id;
   }
 
   public void setId(Integer id) {
     this.id = id;
   }
   @Column(name="sub_title", nullable=true, length=100)
   public String getSubTitle() {
     return this.subTitle;
   }
 
   public void setSubTitle(String subTitle) {
     this.subTitle = subTitle;
   }
   @Column(name="author", nullable=true, length=30)
   public String getAuthor() {
     return this.author;
   }
 
   public void setAuthor(String author) {
     this.author = author;
   }
   @Column(name="origin", nullable=true, length=50)
   public String getOrigin() {
     return this.origin;
   }
 
   public void setOrigin(String origin) {
     this.origin = origin;
   }
   @Column(name="origin_url", nullable=true, length=50)
   public String getOriginUrl() {
     return this.originUrl;
   }
 
   public void setOriginUrl(String originUrl) {
     this.originUrl = originUrl;
   }
   @Column(name="tag_str", nullable=true, length=50)
   public String getTagStr() {
     return this.tagStr;
   }
 
   public void setTagStr(String tagStr) {
     this.tagStr = tagStr;
   }
   @Column(name="show_index", nullable=true, length=1)
   public Boolean getShowIndex() {
     return this.showIndex;
   }
 
   public void setShowIndex(Boolean showIndex) {
     this.showIndex = showIndex;
   }
   @Column(name="is_red_tape", nullable=false, length=1)
   public Boolean getRedTape() {
     return this.redTape;
   }
 
   public void setRedTape(Boolean redTape) {
     this.redTape = redTape;
   }
   @Column(name="red_tape_origin", nullable=true, length=100)
   public String getRedtapeOrigin() {
     return this.redtapeOrigin;
   }
 
   public void setRedtapeOrigin(String redtapeOrigin) {
     this.redtapeOrigin = redtapeOrigin;
   }
   @Column(name="description", nullable=true, length=255)
   public String getDescription() {
     return this.description;
   }
 
   public void setDescription(String description) {
     this.description = description;
   }
   @Column(name="tpl_content", nullable=true, length=100)
   public String getTplContent() {
     return this.tplContent;
   }
 
   public void setTplContent(String tplContent) {
     this.tplContent = tplContent;
   }
   @Column(name="link", nullable=true, length=100)
   public String getLink() {
     return this.link;
   }
 
   public void setLink(String link) {
     this.link = link;
   }
   @Column(name="comment_control", nullable=true, length=1)
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
   @Temporal(TemporalType.DATE)
   @Column(name="time_day", nullable=true, length=19)
   public Date getTimeDay() { return this.timeDay; }
 
   public void setTimeDay(Date timeDay)
   {
     this.timeDay = timeDay;
   }
   @Temporal(TemporalType.TIME)
   @Column(name="time_hour", nullable=true, length=19)
   public Date getTimeHour() { return this.timeHour; }
 
   public void setTimeHour(Date timeHour)
   {
     this.timeHour = timeHour;
   }
   @OneToOne
   @PrimaryKeyJoinColumn
   public Article getArticle() { return this.article; }
 
   public void setArticle(Article article)
   {
     this.article = article;
   }
 }


 
 
 
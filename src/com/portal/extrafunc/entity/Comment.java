 package com.portal.extrafunc.entity;
 
 import com.portal.doccenter.entity.Article;
 import com.portal.sysmgr.entity.Site;
 import com.portal.usermgr.entity.User;
 import java.io.Serializable;
 import java.sql.Timestamp;
 import java.util.Date;
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
 import javax.persistence.Temporal;
 import javax.persistence.TemporalType;
 import javax.persistence.Transient;
 
 @Entity
 @Table(name="tq_comment")
 public class Comment
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private Integer id;
   private Integer ups;
   private Date createTime;
   private Date lastTime;
   private Boolean checked;
   private CommentExt commentExt;
   private Comment parent;
   private Article doc;
   private User user;
   private Site site;
 
   public void init()
   {
     if (getUps() == null) {
       setUps(Integer.valueOf(0));
     }
     if (getCreateTime() == null) {
       setCreateTime(new Timestamp(System.currentTimeMillis()));
     }
     if (getLastTime() == null) {
       setLastTime(new Timestamp(System.currentTimeMillis()));
     }
     if (getChecked() == null)
       setChecked(Boolean.valueOf(!getSite().getCommentCheck().booleanValue()));
   }
 
   @Transient
   public String getIp() {
     CommentExt ext = getCommentExt();
     if (ext != null) {
       return ext.getIp();
     }
     return null;
   }
   @Transient
   public String getContent() {
     CommentExt ext = getCommentExt();
     if (ext != null) {
       return ext.getContent().trim();
     }
     return null;
   }
 
   @Id
   @Column(name="comment_id", unique=true, nullable=false)
   @TableGenerator(name="tg_comment", pkColumnValue="tq_comment", table="tq_gen_table", pkColumnName="tg_gen_name", valueColumnName="tq_gen_value", initialValue=1, allocationSize=1)
   @GeneratedValue(strategy=GenerationType.TABLE, generator="tg_comment")
   public Integer getId()
   {
     return this.id;
   }
 
   public void setId(Integer id) {
     this.id = id;
   }
   @Column(name="ups", nullable=false, length=5)
   public Integer getUps() {
     return this.ups;
   }
 
   public void setUps(Integer ups) {
     this.ups = ups;
   }
   @Temporal(TemporalType.TIMESTAMP)
   @Column(name="create_time", nullable=false, length=19)
   public Date getCreateTime() { return this.createTime; }
 
   public void setCreateTime(Date createTime)
   {
     this.createTime = createTime;
   }
   @Temporal(TemporalType.TIMESTAMP)
   @Column(name="last_time", nullable=false, length=19)
   public Date getLastTime() { return this.lastTime; }
 
   public void setLastTime(Date lastTime)
   {
     this.lastTime = lastTime;
   }
   @Column(name="is_checked", nullable=false, length=1)
   public Boolean getChecked() {
     return this.checked;
   }
 
   public void setChecked(Boolean checked) {
     this.checked = checked;
   }
   @OneToOne(cascade={javax.persistence.CascadeType.REMOVE}, fetch=FetchType.LAZY)
   @PrimaryKeyJoinColumn
   public CommentExt getCommentExt() { return this.commentExt; }
 
   public void setCommentExt(CommentExt commentExt)
   {
     this.commentExt = commentExt;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="parent_id", nullable=true)
   public Comment getParent() { return this.parent; }
 
   public void setParent(Comment parent)
   {
     this.parent = parent;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="doc_id", nullable=false)
   public Article getDoc() { return this.doc; }
 
   public void setDoc(Article doc)
   {
     this.doc = doc;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="user_id", nullable=true)
   public User getUser() { return this.user; }
 
   public void setUser(User user)
   {
     this.user = user;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="site_id", nullable=false)
   public Site getSite() { return this.site; }
 
   public void setSite(Site site)
   {
     this.site = site;
   }
 }


 
 
 
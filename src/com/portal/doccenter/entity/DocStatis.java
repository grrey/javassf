 package com.portal.doccenter.entity;
 
 import java.io.Serializable;
 import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.GeneratedValue;
 import javax.persistence.Id;
 import javax.persistence.OneToOne;
 import javax.persistence.PrimaryKeyJoinColumn;
 import javax.persistence.Table;
 import org.hibernate.annotations.Formula;
 import org.hibernate.annotations.GenericGenerator;
 
 @Entity
 @Table(name="tq_doc_statistics")
 public class DocStatis
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private Integer id;
   private Integer viewsCount;
   private Integer commentCount;
   private Integer ups;
   private Integer treads;
   private Article doc;
 
   public void init()
   {
     if (getViewsCount() == null) {
       setViewsCount(Integer.valueOf(0));
     }
     if (getCommentCount() == null) {
       setCommentCount(Integer.valueOf(0));
     }
     if (getUps() == null) {
       setUps(Integer.valueOf(0));
     }
     if (getTreads() == null)
       setTreads(Integer.valueOf(0));
   }
 
   @Id
   @Column(name="doc_id", unique=true, nullable=false)
   @GenericGenerator(name="copy", strategy="foreign", parameters={@org.hibernate.annotations.Parameter(name="property", value="doc")})
   @GeneratedValue(generator="copy")
   public Integer getId()
   {
     return this.id;
   }
 
   public void setId(Integer id) {
     this.id = id;
   }
   @Column(name="views_count", nullable=false, length=10)
   public Integer getViewsCount() {
     return this.viewsCount;
   }
 
   public void setViewsCount(Integer viewsCount) {
     this.viewsCount = viewsCount;
   }
   @Formula("(select count(c.comment_id) from tq_comment c where c.is_checked=1 and c.doc_id=doc_id)")
   public Integer getCommentCount() {
     return this.commentCount;
   }
 
   public void setCommentCount(Integer commentCount) {
     this.commentCount = commentCount;
   }
   @Column(name="ups", nullable=false, length=10)
   public Integer getUps() {
     return this.ups;
   }
 
   public void setUps(Integer ups) {
     this.ups = ups;
   }
   @Column(name="treads", nullable=false, length=10)
   public Integer getTreads() {
     return this.treads;
   }
 
   public void setTreads(Integer treads) {
     this.treads = treads;
   }
   @OneToOne
   @PrimaryKeyJoinColumn
   public Article getDoc() { return this.doc; }
 
   public void setDoc(Article doc)
   {
     this.doc = doc;
   }
 }


 
 
 
 package com.portal.extrafunc.entity;
 
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
 @Table(name="tq_posts_txt")
 public class PostsTxt
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private Integer id;
   private String content;
   private Posts posts;
 
   @Id
   @Column(name="posts_id", unique=true, nullable=false)
   @GenericGenerator(name="copy", strategy="foreign", parameters={@org.hibernate.annotations.Parameter(name="property", value="posts")})
   @GeneratedValue(generator="copy")
   public Integer getId()
   {
     return this.id;
   }
 
   public void setId(Integer id) {
     this.id = id;
   }
   @Lob
   @Column(name="content", nullable=true)
   public String getContent() { return this.content; }
 
   public void setContent(String content)
   {
     this.content = content;
   }
   @OneToOne
   @PrimaryKeyJoinColumn
   public Posts getPosts() { return this.posts; }
 
   public void setPosts(Posts posts)
   {
     this.posts = posts;
   }
 }


 
 
 
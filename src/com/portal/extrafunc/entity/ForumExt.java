 package com.portal.extrafunc.entity;
 
 import java.io.Serializable;
 import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.GeneratedValue;
 import javax.persistence.Id;
 import javax.persistence.OneToOne;
 import javax.persistence.PrimaryKeyJoinColumn;
 import javax.persistence.Table;
 import org.hibernate.annotations.GenericGenerator;
 
 @Entity
 @Table(name="tq_forum_ext")
 public class ForumExt
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private Integer id;
   private String keywords;
   private String description;
   private String rule;
   private String tplContent;
   private Forum forum;
 
   @Id
   @Column(name="forum_id", unique=true, nullable=false)
   @GenericGenerator(name="copy", strategy="foreign", parameters={@org.hibernate.annotations.Parameter(name="property", value="forum")})
   @GeneratedValue(generator="copy")
   public Integer getId()
   {
     return this.id;
   }
 
   public void setId(Integer id) {
     this.id = id;
   }
   @Column(name="keywords", nullable=true, length=200)
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
   @Column(name="rule", nullable=true, length=255)
   public String getRule() {
     return this.rule;
   }
 
   public void setRule(String rule) {
     this.rule = rule;
   }
   @Column(name="tpl_content", nullable=true, length=150)
   public String getTplContent() {
     return this.tplContent;
   }
 
   public void setTplContent(String tplContent) {
     this.tplContent = tplContent;
   }
   @OneToOne
   @PrimaryKeyJoinColumn
   public Forum getForum() { return this.forum; }
 
   public void setForum(Forum forum)
   {
     this.forum = forum;
   }
 }


 
 
 
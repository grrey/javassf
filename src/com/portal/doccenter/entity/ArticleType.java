 package com.portal.doccenter.entity;
 
 import java.io.Serializable;
 import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.GeneratedValue;
 import javax.persistence.GenerationType;
 import javax.persistence.Id;
 import javax.persistence.Table;
 import javax.persistence.TableGenerator;
 
 @Entity
 @Table(name="tq_article_type")
 public class ArticleType
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private Integer id;
   private String name;
   private Boolean hasImage;
   private Boolean disabled;
 
   @Id
   @Column(name="type_id", unique=true, nullable=false)
   @TableGenerator(name="tg_article_type", pkColumnValue="tq_article_type", table="tq_gen_table", pkColumnName="tg_gen_name", valueColumnName="tq_gen_value", initialValue=1, allocationSize=1)
   @GeneratedValue(strategy=GenerationType.TABLE, generator="tg_article_type")
   public Integer getId()
   {
     return this.id;
   }
 
   public void setId(Integer id) {
     this.id = id;
   }
   @Column(name="type_name", nullable=false, length=20)
   public String getName() {
     return this.name;
   }
 
   public void setName(String name) {
     this.name = name;
   }
   @Column(name="has_image", nullable=false, length=1)
   public Boolean getHasImage() {
     return this.hasImage;
   }
 
   public void setHasImage(Boolean hasImage) {
     this.hasImage = hasImage;
   }
   @Column(name="is_disabled", nullable=false, length=1)
   public Boolean getDisabled() {
     return this.disabled;
   }
 
   public void setDisabled(Boolean disabled) {
     this.disabled = disabled;
   }
 }


 
 
 
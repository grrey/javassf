 package com.portal.doccenter.entity;
 
 import java.io.Serializable;
 import javax.persistence.Column;
 import javax.persistence.Embeddable;
 import javax.persistence.Transient;
 
 @Embeddable
 public class ArticlePicture
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private String imgPath;
   private String description;
   private Boolean thumb;
   private String style;
 
   @Transient
   public boolean getCover()
   {
     return (getThumb() != null) && (!getThumb().booleanValue()) && 
       (getStyle() != null) && 
       (getStyle().indexOf(",1,") > -1);
   }
 
   @Column(name="img_path", nullable=false, length=100)
   public String getImgPath()
   {
     return this.imgPath;
   }
 
   public void setImgPath(String imgPath) {
     this.imgPath = imgPath;
   }
   @Column(name="description", nullable=true, length=255)
   public String getDescription() {
     return this.description;
   }
 
   public void setDescription(String description) {
     this.description = description;
   }
   @Column(name="is_thumb", nullable=true, length=1)
   public Boolean getThumb() {
     return this.thumb;
   }
 
   public void setThumb(Boolean thumb) {
     this.thumb = thumb;
   }
   @Column(name="style", nullable=true, length=50)
   public String getStyle() {
     return this.style;
   }
 
   public void setStyle(String style) {
     this.style = style;
   }
 }


 
 
 
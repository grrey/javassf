 package com.portal.extrafunc.entity;
 
 import java.io.Serializable;
 import javax.persistence.Column;
 import javax.persistence.Embeddable;
 
 @Embeddable
 public class PostsAttach
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private String name;
   private String description;
   private String filePath;
   private String fileName;
   private Integer fileSize;
   private Boolean img;
 
   @Column(name="name", nullable=true, length=100)
   public String getName()
   {
     return this.name;
   }
 
   public void setName(String name) {
     this.name = name;
   }
   @Column(name="description", nullable=true, length=255)
   public String getDescription() {
     return this.description;
   }
 
   public void setDescription(String description) {
     this.description = description;
   }
   @Column(name="file_path", nullable=false, length=100)
   public String getFilePath() {
     return this.filePath;
   }
 
   public void setFilePath(String filePath) {
     this.filePath = filePath;
   }
   @Column(name="file_name", nullable=true, length=50)
   public String getFileName() {
     return this.fileName;
   }
 
   public void setFileName(String fileName) {
     this.fileName = fileName;
   }
   @Column(name="file_size", nullable=true, length=10)
   public Integer getFileSize() {
     return this.fileSize;
   }
 
   public void setFileSize(Integer fileSize) {
     this.fileSize = fileSize;
   }
   @Column(name="is_img", nullable=false, length=1)
   public Boolean getImg() {
     return this.img;
   }
 
   public void setImg(Boolean img) {
     this.img = img;
   }
 }


 
 
 
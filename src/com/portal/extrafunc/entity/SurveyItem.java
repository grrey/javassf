 package com.portal.extrafunc.entity;
 
 import java.io.Serializable;
 import javax.persistence.Column;
 import javax.persistence.Embeddable;
 
 @Embeddable
 public class SurveyItem
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private String name;
   private Integer votes;
   private Integer priority;
 
   public void init()
   {
     if (getVotes() == null) {
       setVotes(Integer.valueOf(0));
     }
     if (getPriority() == null)
       setPriority(Integer.valueOf(10));
   }
 
   @Column(name="name", nullable=false, length=50)
   public String getName()
   {
     return this.name;
   }
 
   public void setName(String name) {
     this.name = name;
   }
   @Column(name="votes", nullable=false, length=10)
   public Integer getVotes() {
     return this.votes;
   }
 
   public void setVotes(Integer votes) {
     this.votes = votes;
   }
   @Column(name="priority", nullable=false, length=10)
   public Integer getPriority() {
     return this.priority;
   }
 
   public void setPriority(Integer priority) {
     this.priority = priority;
   }
 }


 
 
 
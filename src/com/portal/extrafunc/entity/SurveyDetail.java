 package com.portal.extrafunc.entity;
 
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
 import javax.persistence.Table;
 import javax.persistence.TableGenerator;
 import javax.persistence.Temporal;
 import javax.persistence.TemporalType;
 
 @Entity
 @Table(name="tq_survey_detail")
 public class SurveyDetail
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private Integer id;
   private String content;
   private Date createTime;
   private SurveyTheme survey;
   private User user;
 
   public void init()
   {
     if (getCreateTime() == null)
       setCreateTime(new Timestamp(System.currentTimeMillis()));
   }
 
   @Id
   @Column(name="detail_id", unique=true, nullable=false)
   @TableGenerator(name="tg_survey_detail", pkColumnValue="tq_survey_detail", table="tq_gen_table", pkColumnName="tg_gen_name", valueColumnName="tq_gen_value", initialValue=1, allocationSize=1)
   @GeneratedValue(strategy=GenerationType.TABLE, generator="tg_survey_detail")
   public Integer getId()
   {
     return this.id;
   }
 
   public void setId(Integer id) {
     this.id = id;
   }
   @Column(name="content", nullable=true, length=1000)
   public String getContent() {
     return this.content;
   }
 
   public void setContent(String content) {
     this.content = content;
   }
   @Temporal(TemporalType.TIMESTAMP)
   @Column(name="create_time", nullable=false, length=19)
   public Date getCreateTime() { return this.createTime; }
 
   public void setCreateTime(Date createTime)
   {
     this.createTime = createTime;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="survey_id", nullable=false)
   public SurveyTheme getSurvey() { return this.survey; }
 
   public void setSurvey(SurveyTheme survey)
   {
     this.survey = survey;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="user_id", nullable=true)
   public User getUser() { return this.user; }
 
   public void setUser(User user)
   {
     this.user = user;
   }
 }


 
 
 
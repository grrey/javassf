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
 @Table(name="tq_question_detail")
 public class QuestionDetail
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private Integer id;
   private String ip;
   private Date createTime;
   private Questionnaire question;
   private User user;
 
   public void init()
   {
     if (getCreateTime() == null)
       setCreateTime(new Timestamp(System.currentTimeMillis()));
   }
 
   @Id
   @Column(name="detail_id", unique=true, nullable=false)
   @TableGenerator(name="tg_question_detail", pkColumnValue="tq_question_detail", table="tq_gen_table", pkColumnName="tg_gen_name", valueColumnName="tq_gen_value", initialValue=1, allocationSize=1)
   @GeneratedValue(strategy=GenerationType.TABLE, generator="tg_question_detail")
   public Integer getId()
   {
     return this.id;
   }
 
   public void setId(Integer id) {
     this.id = id;
   }
   @Column(name="ip", nullable=false, length=50)
   public String getIp() {
     return this.ip;
   }
 
   public void setIp(String ip) {
     this.ip = ip;
   }
   @Temporal(TemporalType.TIMESTAMP)
   @Column(name="create_time", nullable=false, length=19)
   public Date getCreateTime() { return this.createTime; }
 
   public void setCreateTime(Date createTime)
   {
     this.createTime = createTime;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="question_id", nullable=false)
   public Questionnaire getQuestion() { return this.question; }
 
   public void setQuestion(Questionnaire question)
   {
     this.question = question;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="user_id", nullable=true)
   public User getUser() { return this.user; }
 
   public void setUser(User user)
   {
     this.user = user;
   }
 }


 
 
 
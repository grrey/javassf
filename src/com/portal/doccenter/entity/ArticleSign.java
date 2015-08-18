 package com.portal.doccenter.entity;
 
 import com.portal.usermgr.entity.Admin;
 import com.portal.usermgr.entity.Depart;
 import java.io.Serializable;
 import java.sql.Timestamp;
 import java.text.ParseException;
 import java.text.SimpleDateFormat;
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
 import javax.persistence.Transient;
 
 @Entity
 @Table(name="tq_article_sign")
 public class ArticleSign
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private Integer id;
   private Date signTime;
   private Article article;
   private Admin admin;
   private Depart depart;
 
   public void init()
   {
     if (getSignTime() == null)
       setSignTime(new Timestamp(System.currentTimeMillis()));
   }
 
   @Transient
   public boolean isAfterDay() {
     return isAfterDay(2);
   }
   @Transient
   public boolean isAfterDay(int day) {
     Date d = getArticle().getReleaseDate();
     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
     String dstr = "2014-02-20";
     Date date = null;
     try {
       date = sdf.parse(dstr);
     } catch (ParseException e) {
       e.printStackTrace();
     }
     if (d.before(date)) {
       return false;
     }
     long s = getSignTime().getTime() - d.getTime();
     s /= 1000L;
 
     return s > 86400 * day;
   }
 
   @Id
   @Column(name="sign_id", unique=true, nullable=false)
   @TableGenerator(name="tg_article_sign", pkColumnValue="tq_article_sign", table="tq_gen_table", pkColumnName="tg_gen_name", valueColumnName="tq_gen_value", initialValue=1, allocationSize=1)
   @GeneratedValue(strategy=GenerationType.TABLE, generator="tg_article_sign")
   public Integer getId()
   {
     return this.id;
   }
 
   public void setId(Integer id) {
     this.id = id;
   }
   @Temporal(TemporalType.TIMESTAMP)
   @Column(name="sign_time", nullable=false, length=19)
   public Date getSignTime() { return this.signTime; }
 
   public void setSignTime(Date signTime)
   {
     this.signTime = signTime;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="article_id", nullable=false)
   public Article getArticle() { return this.article; }
 
   public void setArticle(Article article)
   {
     this.article = article;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="admin_id", nullable=false)
   public Admin getAdmin() { return this.admin; }
 
   public void setAdmin(Admin admin)
   {
     this.admin = admin;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="depart_id", nullable=false)
   public Depart getDepart() { return this.depart; }
 
   public void setDepart(Depart depart)
   {
     this.depart = depart;
   }
 }


 
 
 
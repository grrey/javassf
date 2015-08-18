 package com.portal.sysmgr.entity;
 
 import java.io.Serializable;
 import java.sql.Timestamp;
 import java.util.Date;
 import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.GeneratedValue;
 import javax.persistence.GenerationType;
 import javax.persistence.Id;
 import javax.persistence.Table;
 import javax.persistence.TableGenerator;
 import javax.persistence.Temporal;
 import javax.persistence.TemporalType;
 
 @Entity
 @Table(name="tq_database_config")
 public class DatabaseConfig
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private Integer id;
   private Integer inter;
   private Integer backupTime;
   private Date preTime;
 
   public void init()
   {
     if (getBackupTime() == null) {
       setBackupTime(Integer.valueOf(0));
     }
     if (getInter() == null) {
       setInter(Integer.valueOf(15));
     }
     if (getPreTime() == null)
       setPreTime(new Timestamp(System.currentTimeMillis()));
   }
 
   @Id
   @Column(name="config_id", unique=true, nullable=false)
   @TableGenerator(name="tg_database_config", pkColumnValue="tq_database_config", table="tq_gen_table", pkColumnName="tg_gen_name", valueColumnName="tq_gen_value", initialValue=1, allocationSize=1)
   @GeneratedValue(strategy=GenerationType.TABLE, generator="tg_database_config")
   public Integer getId()
   {
     return this.id;
   }
 
   public void setId(Integer id) {
     this.id = id;
   }
   @Column(name="inter", nullable=false, length=11)
   public Integer getInter() {
     return this.inter;
   }
 
   public void setInter(Integer inter) {
     this.inter = inter;
   }
   @Column(name="backup_time", nullable=false, length=11)
   public Integer getBackupTime() {
     return this.backupTime;
   }
 
   public void setBackupTime(Integer backupTime) {
     this.backupTime = backupTime;
   }
   @Temporal(TemporalType.TIMESTAMP)
   @Column(name="pre_time", nullable=false, length=19)
   public Date getPreTime() { return this.preTime; }
 
   public void setPreTime(Date preTime)
   {
     this.preTime = preTime;
   }
 }


 
 
 
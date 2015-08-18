 package com.portal.datacenter.commdata.entity;
 
 import java.io.Serializable;
 import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.GeneratedValue;
 import javax.persistence.GenerationType;
 import javax.persistence.Id;
 import javax.persistence.Table;
 import javax.persistence.TableGenerator;
 
 @Entity
 @Table(name="tq_profess_post")
 public class ProfessPost
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private Integer id;
   private String code;
   private String name;
 
   @Id
   @Column(name="post_id", unique=true, nullable=false)
   @TableGenerator(name="tg_profess_post", pkColumnValue="tq_profess_post", table="tq_gen_table", pkColumnName="tg_gen_name", valueColumnName="tq_gen_value", initialValue=1, allocationSize=1)
   @GeneratedValue(strategy=GenerationType.TABLE, generator="tg_profess_post")
   public Integer getId()
   {
     return this.id;
   }
 
   public void setId(Integer id) {
     this.id = id;
   }
   @Column(name="post_code", nullable=false, length=10)
   public String getCode() {
     return this.code;
   }
 
   public void setCode(String code) {
     this.code = code;
   }
   @Column(name="post_name", nullable=false, length=50)
   public String getName() {
     return this.name;
   }
 
   public void setName(String name) {
     this.name = name;
   }
 }

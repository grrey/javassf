 package com.portal.datacenter.commdata.entity;
 
 import java.io.Serializable;
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
 
 @Entity
 @Table(name="tq_metier")
 public class Metier
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private Integer id;
   private String code;
   private String name;
   private Metier parent;
 
   @Id
   @Column(name="metier_id", unique=true, nullable=false)
   @TableGenerator(name="tg_metier", pkColumnValue="tq_metier", table="tq_gen_table", pkColumnName="tg_gen_name", valueColumnName="tq_gen_value", initialValue=1, allocationSize=1)
   @GeneratedValue(strategy=GenerationType.TABLE, generator="tg_metier")
   public Integer getId()
   {
     return this.id;
   }
 
   public void setId(Integer id) {
     this.id = id;
   }
   @Column(name="metier_code", nullable=false, length=10)
   public String getCode() {
     return this.code;
   }
 
   public void setCode(String code) {
     this.code = code;
   }
   @Column(name="metier_name", nullable=false, length=50)
   public String getName() {
     return this.name;
   }
 
   public void setName(String name) {
     this.name = name;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="parent_id", nullable=true)
   public Metier getParent() { return this.parent; }
 
   public void setParent(Metier parent)
   {
     this.parent = parent;
   }
 }

 package com.portal.sysmgr.entity;
 
 import java.io.Serializable;
 import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.GeneratedValue;
 import javax.persistence.Id;
 import javax.persistence.OneToOne;
 import javax.persistence.PrimaryKeyJoinColumn;
 import javax.persistence.Table;
 import org.hibernate.annotations.GenericGenerator;
 
 @Entity
 @Table(name="tq_thirdparty_config")
 public class ThirdpartyConfig
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private Integer id;
   private String qqKey;
   private String sinaKey;
   private Site site;
 
   @Id
   @Column(name="config_id", unique=true, nullable=false)
   @GenericGenerator(name="copy", strategy="foreign", parameters={@org.hibernate.annotations.Parameter(name="property", value="site")})
   @GeneratedValue(generator="copy")
   public Integer getId()
   {
     return this.id;
   }
 
   public void setId(Integer id) {
     this.id = id;
   }
   @Column(name="qq_key", nullable=true, length=100)
   public String getQqKey() {
     return this.qqKey;
   }
 
   public void setQqKey(String qqKey) {
     this.qqKey = qqKey;
   }
   @Column(name="sina_key", nullable=true, length=100)
   public String getSinaKey() {
     return this.sinaKey;
   }
 
   public void setSinaKey(String sinaKey) {
     this.sinaKey = sinaKey;
   }
   @OneToOne
   @PrimaryKeyJoinColumn
   public Site getSite() { return this.site; }
 
   public void setSite(Site site)
   {
     this.site = site;
   }
 }


 
 
 
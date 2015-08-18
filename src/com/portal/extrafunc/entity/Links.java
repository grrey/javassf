 package com.portal.extrafunc.entity;
 
 import com.portal.sysmgr.entity.Site;
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
 @Table(name="tq_links")
 public class Links
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private Integer id;
   private String name;
   private String icon;
   private String url;
   private Integer priority;
   private Boolean show;
   private Boolean showIcon;
   private LinksType type;
   private Site site;
 
   public void init()
   {
     if (getPriority() == null) {
       setPriority(Integer.valueOf(10));
     }
     if (getShow() == null) {
       setShow(Boolean.valueOf(true));
     }
     if (getShowIcon() == null)
       setShowIcon(Boolean.valueOf(false));
   }
 
   @Id
   @Column(name="links_id", unique=true, nullable=false)
   @TableGenerator(name="tg_links", pkColumnValue="tq_links", table="tq_gen_table", pkColumnName="tg_gen_name", valueColumnName="tq_gen_value", initialValue=1, allocationSize=1)
   @GeneratedValue(strategy=GenerationType.TABLE, generator="tg_links")
   public Integer getId()
   {
     return this.id;
   }
 
   public void setId(Integer id) {
     this.id = id;
   }
   @Column(name="links_name", nullable=false, length=50)
   public String getName() {
     return this.name;
   }
 
   public void setName(String name) {
     this.name = name;
   }
   @Column(name="links_icon", nullable=true, length=100)
   public String getIcon() {
     return this.icon;
   }
 
   public void setIcon(String icon) {
     this.icon = icon;
   }
   @Column(name="links_url", nullable=true, length=100)
   public String getUrl() {
     return this.url;
   }
 
   public void setUrl(String url) {
     this.url = url;
   }
   @Column(name="priority", nullable=false, length=10)
   public Integer getPriority() {
     return this.priority;
   }
 
   public void setPriority(Integer priority) {
     this.priority = priority;
   }
   @Column(name="is_show", nullable=false, length=1)
   public Boolean getShow() {
     return this.show;
   }
 
   public void setShow(Boolean show) {
     this.show = show;
   }
   @Column(name="show_icon", nullable=false, length=1)
   public Boolean getShowIcon() {
     return this.showIcon;
   }
 
   public void setShowIcon(Boolean showIcon) {
     this.showIcon = showIcon;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="type_id", nullable=false)
   public LinksType getType() { return this.type; }
 
   public void setType(LinksType type)
   {
     this.type = type;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="site_id", nullable=false)
   public Site getSite() { return this.site; }
 
   public void setSite(Site site)
   {
     this.site = site;
   }
 }


 
 
 
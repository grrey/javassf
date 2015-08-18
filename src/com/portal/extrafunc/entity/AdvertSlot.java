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
 import javax.persistence.Transient;
 
 @Entity
 @Table(name="tq_advert_slot")
 public class AdvertSlot
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   public static final Short FIXED = Short.valueOf((short) 1);
 
   public static final Short FLOAT = Short.valueOf((short) 2);
 
   public static final Short POP = Short.valueOf((short) 3);
 
   public static final Short COUPLET = Short.valueOf((short) 4);
   private Integer id;
   private String name;
   private Short slotType;
   private Integer height;
   private Integer width;
   private Integer remain;
   private Boolean scrollbar;
   private String sexplain;
   private Boolean idleholder;
   private Boolean rotation;
   private Site site;
 
   public void init()
   {
     if (getRemain() == null) {
       setRemain(Integer.valueOf(0));
     }
     if (getScrollbar() == null) {
       setScrollbar(Boolean.valueOf(false));
     }
     if (getRotation() == null)
       setRotation(Boolean.valueOf(true));
   }
 
   @Transient
   public String getSlotTypeString() {
     if (getSlotType().equals(FIXED))
       return "固定";
     if (getSlotType().equals(FLOAT))
       return "漂浮";
     if (getSlotType().equals(POP)) {
       return "弹窗";
     }
     return "对联";
   }
 
   @Id
   @Column(name="slot_id", unique=true, nullable=false)
   @TableGenerator(name="tg_advert_slot", pkColumnValue="tq_advert_slot", table="tq_gen_table", pkColumnName="tg_gen_name", valueColumnName="tq_gen_value", initialValue=1, allocationSize=1)
   @GeneratedValue(strategy=GenerationType.TABLE, generator="tg_advert_slot")
   public Integer getId()
   {
     return this.id;
   }
 
   public void setId(Integer id) {
     this.id = id;
   }
   @Column(name="name", nullable=false, length=30)
   public String getName() {
     return this.name;
   }
 
   public void setName(String name) {
     this.name = name;
   }
   @Column(name="slot_type", nullable=false, length=5)
   public Short getSlotType() {
     return this.slotType;
   }
 
   public void setSlotType(Short slotType) {
     this.slotType = slotType;
   }
   @Column(name="height", nullable=true, length=10)
   public Integer getHeight() {
     return this.height;
   }
 
   public void setHeight(Integer height) {
     this.height = height;
   }
   @Column(name="width", nullable=true, length=10)
   public Integer getWidth() {
     return this.width;
   }
 
   public void setWidth(Integer width) {
     this.width = width;
   }
   @Column(name="remain", nullable=true, length=10)
   public Integer getRemain() {
     return this.remain;
   }
 
   public void setRemain(Integer remain) {
     this.remain = remain;
   }
   @Column(name="scrollbar", nullable=true, length=1)
   public Boolean getScrollbar() {
     return this.scrollbar;
   }
 
   public void setScrollbar(Boolean scrollbar) {
     this.scrollbar = scrollbar;
   }
   @Column(name="sexplain", nullable=true, length=500)
   public String getSexplain() {
     return this.sexplain;
   }
 
   public void setSexplain(String sexplain) {
     this.sexplain = sexplain;
   }
   @Column(name="idleholder", nullable=true, length=1)
   public Boolean getIdleholder() {
     return this.idleholder;
   }
 
   public void setIdleholder(Boolean idleholder) {
     this.idleholder = idleholder;
   }
   @Column(name="rotation", nullable=false, length=1)
   public Boolean getRotation() {
     return this.rotation;
   }
 
   public void setRotation(Boolean rotation) {
     this.rotation = rotation;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="site_id", nullable=false)
   public Site getSite() { return this.site; }
 
   public void setSite(Site site)
   {
     this.site = site;
   }
 }


 
 
 
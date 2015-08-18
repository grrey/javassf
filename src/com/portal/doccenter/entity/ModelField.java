 package com.portal.doccenter.entity;
 
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
 import org.apache.commons.lang.StringUtils;
 
 @Entity
 @Table(name="tq_model_field")
 public class ModelField
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private Integer id;
   private String name;
   private String label;
   private Integer priority;
   private String size;
   private String maxlength;
   private String width;
   private String height;
   private String tip;
   private String valueList;
   private Integer dataType;
   private Boolean required;
   private Boolean single;
   private Boolean economy;
   private Boolean show;
   private Model model;
 
   public void init()
   {
     if (getSingle() == null) {
       setSingle(Boolean.valueOf(true));
     }
     if (getEconomy() == null) {
       setEconomy(Boolean.valueOf(false));
     }
     if (getShow() == null) {
       setShow(Boolean.valueOf(true));
     }
     if (getRequired() == null)
       setRequired(Boolean.valueOf(true));
   }
 
   @Transient
   public String getDataTypeString() {
     int dataType = getDataType().intValue();
     if (dataType == 1) {
       return "文本输入框";
     }
     if (dataType == 2) {
       return "多行文本框";
     }
     if (dataType == 3) {
       return "编辑器";
     }
     if (dataType == 4) {
       return "下拉框";
     }
     if (dataType == 5) {
       return "数字";
     }
     if (dataType == 6) {
       return "日期";
     }
     if (dataType == 7) {
       return "单选框";
     }
     if (dataType == 8) {
       return "复选框";
     }
     if (dataType == 9) {
       return "关联";
     }
     return "关联";
   }
 
   public void emptyToNull()
   {
     if (StringUtils.isBlank(getSize()))
       setSize(null);
   }
 
   @Id
   @Column(name="field_id", unique=true, nullable=false)
   @TableGenerator(name="tg_model_field", pkColumnValue="tq_model_field", table="tq_gen_table", pkColumnName="tg_gen_name", valueColumnName="tq_gen_value", initialValue=1, allocationSize=1)
   @GeneratedValue(strategy=GenerationType.TABLE, generator="tg_model_field")
   public Integer getId()
   {
     return this.id;
   }
 
   public void setId(Integer id) {
     this.id = id;
   }
   @Column(name="name", nullable=false, length=50)
   public String getName() {
     return this.name;
   }
 
   public void setName(String name) {
     this.name = name;
   }
   @Column(name="label", nullable=false, length=100)
   public String getLabel() {
     return this.label;
   }
 
   public void setLabel(String label) {
     this.label = label;
   }
   @Column(name="priority", nullable=true, length=10)
   public Integer getPriority() {
     return this.priority;
   }
 
   public void setPriority(Integer priority) {
     this.priority = priority;
   }
   @Column(name="text_size", nullable=true, length=20)
   public String getSize() {
     return this.size;
   }
 
   public void setSize(String size) {
     this.size = size;
   }
   @Column(name="text_maxlength", nullable=true, length=10)
   public String getMaxlength() {
     return this.maxlength;
   }
 
   public void setMaxlength(String maxlength) {
     this.maxlength = maxlength;
   }
   @Column(name="width", nullable=true, length=10)
   public String getWidth() {
     return this.width;
   }
 
   public void setWidth(String width) {
     this.width = width;
   }
   @Column(name="height", nullable=true, length=3)
   public String getHeight() {
     return this.height;
   }
 
   public void setHeight(String height) {
     this.height = height;
   }
   @Column(name="tip", nullable=true, length=255)
   public String getTip() {
     return this.tip;
   }
 
   public void setTip(String tip) {
     this.tip = tip;
   }
   @Column(name="data_type", nullable=false, length=10)
   public Integer getDataType() {
     return this.dataType;
   }
 
   public void setDataType(Integer dataType) {
     this.dataType = dataType;
   }
   @Column(name="is_required", nullable=false, length=1)
   public Boolean getRequired() {
     return this.required;
   }
 
   public void setRequired(Boolean required) {
     this.required = required;
   }
   @Column(name="is_single", nullable=false, length=1)
   public Boolean getSingle() {
     return this.single;
   }
 
   public void setSingle(Boolean single) {
     this.single = single;
   }
   @Column(name="is_economy", nullable=false, length=1)
   public Boolean getEconomy() {
     return this.economy;
   }
 
   public void setEconomy(Boolean economy) {
     this.economy = economy;
   }
   @Column(name="is_show", nullable=false, length=1)
   public Boolean getShow() {
     return this.show;
   }
 
   public void setShow(Boolean show) {
     this.show = show;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="model_id", nullable=false)
   public Model getModel() { return this.model; }
 
   public void setModel(Model model)
   {
     this.model = model;
   }
   @Column(name="value_list", nullable=true, length=255)
   public String getValueList() {
     return this.valueList;
   }
 
   public void setValueList(String valueList) {
     this.valueList = valueList;
   }
 }


 
 
 
 package com.portal.extrafunc.entity;
 
 import java.io.Serializable;
 import java.util.HashSet;
 import java.util.Set;
 import javax.persistence.CollectionTable;
 import javax.persistence.Column;
 import javax.persistence.ElementCollection;
 import javax.persistence.Entity;
 import javax.persistence.FetchType;
 import javax.persistence.GeneratedValue;
 import javax.persistence.GenerationType;
 import javax.persistence.Id;
 import javax.persistence.JoinColumn;
 import javax.persistence.ManyToOne;
 import javax.persistence.OrderBy;
 import javax.persistence.Table;
 import javax.persistence.TableGenerator;
 import javax.persistence.Transient;
 
 @Entity
 @Table(name="tq_survey_theme")
 public class SurveyTheme
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   public static final Integer NORMAL = Integer.valueOf(1);
 
   public static final Integer WRITED = Integer.valueOf(2);
 
   public static final Integer CHECKED = Integer.valueOf(1);
 
   public static final Integer SELECTED = Integer.valueOf(2);
 
   public static final Integer TEXT = Integer.valueOf(3);
 
   public static final Integer ONLINE = Integer.valueOf(4);
 
   public static final Integer AREA = Integer.valueOf(5);
   private Integer id;
   private String title;
   private Integer surveyType;
   private Integer totalCount;
   private Integer maxlength;
   private Integer showType;
   private Integer priority;
   private Questionnaire naire;
   private Set<SurveyItem> items;
 
   public void init()
   {
     if (getSurveyType().equals(WRITED)) {
       setTotalCount(null);
       if (getMaxlength() == null) {
         setMaxlength(Integer.valueOf(50));
       }
     }
     if (getSurveyType().equals(NORMAL)) {
       setMaxlength(null);
       if (getTotalCount() == null) {
         setTotalCount(Integer.valueOf(1));
       }
     }
     if (getPriority() == null)
       setPriority(Integer.valueOf(10));
   }
 
   @Transient
   public String getShowString() {
     Integer showtype = getShowType();
     if (showtype.equals(CHECKED))
       return "单复选框显示";
     if (showtype.equals(SELECTED))
       return "下拉框显示";
     if (showtype.equals(TEXT))
       return "普通输入框显示";
     if (showtype.equals(ONLINE)) {
       return "下划线显示";
     }
     return "文本区域显示";
   }
 
   @Transient
   public String getSurveyTypeString() {
     Integer surveytype = getSurveyType();
     if (surveytype.equals(NORMAL)) {
       return "选项类";
     }
     return "填写类";
   }
 
   public void addToItems(String name, Integer votes, Integer priority)
   {
     Set items = getItems();
     if (items == null) {
       items = new HashSet();
       setItems(items);
     }
     SurveyItem item = new SurveyItem();
     item.setName(name);
     item.setVotes(votes);
     item.setPriority(priority);
     items.add(item);
   }
 
   @Id
   @Column(name="theme_id", unique=true, nullable=false)
   @TableGenerator(name="tg_survey_theme", pkColumnValue="tq_survey_theme", table="tq_gen_table", pkColumnName="tg_gen_name", valueColumnName="tq_gen_value", initialValue=1, allocationSize=1)
   @GeneratedValue(strategy=GenerationType.TABLE, generator="tg_survey_theme")
   public Integer getId()
   {
     return this.id;
   }
 
   public void setId(Integer id) {
     this.id = id;
   }
   @Column(name="title", nullable=false, length=50)
   public String getTitle() {
     return this.title;
   }
 
   public void setTitle(String title) {
     this.title = title;
   }
   @Column(name="survey_type", nullable=false, length=10)
   public Integer getSurveyType() {
     return this.surveyType;
   }
 
   public void setSurveyType(Integer surveyType) {
     this.surveyType = surveyType;
   }
   @Column(name="total_count", nullable=true, length=10)
   public Integer getTotalCount() {
     return this.totalCount;
   }
 
   public void setTotalCount(Integer totalCount) {
     this.totalCount = totalCount;
   }
   @Column(name="maxlength", nullable=true, length=10)
   public Integer getMaxlength() {
     return this.maxlength;
   }
 
   public void setMaxlength(Integer maxlength) {
     this.maxlength = maxlength;
   }
   @Column(name="show_type", nullable=true, length=10)
   public Integer getShowType() {
     return this.showType;
   }
 
   public void setShowType(Integer showType) {
     this.showType = showType;
   }
   @Column(name="priority", nullable=false, length=10)
   public Integer getPriority() {
     return this.priority;
   }
 
   public void setPriority(Integer priority) {
     this.priority = priority;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="naire_id", nullable=false)
   public Questionnaire getNaire() { return this.naire; }
 
   public void setNaire(Questionnaire naire)
   {
     this.naire = naire; } 
   @ElementCollection(fetch=FetchType.LAZY)
   @CollectionTable(name="tq_survey_item", joinColumns={@JoinColumn(name="theme_id")})
   @OrderBy("priority asc")
   public Set<SurveyItem> getItems() { return this.items;
   }
 
   public void setItems(Set<SurveyItem> items)
   {
     this.items = items;
   }
 }


 
 
 
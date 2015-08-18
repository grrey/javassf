 package com.portal.doccenter.entity;
 
 import com.portal.sysmgr.entity.Site;
 import com.portal.usermgr.entity.Role;
 import java.io.Serializable;
 import java.sql.Timestamp;
 import java.util.ArrayList;
 import java.util.Date;
 import java.util.List;
 import java.util.Set;
 import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.FetchType;
 import javax.persistence.GeneratedValue;
 import javax.persistence.GenerationType;
 import javax.persistence.Id;
 import javax.persistence.JoinColumn;
 import javax.persistence.ManyToOne;
 import javax.persistence.OneToMany;
 import javax.persistence.OrderBy;
 import javax.persistence.Table;
 import javax.persistence.TableGenerator;
 import javax.persistence.Temporal;
 import javax.persistence.TemporalType;
 import javax.persistence.Transient;
 
 @Entity
 @Table(name="tq_work_flow")
 public class WorkFlow
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private Integer id;
   private String name;
   private String description;
   private Integer stepCount;
   private Date createTime;
   private Site site;
   private Set<FlowStep> flowSteps;
 
   public void init()
   {
     if (getCreateTime() == null)
       setCreateTime(new Timestamp(System.currentTimeMillis()));
   }
 
   @Transient
   public List<Integer> getSteps() {
     if (getFlowSteps() != null) {
       List l = new ArrayList();
       for (FlowStep fl : getFlowSteps()) {
         l.add(fl.getStep());
       }
       return l;
     }
     return null;
   }
   @Transient
   public FlowStep getFlowStep(Integer step) {
     if (getFlowSteps() != null) {
       for (FlowStep fl : getFlowSteps()) {
         if (fl.getStep().equals(step)) {
           return fl;
         }
       }
     }
     return null;
   }
   @Transient
   public FlowStep getNextFlowStep(Integer roleId) {
     Set steps = getFlowSteps();
     if (steps != null) {
       List stepList = new ArrayList(steps);
       for (int i = stepList.size() - 1; i >= 0; i--) {
         if ((((FlowStep)stepList.get(i)).getRole().getId().equals(roleId)) && 
           (i + 1 < stepList.size())) {
           return (FlowStep)stepList.get(i + 1);
         }
       }
     }
     return null;
   }
   @Transient
   public FlowStep getLastFlowStep() {
     Set steps = getFlowSteps();
     if (steps != null) {
       List stepList = new ArrayList(steps);
       return (FlowStep)stepList.get(stepList.size() - 1);
     }
     return null;
   }
   @Transient
   public FlowStep getStep(Integer roleId) {
     Set<FlowStep> steps = getFlowSteps();
     if (steps != null) {
       for (FlowStep step : steps) {
         if (step.getRole().getId().equals(roleId)) {
           return step;
         }
       }
     }
     return null;
   }
 
   @Id
   @Column(name="flow_id", unique=true, nullable=false)
   @TableGenerator(name="tg_work_flow", pkColumnValue="tq_work_flow", table="tq_gen_table", pkColumnName="tg_gen_name", valueColumnName="tq_gen_value", initialValue=1, allocationSize=1)
   @GeneratedValue(strategy=GenerationType.TABLE, generator="tg_work_flow")
   public Integer getId()
   {
     return this.id;
   }
 
   public void setId(Integer id) {
     this.id = id;
   }
   @Column(name="flow_name", nullable=false, length=50)
   public String getName() {
     return this.name;
   }
 
   public void setName(String name) {
     this.name = name;
   }
   @Column(name="description", nullable=true, length=200)
   public String getDescription() {
     return this.description;
   }
 
   public void setDescription(String description) {
     this.description = description;
   }
   @Column(name="step_count", nullable=false, length=10)
   public Integer getStepCount() {
     return this.stepCount;
   }
 
   public void setStepCount(Integer stepCount) {
     this.stepCount = stepCount;
   }
   @Temporal(TemporalType.TIMESTAMP)
   @Column(name="create_time", nullable=false, length=19)
   public Date getCreateTime() { return this.createTime; }
 
   public void setCreateTime(Date createTime)
   {
     this.createTime = createTime;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="site_id", nullable=false)
   public Site getSite() { return this.site; }
 
   public void setSite(Site site)
   {
     this.site = site;
   }
   @OneToMany(fetch=FetchType.LAZY, cascade={javax.persistence.CascadeType.REMOVE}, mappedBy="flow")
   @OrderBy("step asc")
   public Set<FlowStep> getFlowSteps() { return this.flowSteps;
   }
 
   public void setFlowSteps(Set<FlowStep> flowSteps)
   {
     this.flowSteps = flowSteps;
   }
 }


 
 
 
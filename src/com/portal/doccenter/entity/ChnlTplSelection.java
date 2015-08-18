 package com.portal.doccenter.entity;
 
 import java.io.Serializable;
 import java.util.Collection;
 import javax.persistence.Column;
 import javax.persistence.Embeddable;
 import javax.persistence.FetchType;
 import javax.persistence.JoinColumn;
 import javax.persistence.ManyToOne;
 import javax.persistence.Transient;
 
 @Embeddable
 public class ChnlTplSelection
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private String tplDoc;
   private Model model;
 
   public static Integer[] fetchIds(Collection<ChnlTplSelection> tpls)
   {
     Integer[] ids = new Integer[tpls.size()];
     int i = 0;
     for (ChnlTplSelection g : tpls) {
       ids[(i++)] = g.getModelId();
     }
     return ids;
   }
   @Transient
   public Integer getModelId() {
     return getModel().getId();
   }
   @Transient
   public Integer getPriority() {
     return getModel().getPriority();
   }
 
   @Column(name="tpl_doc", nullable=true, length=100)
   public String getTplDoc()
   {
     return this.tplDoc;
   }
 
   public void setTplDoc(String tplDoc) {
     this.tplDoc = tplDoc;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="model_id", nullable=false)
   public Model getModel() { return this.model; }
 
   public void setModel(Model model)
   {
     this.model = model;
   }
 }


 
 
 
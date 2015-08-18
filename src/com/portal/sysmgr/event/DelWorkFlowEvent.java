 package com.portal.sysmgr.event;
 
 import com.portal.doccenter.entity.WorkFlow;
 import org.springframework.context.ApplicationEvent;
 
 public class DelWorkFlowEvent extends ApplicationEvent
 {
   public DelWorkFlowEvent(WorkFlow flow)
   {
     super(flow);
   }
 
   public WorkFlow getWorkFlow() {
     return (WorkFlow)super.getSource();
   }
 }


 
 
 
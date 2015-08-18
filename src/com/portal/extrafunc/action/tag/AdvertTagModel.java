 package com.portal.extrafunc.action.tag;
 
 import com.portal.extrafunc.entity.Advert;
 import com.portal.extrafunc.service.AdvertService;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.utils.TagModelTools;
 import com.portal.sysmgr.utils.ViewTools;
 import freemarker.core.Environment;
 import freemarker.template.ObjectWrapper;
 import freemarker.template.TemplateDirectiveBody;
 import freemarker.template.TemplateDirectiveModel;
 import freemarker.template.TemplateException;
 import freemarker.template.TemplateModel;
 import java.io.IOException;
 import java.util.Date;
 import java.util.List;
 import java.util.Map;
 import org.springframework.beans.factory.annotation.Autowired;
 
 public class AdvertTagModel
   implements TemplateDirectiveModel
 {
   public static final String PARAM_ID = "id";
   public static final String PARAM_SLOT_ID = "sId";
   public static final String PARAM_CUSTOM = "custom";
 
   @Autowired
   private AdvertService service;
 
   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
     throws TemplateException, IOException
   {
     Site site = ViewTools.getSite(env);
     Integer id = TagModelTools.getInt("id", params);
     Integer sId = TagModelTools.getInt("sId", params);
     Boolean custom = TagModelTools.getBool("custom", params);
     boolean out = false;
     if (id != null) {
       Advert a = this.service.findById(id);
       if ((a != null) && (a.getEnable().booleanValue()) && 
         (a.getStartTime().before(new Date())))
         if ((a.getEndTime() != null) && (a.getEndTime().after(new Date()))) {
           out = true;
           env.setVariable("bean", ObjectWrapper.DEFAULT_WRAPPER.wrap(a));
         } else {
           out = true;
           env.setVariable("bean", ObjectWrapper.DEFAULT_WRAPPER.wrap(a));
         }
     }
     else {
       out = true;
       List list = this.service.getListByTag(site.getId(), sId);
       env.setVariable("list", ObjectWrapper.DEFAULT_WRAPPER.wrap(list));
     }
     if ((custom != null) && (custom.booleanValue())) {
       if (out)
         body.render(env.getOut());
     }
     else
       ViewTools.includeTpl("adv", site, env);
   }
 }


 
 
 
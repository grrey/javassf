 package com.portal.doccenter.action.tag;
 
 import com.portal.doccenter.entity.Channel;
 import com.portal.doccenter.service.ChannelService;
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
 import java.io.PrintStream;
 import java.util.Map;
 import org.apache.commons.lang.StringUtils;
 import org.springframework.beans.factory.annotation.Autowired;
 
 public class ChannelTagModel
   implements TemplateDirectiveModel
 {
   public static final String PARAM_ID = "id";
   public static final String PARAM_PATH = "path";
   public static final String PARAM_SITE_ID = "siteId";
 
   @Autowired
   private ChannelService channelService;
 
   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
     throws TemplateException, IOException
   {
     Site site = ViewTools.getSite(env);
     Integer id = TagModelTools.getInt("id", params);
     Channel channel;
     //Channel channel;
     if (id != null) {
       channel = this.channelService.findById(id);
     } else {
       String path = TagModelTools.getString("path", params);
       if (StringUtils.isBlank(path)) {
         System.out.println("缺少必要参数!");
         return;
       }
       Integer siteId = TagModelTools.getInt("siteId", params);
       if (siteId == null) {
         siteId = site.getId();
       }
       channel = this.channelService.findByPathForTag(path, siteId);
     }
     env.setVariable("bean", ObjectWrapper.DEFAULT_WRAPPER.wrap(channel));
     if (body != null)
       body.render(env.getOut());
   }
 }


 
 
 
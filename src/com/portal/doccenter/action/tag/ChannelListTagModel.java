 package com.portal.doccenter.action.tag;
 
 import com.portal.doccenter.action.tag.base.BaseChannelTagModel;
 import com.portal.doccenter.service.ChannelService;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.utils.TagModelTools;
 import com.portal.sysmgr.utils.ViewTools;
 import freemarker.core.Environment;
 import freemarker.template.ObjectWrapper;
 import freemarker.template.TemplateDirectiveBody;
 import freemarker.template.TemplateException;
 import freemarker.template.TemplateModel;
 import java.io.IOException;
 import java.util.List;
 import java.util.Map;
 import org.apache.commons.lang.StringUtils;
 
 public class ChannelListTagModel extends BaseChannelTagModel
 {
   public static final String TPL_NAME = "tplName";
 
   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
     throws TemplateException, IOException
   {
     Site site = ViewTools.getSite(env);
     Integer parentId = TagModelTools.getInt("pId", params);
     Integer siteId = TagModelTools.getInt("sId", params);
     String tplName = TagModelTools.getString("tplName", params);
     Boolean isAlone = getAlone(params);
     if (siteId == null) {
       siteId = site.getId();
     }
     List list = this.channelService.getChannelListByTag(siteId, 
       parentId, isAlone, true, ViewTools.getCount(params));
     env.setVariable("list", ObjectWrapper.DEFAULT_WRAPPER.wrap(list));
     if (!StringUtils.isBlank(tplName)) {
       ViewTools.includeTpl(tplName, site, env);
     }
     else if (body != null)
       body.render(env.getOut());
   }
 }


 
 
 
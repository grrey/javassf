 package com.portal.sysmgr.action.tag;
 
 import com.javassf.basic.utils.StringBeanUtils;
 import com.portal.sysmgr.utils.TagModelTools;
 import freemarker.core.Environment;
 import freemarker.template.TemplateDirectiveBody;
 import freemarker.template.TemplateDirectiveModel;
 import freemarker.template.TemplateException;
 import freemarker.template.TemplateModel;
 import java.io.IOException;
 import java.io.Writer;
 import java.util.Map;
 import org.apache.commons.lang.StringUtils;
 
 public class StrLimitTagModel
   implements TemplateDirectiveModel
 {
   public static final String PARAM_S = "s";
   public static final String PARAM_LEN = "l";
   public static final String PARAM_REP = "r";
 
   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
     throws TemplateException, IOException
   {
     String s = TagModelTools.getString("s", params);
     Integer len = TagModelTools.getInt("l", params);
     String r = TagModelTools.getString("r", params);
     if (!StringUtils.isBlank(r)) {
       s = s.replaceAll(r, "");
     }
     String append = "&hellip;";
     if (s != null) {
       Writer out = env.getOut();
       if (len != null)
         out.append(StringBeanUtils.textCut(s, len.intValue(), append));
       else
         out.append(s);
     }
   }
 }


 
 
 
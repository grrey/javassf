 package com.portal.sysmgr.utils;
 
 import com.javassf.basic.plugin.springmvc.DateTypeEditor;
 import freemarker.template.TemplateBooleanModel;
 import freemarker.template.TemplateDateModel;
 import freemarker.template.TemplateException;
 import freemarker.template.TemplateModel;
 import freemarker.template.TemplateModelException;
 import freemarker.template.TemplateNumberModel;
 import freemarker.template.TemplateScalarModel;
 import java.io.PrintStream;
 import java.util.Date;
 import java.util.Map;
 import org.apache.commons.lang.StringUtils;
 import org.springframework.util.NumberUtils;
 
 public abstract class TagModelTools
 {
   public static final String BEAN = "bean";
   public static final String LIST = "list";
   public static final String PAGE = "page";
 
   public static String getString(String name, Map<String, TemplateModel> params)
     throws TemplateModelException
   {
     TemplateModel model = (TemplateModel)params.get(name);
     return getString(name, model);
   }
 
   public static String getString(String name, TemplateModel model) throws TemplateModelException
   {
     if (model == null) {
       return null;
     }
     if ((model instanceof TemplateScalarModel))
       return ((TemplateScalarModel)model).getAsString();
     if ((model instanceof TemplateNumberModel)) {
       return ((TemplateNumberModel)model).getAsNumber().toString();
     }
     System.out.println("参数必须是字符！");
     return null;
   }
 
   public static String[] getStringArray(String name, Map<String, TemplateModel> params)
     throws TemplateModelException
   {
     String str = getString(name, params);
     if (StringUtils.isBlank(str)) {
       return null;
     }
     String[] arr = StringUtils.split(str, ',');
     return arr;
   }
 
   public static Long getLong(String name, TemplateModel model) throws TemplateModelException
   {
     return (Long)getNumber(model, name, Long.class);
   }
 
   public static Long getLong(String name, Map<String, TemplateModel> params) throws TemplateModelException
   {
     return (Long)getNumber(params, name, Long.class);
   }
 
   public static Integer getInt(String name, TemplateModel model) throws TemplateModelException
   {
     return (Integer)getNumber(model, name, Integer.class);
   }
 
   public static Integer getInt(String name, Map<String, TemplateModel> params) throws TemplateModelException
   {
     return (Integer)getNumber(params, name, Integer.class);
   }
 
   public static Byte getByte(String name, TemplateModel model) throws TemplateModelException
   {
     return (Byte)getNumber(model, name, Byte.class);
   }
 
   public static Byte getByte(String name, Map<String, TemplateModel> params) throws TemplateModelException
   {
     return (Byte)getNumber(params, name, Byte.class);
   }
 
   public static <T extends Number> T getNumber(TemplateModel model, String name, Class<T> targetClass) throws TemplateModelException
   {
     if (model == null) {
       return null;
     }
     if ((model instanceof TemplateScalarModel)) {
       TemplateScalarModel scalarModel = (TemplateScalarModel)model;
       String text = scalarModel.getAsString();
       if (StringUtils.isNotBlank(text)) {
         try {
           return NumberUtils.parseNumber(text, targetClass);
         } catch (NumberFormatException e) {
           System.out.println("参数必须是数字");
           return null;
         }
       }
       return null;
     }
     if ((model instanceof TemplateNumberModel)) {
       TemplateNumberModel numberModel = (TemplateNumberModel)model;
       Number number = numberModel.getAsNumber();
       return NumberUtils.convertNumberToTargetClass(number, targetClass);
     }
     System.out.println("参数必须是数字");
     return null;
   }
 
   public static <T extends Number> T getNumber(Map<String, TemplateModel> params, String name, Class<T> targetClass)
     throws TemplateModelException
   {
     TemplateModel model = (TemplateModel)params.get(name);
     return getNumber(model, name, targetClass);
   }
 
   public static Integer[] getIntArray(String name, Map<String, TemplateModel> params) throws TemplateException
   {
     String str = getString(name, params);
     if (StringUtils.isBlank(str)) {
       return null;
     }
     String[] arr = StringUtils.split(str, ',');
     Integer[] ids = new Integer[arr.length];
     int i = 0;
     try {
       for (String s : arr) {
         ids[(i++)] = Integer.valueOf(s);
       }
       return ids;
     } catch (NumberFormatException e) {
       System.out.println("参数必须是数字,而且以,隔开!");
     }return null;
   }
 
   public static Boolean getBool(String name, Map<String, TemplateModel> params)
     throws TemplateException
   {
     TemplateModel model = (TemplateModel)params.get(name);
     if (model == null) {
       return null;
     }
     if ((model instanceof TemplateBooleanModel))
       return Boolean.valueOf(((TemplateBooleanModel)model).getAsBoolean());
     if ((model instanceof TemplateNumberModel))
       return Boolean.valueOf(((TemplateNumberModel)model).getAsNumber().intValue() != 0);
     if ((model instanceof TemplateScalarModel)) {
       String s = ((TemplateScalarModel)model).getAsString();
       if (!StringUtils.isBlank(s)) {
         return Boolean.valueOf((!s.equals("0")) && (!s.equalsIgnoreCase("false")) && (!s
           .equalsIgnoreCase("f")));
       }
       return null;
     }
 
     System.out.println("参数必须是boolean型");
     return null;
   }
 
   public static Date getDate(String name, Map<String, TemplateModel> params)
     throws TemplateException
   {
     TemplateModel model = (TemplateModel)params.get(name);
     if (model == null) {
       return null;
     }
     if ((model instanceof TemplateDateModel))
       return ((TemplateDateModel)model).getAsDate();
     if ((model instanceof TemplateScalarModel)) {
       DateTypeEditor editor = new DateTypeEditor();
       editor.setAsText(((TemplateScalarModel)model).getAsString());
       return (Date)editor.getValue();
     }
     System.out.println("参数必须是日期型");
     return null;
   }
 }


 
 
 
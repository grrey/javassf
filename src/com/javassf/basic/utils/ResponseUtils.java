package com.javassf.basic.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ResponseUtils
{
  public static final Logger log = LoggerFactory.getLogger(ResponseUtils.class);

  public static void renderText(HttpServletResponse response, String text)
  {
    render(response, "text/plain;charset=UTF-8", text);
  }

  public static void renderJson(HttpServletResponse response, String text)
  {
    render(response, "application/json;charset=UTF-8", text);
  }

  public static void renderXml(HttpServletResponse response, String text)
  {
    render(response, "text/xml;charset=UTF-8", text);
  }

  public static void render(HttpServletResponse response, String contentType, String text)
  {
    response.setContentType(contentType);
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0L);
    try {
      response.getWriter().write(text);
    } catch (IOException e) {
      log.error(e.getMessage(), e);
    }
  }

  private static String beanToJson(Object bean, String arg) {
    if (bean == null) {
      return "";
    }
    PropertyDescriptor[] props = (PropertyDescriptor[])null;
    try {
      props = Introspector.getBeanInfo(bean.getClass(), Object.class)
        .getPropertyDescriptors();
    } catch (IntrospectionException localIntrospectionException) {
    }
    if (props != null) {
      for (int i = 0; i < props.length; ) {
        try {
          String name = props[i].getName();
          if (arg.indexOf(".") > -1) {
            String temparg = arg.substring(0, arg.indexOf("."));
            if (temparg.equals(name)) {
              return beanToJson(
                props[i].getReadMethod().invoke(bean, new Object[0]), 
                arg.substring(arg.indexOf(".") + 1, 
                arg.length()));
            }
          }
          else if (name.equals(arg)) {
            String value = props[i].getReadMethod()
              .invoke(bean, new Object[0]).toString();
            return value;
          }
        }
        catch (Exception localException)
        {
          i++;
        }

      }

    }

    return "";
  }

  public static String beanToJson(Object bean, String[] args) {
    StringBuffer json = new StringBuffer();
    json.append("{");
    PropertyDescriptor[] props = (PropertyDescriptor[])null;
    try {
      props = Introspector.getBeanInfo(bean.getClass(), Object.class)
        .getPropertyDescriptors();
    } catch (IntrospectionException localIntrospectionException) {
    }
    if (props != null) {
      for (int i = 0; i < props.length; i++)
        try {
          String name = props[i].getName();
          for (String arg : args) {
            if (arg.indexOf(".") > -1) {
              String[] showname = arg.split(":");
              String temparg = showname[0].substring(0, 
                showname[0].indexOf("."));
              if (temparg.equals(name)) {
                String tvalue = beanToJson(props[i]
                  .getReadMethod().invoke(bean, new Object[0]), 
                  showname[0].substring(
                  showname[0].indexOf(".") + 1, 
                  showname[0].length()));
                json.append(showname[1]);
                json.append(":");
                json.append('"');
                json.append(StringBeanUtils.txt2htm(tvalue));
                json.append('"');
                json.append(",");
              }
            }
          }
          String value = props[i].getReadMethod().invoke(bean, new Object[0])
            .toString();
          json.append(name);
          json.append(":");
          json.append('"');
          json.append(StringBeanUtils.txt2htm(value));
          json.append('"');
          json.append(",");
        }
        catch (Exception localException) {
        }
      json.setCharAt(json.length() - 1, '}');
    } else {
      json.append("}");
    }
    return json.toString();
  }

  public static String listToJson(List list) {
    StringBuffer json = new StringBuffer();
    json.append("{c:[");
    if ((list != null) && (list.size() > 0)) {
      for (Iterator localIterator = list.iterator(); localIterator.hasNext(); ) { Object o = localIterator.next();
        json.append(beanToJson(o, new String[0]));
        json.append(",");
      }
      json.setCharAt(json.length() - 1, ']');
      json.append("}");
    } else {
      json.append("]}");
    }
    return json.toString();
  }

  public static String listToJsonadmin(List list, Integer total, String[] args)
  {
    StringBuffer json = new StringBuffer();
    json.append("{Rows:[");
    if ((list != null) && (list.size() > 0)) {
      for (Iterator localIterator = list.iterator(); localIterator.hasNext(); ) { Object o = localIterator.next();
        json.append(beanToJson(o, args));
        json.append(",");
      }
      json.setCharAt(json.length() - 1, ']');
      if (total != null) {
        json.append(",Total:");
        json.append(total);
      }
      json.append("}");
    } else {
      json.append("]}");
    }
    return json.toString();
  }
}

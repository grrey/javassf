package com.javassf.basic.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UrlPathHelper;

public class ServicesUtils
{
  private static final Logger log = LoggerFactory.getLogger(ServicesUtils.class);
  private static final String NUKNOWN = "unknown";
  private static final String[] ADDR_HEADER = { "x-forwarded-for", 
    "Proxy-Client-IP", "WL-Proxy-Client-IP" };

  public static String getQueryParam(HttpServletRequest request, String name)
  {
    if (StringUtils.isBlank(name)) {
      return null;
    }
    if (request.getMethod().equalsIgnoreCase("POST")) {
      return request.getParameter(name);
    }
    String s = request.getQueryString();
    if (StringUtils.isBlank(s))
      return null;
    try
    {
      s = URLDecoder.decode(s, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      log.error("encoding UTF-8 not support?", e);
    }
    String[] values = (String[])parseQueryString(s).get(name);
    if ((values != null) && (values.length > 0)) {
      return values[(values.length - 1)];
    }
    return null;
  }

  public static Map<String, Object> getQueryParams(HttpServletRequest request)
  {
		Map<String,String[]> map;
   //Map map;
    if (request.getMethod().equalsIgnoreCase("POST")) {
      map = request.getParameterMap();
    } else {
      String s = request.getQueryString();
      if (StringUtils.isBlank(s))
        return new HashMap();
      try
      {
        s = URLDecoder.decode(s, "UTF-8");
      } catch (UnsupportedEncodingException e) {
        log.error("encoding UTF-8 not support?", e);
      }
      map = parseQueryString(s);
    }

    Map<String, Object> params = new HashMap<String, Object>(map.size());
    for (Map.Entry<String,String[]> entry : map.entrySet()) {
      int len = entry.getValue().length;
      if (len == 1)
        params.put(entry.getKey(), entry.getValue()[0]);
      else if (len > 1) {
        params.put(entry.getKey(), entry.getValue());
      }
    }
    return params;
  }

  public static Map<String, String[]> parseQueryString(String s)
  {
    String[] valArray = (String[])null;
    if (s == null) {
      throw new IllegalArgumentException();
    }
    Map ht = new HashMap();
    StringTokenizer st = new StringTokenizer(s, "&");
    while (st.hasMoreTokens()) {
      String pair = st.nextToken();
      int pos = pair.indexOf('=');
      if (pos == -1) {
        continue;
      }
      String key = pair.substring(0, pos);
      String val = pair.substring(pos + 1, pair.length());
      if (ht.containsKey(key)) {
        String[] oldVals = (String[])ht.get(key);
        valArray = new String[oldVals.length + 1];
        for (int i = 0; i < oldVals.length; i++) {
          valArray[i] = oldVals[i];
        }
        valArray[oldVals.length] = val;
      } else {
        valArray = new String[1];
        valArray[0] = val;
      }
      ht.put(key, valArray);
    }
    return ht;
  }

  public static Map<String, String> getRequestMap(ServletRequest request, String prefix)
  {
    return getRequestMap(request, prefix, false);
  }

  public static Map<String, String> getRequestMapWithPrefix(ServletRequest request, String prefix)
  {
    return getRequestMap(request, prefix, true);
  }

  private static Map<String, String> getRequestMap(ServletRequest request, String prefix, boolean nameWithPrefix)
  {
    Map map = new HashMap();
    Enumeration names = request.getParameterNames();

    while (names.hasMoreElements()) {
      String name = (String)names.nextElement();
      if (name.startsWith(prefix)) {
        String key = nameWithPrefix ? name : name.substring(prefix.length());
        String value = StringUtils.join(request.getParameterValues(name), ',');
        map.put(key, value);
      }
    }
    return map;
  }

  public static Map<String, String[]> getRequestMapList(ServletRequest request, String prefix)
  {
    return getRequestMapList(request, prefix, false);
  }

  private static Map<String, String[]> getRequestMapList(ServletRequest request, String prefix, boolean nameWithPrefix)
  {
    Map map = new HashMap();
    Enumeration names = request.getParameterNames();

    while (names.hasMoreElements()) {
      String name = (String)names.nextElement();
      if (name.startsWith(prefix)) {
        String key = nameWithPrefix ? name : name.substring(prefix.length());
        map.put(key, request.getParameterValues(name));
      }
    }
    return map;
  }

  public static Map<String, String> getRequestMapWithPrefix(ServletRequest request, String prefix, String[] contains)
  {
    return getRequestMap(request, prefix, contains, false);
  }

  private static Map<String, String> getRequestMap(ServletRequest request, String prefix, String[] contains, boolean nameWithPrefix)
  {
    Map map = new HashMap();
    Enumeration names = request.getParameterNames();

    while (names.hasMoreElements()) {
      String name = (String)names.nextElement();
      if (name.startsWith(prefix)) {
        String key = nameWithPrefix ? name : name.substring(prefix.length());
        if (StringBeanUtils.contains(key, contains)) {
          String value = StringUtils.join(request.getParameterValues(name), 
            ',');
          map.put(key, value);
        }
      }
    }
    return map;
  }

  public static String getIpAddr(ServletRequest request)
  {
    String addr = null;
    if ((request instanceof HttpServletRequest)) {
      HttpServletRequest hsr = (HttpServletRequest)request;
      for (String header : ADDR_HEADER) {
        if ((!StringUtils.isBlank(addr)) && (!"unknown".equalsIgnoreCase(addr))) break;
        addr = hsr.getHeader(header);
      }

    }

    if ((StringUtils.isBlank(addr)) || ("unknown".equalsIgnoreCase(addr))) {
      addr = request.getRemoteAddr();
    } else {
      int i = addr.indexOf(",");
      if (i > 0) {
        addr = addr.substring(0, i);
      }
    }
    return addr;
  }

  public static String getLocation(HttpServletRequest request, boolean query)
  {
    UrlPathHelper helper = new UrlPathHelper();
    StringBuffer buff = request.getRequestURL();
    String uri = request.getRequestURI();
    String origUri = helper.getOriginatingRequestUri(request);
    buff.replace(buff.length() - uri.length(), buff.length(), origUri);
    if (query) {
      String queryString = helper.getOriginatingQueryString(request);
      if (queryString != null) {
        buff.append("?").append(queryString);
      }
    }
    return buff.toString();
  }

  public static void main(String[] args)
  {
  }
}
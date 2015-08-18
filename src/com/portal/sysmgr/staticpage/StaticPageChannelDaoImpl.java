 package com.portal.sysmgr.staticpage;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.doccenter.entity.Channel;
 import freemarker.template.Configuration;
 import java.util.ArrayList;
 import java.util.List;
 import javax.servlet.ServletContext;
 import org.apache.commons.lang.StringUtils;
 import org.hibernate.CacheMode;
 import org.hibernate.Criteria;
 import org.hibernate.ScrollMode;
 import org.hibernate.ScrollableResults;
 import org.hibernate.Session;
 import org.hibernate.criterion.Restrictions;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class StaticPageChannelDaoImpl extends QueryDaoImpl<Channel, Integer>
   implements StaticPageChannelDao
 {
   public List<String> staticChannelPage(String treeNumber, Configuration config, ServletContext ctx)
   {
     Criteria crit = createCriteria();
     if (!StringUtils.isBlank(treeNumber)) {
       crit.add(Restrictions.like("number", treeNumber + "%"));
     }
     Session session = getSession();
     ScrollableResults channels = crit.setCacheMode(CacheMode.IGNORE)
       .scroll(ScrollMode.FORWARD_ONLY);
 
     List list = new ArrayList();
     List listtemp = new ArrayList();
     int count = 0; int counts = 0; int countf = 0; int countn = 0;
     while (channels.next()) {
       Channel channel = (Channel)channels.get(0);
       Integer i = StaticChannel.staticChannel(channel, config, ctx, null);
       switch (i.intValue()) {
       case 0:
         countn++;
         break;
       case -1:
         countf++;
         listtemp.add("栏目：" + channel.getName() + "的模板不存在或者存在错误，生成静态页失败！");
         break;
       case -2:
         countf++;
         listtemp.add("栏目：" + channel.getName() + "生成静态页时发生IO异常！");
         break;
       case -3:
         countf++;
         listtemp.add("栏目：" + channel.getName() + "目标静态页文件或者文件夹不存在!");
         break;
       case -4:
         countf++;
         listtemp.add("栏目：" + channel.getName() + "生成静态页时发生异常！");
         break;
       default:
         counts++;
       }
 
       count++; if (count % 5 == 0) {
         session.clear();
       }
     }
     list.add("成功生成栏目静态页" + counts + "个栏目，失败" + countf + "个栏目,未生成" + countn + 
       "个栏目。");
     list.addAll(listtemp);
     return list;
   }
 
   protected Class<Channel> getEntityClass() {
     return Channel.class;
   }
 }


 
 
 
 package com.portal.extrafunc.entity;
 
 import com.javassf.basic.utils.StringBeanUtils;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.utils.code.BbcodeHandler;
 import com.portal.usermgr.entity.User;
 import java.io.Serializable;
 import java.sql.Timestamp;
 import java.util.ArrayList;
 import java.util.Date;
 import java.util.List;
 import java.util.regex.Matcher;
 import java.util.regex.Pattern;
 import javax.persistence.CollectionTable;
 import javax.persistence.Column;
 import javax.persistence.ElementCollection;
 import javax.persistence.Entity;
 import javax.persistence.FetchType;
 import javax.persistence.GeneratedValue;
 import javax.persistence.GenerationType;
 import javax.persistence.Id;
 import javax.persistence.JoinColumn;
 import javax.persistence.ManyToOne;
 import javax.persistence.OneToOne;
 import javax.persistence.OrderColumn;
 import javax.persistence.PrimaryKeyJoinColumn;
 import javax.persistence.Table;
 import javax.persistence.TableGenerator;
 import javax.persistence.Temporal;
 import javax.persistence.TemporalType;
 import javax.persistence.Transient;
import org.apache.commons.lang.StringUtils;
 
 @Entity
 @Table(name="tq_posts")
 public class Posts
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   public static final int NORMAL = 0;
   public static final int SHIELD = -1;
   public static final int DELETE = -2;
   private Integer id;
   private String title;
   private Integer status;
   private Boolean affix;
   private Boolean img;
   private Boolean hidden;
   private Integer floor;
   private Date createTime;
   private PostsExt ext;
   private PostsTxt txt;
   private Theme theme;
   private Posts quote;
   private User creater;
   private Site site;
   private List<PostsAttach> attachs;
 
   public void init()
   {
     if (getStatus() == null) {
       setStatus(Integer.valueOf(0));
     }
     if (getImg() == null) {
       setImg(Boolean.valueOf(false));
     }
     if (getCreateTime() == null)
       setCreateTime(new Timestamp(System.currentTimeMillis()));
   }
 
   public void addToAttachs(String name, String description, String fileName, String filePath, Integer fileSize, Boolean img)
   {
     List list = getAttachs();
     if (list == null) {
       list = new ArrayList();
       setAttachs(list);
     }
     PostsAttach pa = new PostsAttach();
     pa.setName(name);
     pa.setDescription(description);
     pa.setFileName(fileName);
     pa.setFilePath(filePath);
     pa.setFileSize(fileSize);
     pa.setImg(img);
     list.add(pa);
   }
   @Transient
   public String getContent() {
     PostsTxt txt = getTxt();
     if (txt != null) {
       return txt.getContent();
     }
     return null;
   }
 
   @Transient
   public String getContentAll()
   {
     String s = getContent();
     String qc = "";
     if (StringUtils.isBlank(s)) {
       return "";
     }
     if (getStatus().intValue() < 0) {
       s = "[quote='shield'][h=6]提示:该贴已被删除或者被屏蔽,您是管理员，可见其内容[/h]" + s + 
         "[/quote]";
     }
     if (getQuote() != null) {
       qc = getQuote().getContentNoQuote(true);
     }
     if (getHidden().booleanValue()) {
       List<String> list = getHideContent(s);
       for (String str : list) {
         s = StringBeanUtils.replace(s, "[hide]" + str + "[/hide]", 
           "[quote='content_hidden'][h=6]此为隐藏内容:[/h]" + str + 
           "[/quote]");
       }
     }
     if (getAffix().booleanValue()) {
       List att = getAttachs();
       for (int i = 0; i < att.size(); i++) {
         PostsAttach pa = (PostsAttach)att.get(i);
         if (pa != null) {
           String oldcontent = "[attachment]" + i + 
             "[/attachment]";
           if (pa.getImg().booleanValue()) {
             String newcontent = "[img]" + getSite().getUrl() + 
               pa.getFilePath().substring(1) + "[/img]";
             s = StringBeanUtils.replace(s, oldcontent, 
               newcontent);
           } else {
             String newcontent = "[url=" + getSite().getUrl() + 
               pa.getFilePath().substring(1) + "]" + 
               pa.getFileName() + "[/url]";
             s = StringBeanUtils.replace(s, oldcontent, 
               newcontent);
           }
         }
       }
     }
     return qc + BbcodeHandler.toHtml(s);
   }
 
   @Transient
   public String getContentAndQuote(boolean showhidden)
   {
     String s = getContent();
     String qc = "";
     if (StringUtils.isBlank(s)) {
       return "";
     }
     if (getStatus().intValue() < 0) {
       String content = "[quote='shield'][color=red]该贴已被删除或者被屏蔽[/color][/quote]";
       return BbcodeHandler.toHtml(content);
     }
     if (getQuote() != null) {
       qc = getQuote().getContentNoQuote(showhidden);
     }
     if (getHidden().booleanValue()) {
       List<String>  list = getHideContent(s);
       if (showhidden) {
         for (String str : list)
           s = StringBeanUtils.replace(s, "[hide]" + str + 
             "[/hide]", 
             "[quote='content_hidden'][h=6]此为隐藏内容:[/h]" + 
             str + "[/quote]");
       }
       else {
         for (String str : list) {
           s = 
             StringBeanUtils.replace(
             s, 
             "[hide]" + str + "[/hide]", 
             "[quote='content_hidden'][h=6]提示:[/h][color=red]这是隐藏内容.需要回复才能浏览[/color][/quote]");
         }
       }
     }
     if (getAffix().booleanValue()) {
       List att = getAttachs();
       for (int i = 0; i < att.size(); i++) {
         PostsAttach pa = (PostsAttach)att.get(i);
         if (pa != null) {
           String oldcontent = "[attachment]" + i + 
             "[/attachment]";
           if (pa.getImg().booleanValue()) {
             String newcontent = "[img]" + getSite().getUrl() + 
               pa.getFilePath().substring(1) + "[/img]";
             s = StringBeanUtils.replace(s, oldcontent, 
               newcontent);
           } else {
             String newcontent = "[url=" + getSite().getUrl() + 
               pa.getFilePath().substring(1) + "]" + 
               pa.getFileName() + "[/url]";
             s = StringBeanUtils.replace(s, oldcontent, 
               newcontent);
           }
         }
       }
     }
     return qc + BbcodeHandler.toHtml(s);
   }
 
   @Transient
   private String getContentNoQuote(boolean showhidden)
   {
     String s = getContent();
     if (StringUtils.isBlank(s)) {
       return "";
     }
     if (getStatus().intValue() < 0) {
       String content = "[quote='shield'][color=red]该贴已被删除或者被屏蔽[/color][/quote]";
       return BbcodeHandler.toHtml(content);
     }
     if (getHidden().booleanValue()) {
       List<String>  list = getHideContent(s);
       if (showhidden) {
         for (String str : list)
           s = StringBeanUtils.replace(s, "[hide]" + str + 
             "[/hide]", 
             "[quote='content_hidden'][h=6]此为隐藏内容:[/h]" + 
             str + "[/quote]");
       }
       else {
         for (String str : list) {
           s = 
             StringBeanUtils.replace(
             s, 
             "[hide]" + str + "[/hide]", 
             "[quote='content_hidden'][h=6]提示:[/h][color=red]引用了隐藏内容，需要回复才能浏览！[/color][/quote]");
         }
       }
     }
     if (getAffix().booleanValue()) {
       List att = getAttachs();
       for (int i = 0; i < att.size(); i++) {
         PostsAttach pa = (PostsAttach)att.get(i);
         if (pa != null) {
           String oldcontent = "[attachment]" + i + 
             "[/attachment]";
           if (pa.getImg().booleanValue()) {
             String newcontent = "[img]" + getSite().getUrl() + 
               pa.getFilePath().substring(1) + "[/img]";
             s = StringBeanUtils.replace(s, oldcontent, 
               newcontent);
           } else {
             String newcontent = "[url=" + getSite().getUrl() + 
               pa.getFilePath().substring(1) + "]" + 
               pa.getFileName() + "[/url]";
             s = StringBeanUtils.replace(s, oldcontent, 
               newcontent);
           }
         }
       }
     }
     s = "[quote][h=6]引用:[/h]" + s + "[/quote]";
     return BbcodeHandler.toHtml(s);
   }
 
   @Transient
   public String getContentNotHtml(boolean showhidden)
   {
     String s = getContent();
     if (StringUtils.isBlank(s)) {
       return "";
     }
     if (getStatus().intValue() < 0) {
       String content = "[color=red]该贴已被删除或者被屏蔽[/color]";
       return content;
     }
     if (getHidden().booleanValue()) {
       List<String> list = getHideContent(s);
       if (showhidden) {
         for (String str : list)
           s = StringBeanUtils.replace(s, "[hide]" + str + 
             "[/hide]", str);
       }
       else {
         for (String str : list) {
           String newcontent = "[color=red]这是隐藏内容.需要回复才能浏览[/color]";
           s = StringBeanUtils.replace(s, "[hide]" + str + 
             "[/hide]", newcontent);
         }
       }
     }
     return s;
   }
 
   @Transient
   private List<String> getHideContent(String content)
   {
     String ems = "\\[hide\\]([\\s\\S]*)\\[/hide\\]";
     Matcher matcher = Pattern.compile(ems).matcher(content);
     List list = new ArrayList();
     while (matcher.find()) {
       String url = matcher.group(1);
       list.add(url);
     }
     return list;
   }
 
   @Id
   @Column(name="posts_id", unique=true, nullable=false)
   @TableGenerator(name="tg_posts", pkColumnValue="tq_posts", table="tq_gen_table", pkColumnName="tg_gen_name", valueColumnName="tq_gen_value", initialValue=1, allocationSize=1)
   @GeneratedValue(strategy=GenerationType.TABLE, generator="tg_posts")
   public Integer getId()
   {
     return this.id;
   }
 
   public void setId(Integer id) {
     this.id = id;
   }
   @Column(name="title", nullable=true, length=100)
   public String getTitle() {
     return this.title;
   }
 
   public void setTitle(String title) {
     this.title = title;
   }
   @Column(name="status", nullable=false, length=10)
   public Integer getStatus() {
     return this.status;
   }
 
   public void setStatus(Integer status) {
     this.status = status;
   }
   @Column(name="is_affix", nullable=false, length=1)
   public Boolean getAffix() {
     return this.affix;
   }
 
   public void setAffix(Boolean affix) {
     this.affix = affix;
   }
   @Column(name="is_img", nullable=false, length=1)
   public Boolean getImg() {
     return this.img;
   }
 
   public void setImg(Boolean img) {
     this.img = img;
   }
   @Column(name="is_hidden", nullable=false, length=1)
   public Boolean getHidden() {
     return this.hidden;
   }
 
   public void setHidden(Boolean hidden) {
     this.hidden = hidden;
   }
   @Column(name="floor", nullable=false, length=10)
   public Integer getFloor() {
     return this.floor;
   }
 
   public void setFloor(Integer floor) {
     this.floor = floor;
   }
   @Temporal(TemporalType.TIMESTAMP)
   @Column(name="create_time", nullable=false, length=19)
   public Date getCreateTime() { return this.createTime; }
 
   public void setCreateTime(Date createTime)
   {
     this.createTime = createTime;
   }
   @OneToOne(cascade={javax.persistence.CascadeType.REMOVE}, fetch=FetchType.LAZY)
   @PrimaryKeyJoinColumn
   public PostsExt getExt() { return this.ext; }
 
   public void setExt(PostsExt ext)
   {
     this.ext = ext;
   }
   @OneToOne(cascade={javax.persistence.CascadeType.REMOVE}, fetch=FetchType.LAZY)
   @PrimaryKeyJoinColumn
   public PostsTxt getTxt() { return this.txt; }
 
   public void setTxt(PostsTxt txt)
   {
     this.txt = txt;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="theme_id", nullable=false)
   public Theme getTheme() { return this.theme; }
 
   public void setTheme(Theme theme)
   {
     this.theme = theme;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="quote_id", nullable=true)
   public Posts getQuote() { return this.quote; }
 
   public void setQuote(Posts quote)
   {
     this.quote = quote;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="creater_id", nullable=true)
   public User getCreater() { return this.creater; }
 
   public void setCreater(User creater)
   {
     this.creater = creater;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="site_id", nullable=false)
   public Site getSite() { return this.site; }
 
   public void setSite(Site site)
   {
     this.site = site; } 
   @ElementCollection(fetch=FetchType.LAZY)
   @CollectionTable(name="tq_posts_attach", joinColumns={@JoinColumn(name="posts_id")})
   @OrderColumn(name="priority")
   public List<PostsAttach> getAttachs() { return this.attachs;
   }
 
   public void setAttachs(List<PostsAttach> attachs)
   {
     this.attachs = attachs;
   }
 }


 
 
 
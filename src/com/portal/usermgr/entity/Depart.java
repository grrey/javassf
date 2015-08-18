 package com.portal.usermgr.entity;
 
 import com.portal.doccenter.entity.Channel;
 import com.portal.sysmgr.entity.Site;
 import java.io.Serializable;
 import java.sql.Timestamp;
 import java.util.Date;
 import java.util.HashSet;
 import java.util.Set;
 import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.FetchType;
 import javax.persistence.GeneratedValue;
 import javax.persistence.GenerationType;
 import javax.persistence.Id;
 import javax.persistence.JoinColumn;
 import javax.persistence.JoinTable;
 import javax.persistence.ManyToMany;
 import javax.persistence.ManyToOne;
 import javax.persistence.OneToMany;
 import javax.persistence.OrderBy;
 import javax.persistence.Table;
 import javax.persistence.TableGenerator;
 import javax.persistence.Temporal;
 import javax.persistence.TemporalType;
 import javax.persistence.Transient;
 import org.hibernate.annotations.Formula;
 
 @Entity
 @Table(name="tq_depart")
 public class Depart
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   private Integer id;
   private String name;
   private String shortName;
   private String number;
   private String visitPath;
   private Integer priority;
   private Integer signCount;
   private Boolean show;
   private Boolean allChannel;
   private Date createTime;
   private Integer reportCount;
   private Integer useCount;
   private Depart parent;
   private Site site;
   private Set<Channel> channels;
   private Set<Depart> child;
 
   public void init()
   {
     if (getCreateTime() == null) {
       setCreateTime(new Timestamp(System.currentTimeMillis()));
     }
     if (getPriority() == null) {
       setPriority(Integer.valueOf(10));
     }
     if (getSignCount() == null) {
       setSignCount(Integer.valueOf(0));
     }
     if (getShow() == null) {
       setShow(Boolean.valueOf(false));
     }
     if (getAllChannel() == null)
       setAllChannel(Boolean.valueOf(false));
   }
 
   public void initTreeNumber()
   {
     String number = "-";
     if (getParent() != null) {
       number = getParent().getNumber();
     }
     setNumber(number + getId() + "-");
   }
 
   public void addToChannels(Channel channel) {
     if (channel == null) {
       return;
     }
     Set set = getChannels();
     if (set == null) {
       set = new HashSet();
       setChannels(set);
     }
     set.add(channel);
   }
 
   public void addToChilds(Depart child) {
     Set set = getChild();
     if (set == null) {
       set = new HashSet();
       setChild(set);
     }
     set.add(child);
   }
   @Transient
   public String getUrl() {
     Site site = getSite();
     StringBuilder url = new StringBuilder(site.getUrl());
     url.append("/").append(getVisitPath());
     url.append("/").append("dp~")
       .append("index");
     url.append(".jsp");
     return url.toString();
   }
   @Transient
   public int getDeep() {
     int deep = 0;
     Depart parent = getParent();
     while (parent != null) {
       deep++;
       parent = parent.getParent();
     }
     return deep;
   }
   @Transient
   public Integer[] getChannelIds() {
     Set channels = getChannels();
     return Channel.fetchIds(channels);
   }
 
   @Id
   @Column(name="depart_id", unique=true, nullable=false)
   @TableGenerator(name="tg_depart", pkColumnValue="tq_depart", table="tq_gen_table", pkColumnName="tg_gen_name", valueColumnName="tq_gen_value", initialValue=1, allocationSize=1)
   @GeneratedValue(strategy=GenerationType.TABLE, generator="tg_depart")
   public Integer getId()
   {
     return this.id;
   }
 
   public void setId(Integer id) {
     this.id = id;
   }
   @Column(name="name", nullable=false, length=50)
   public String getName() {
     return this.name;
   }
 
   public void setName(String name) {
     this.name = name;
   }
   @Column(name="short_name", nullable=false, length=50)
   public String getShortName() {
     return this.shortName;
   }
 
   public void setShortName(String shortName) {
     this.shortName = shortName;
   }
   @Column(name="tree_number", nullable=true, length=150)
   public String getNumber() {
     return this.number;
   }
 
   public void setNumber(String number) {
     this.number = number;
   }
   @Column(name="visit_path", nullable=true, length=30)
   public String getVisitPath() {
     return this.visitPath;
   }
 
   public void setVisitPath(String visitPath) {
     this.visitPath = visitPath;
   }
   @Column(name="priority", nullable=false, length=10)
   public Integer getPriority() {
     return this.priority;
   }
 
   public void setPriority(Integer priority) {
     this.priority = priority;
   }
   @Formula("(select count(s.sign_id) from tq_article_sign s where s.depart_id=depart_id)")
   public Integer getSignCount() {
     return this.signCount;
   }
 
   public void setSignCount(Integer signCount) {
     this.signCount = signCount;
   }
   @Column(name="is_show", nullable=false, length=1)
   public Boolean getShow() {
     return this.show;
   }
 
   public void setShow(Boolean show) {
     this.show = show;
   }
   @Column(name="is_all_channel", nullable=false, length=1)
   public Boolean getAllChannel() {
     return this.allChannel;
   }
 
   public void setAllChannel(Boolean allChannel) {
     this.allChannel = allChannel;
   }
   @Temporal(TemporalType.TIMESTAMP)
   @Column(name="create_time", nullable=false, length=19)
   public Date getCreateTime() { return this.createTime; }
 
   public void setCreateTime(Date createTime)
   {
     this.createTime = createTime;
   }
   @Formula("(select count(a.article_id) from tq_article a,tq_channel c where a.depart_id=depart_id and a.channel_id=c.channel_id and c.flow_id is not null and a.status!=3)")
   public Integer getReportCount() {
     return this.reportCount;
   }
 
   public void setReportCount(Integer reportCount) {
     this.reportCount = reportCount;
   }
   @Formula("(select count(a.article_id) from tq_article a,tq_channel c where a.status=2 and a.depart_id=depart_id and a.channel_id=c.channel_id and c.flow_id is not null)")
   public Integer getUseCount() {
     return this.useCount;
   }
 
   public void setUseCount(Integer useCount) {
     this.useCount = useCount;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="parent_id", nullable=true)
   public Depart getParent() { return this.parent; }
 
   public void setParent(Depart parent)
   {
     this.parent = parent;
   }
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="site_id", nullable=false)
   public Site getSite() { return this.site; }
 
   public void setSite(Site site)
   {
     this.site = site;
   }
   @ManyToMany(fetch=FetchType.LAZY)
   @JoinTable(name="tq_depart_channel", joinColumns={@JoinColumn(name="depart_id")}, inverseJoinColumns={@JoinColumn(name="channel_id")})
   public Set<Channel> getChannels() { return this.channels;
   }
 
   public void setChannels(Set<Channel> channels)
   {
     this.channels = channels;
   }
   @OneToMany(fetch=FetchType.LAZY, cascade={javax.persistence.CascadeType.REMOVE}, mappedBy="parent")
   @OrderBy("priority asc")
   public Set<Depart> getChild() { return this.child; }
 
   public void setChild(Set<Depart> child)
   {
     this.child = child;
   }
 }


 
 
 
package com.portal.doccenter.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.portal.sysmgr.entity.Site;
import com.portal.usermgr.entity.Depart;
import com.portal.usermgr.entity.Group;
import com.portal.usermgr.entity.Role;
import com.portal.usermgr.entity.User;

@Entity
@Table(name = "tq_article")
public class Article implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final byte BACK = -1;
	public static final byte CHECKING = 1;
	public static final byte CHECKED = 2;
	public static final byte RECYCLE = 3;
	public static final String DOC_URL = "doc";
	private Integer id;
	private String title;
	private String shortTitle;
	private Date releaseDate;
	private String titleColor;
	private Boolean bold;
	private Boolean top;
	private Boolean recommend;
	private Byte status;
	private String style;
	private ArticleExt articleExt;
	private ArticleTxt articleTxt;
	private DocStatis docStatis;
	private Site site;
	private Model model;
	private User user;
	private Depart inputDepart;
	private User checkUser;
	private Role role;
	private Channel channel;
	private Set<Group> viewGroups;
	private List<ArticlePicture> pics;
	private List<ArticleAttachment> atts;
	private Map<String, String> attr;
	private Set<ArticleSign> signs;

	@Transient
	public String getUrl() {
		return getUrl(Integer.valueOf(1));
	}

	@Transient
	public String getUrl(Integer page) {
		if (StringUtils.isNotBlank(getLink())) {
			return getLink();
		}
		if (page == null) {
			page = Integer.valueOf(1);
		}
		return getUrlStatic(page);
	}

	@Transient
	public String getUrlDynamic(Integer page) {
		if (page == null) {
			page = Integer.valueOf(1);
		}
		StringBuilder sb = new StringBuilder(getSite().getUrl());
		sb.append("doc");
		sb.append("/");
		sb.append(getId());
		sb.append("_");
		sb.append(page);
		sb.append(".jsp");
		return sb.toString();
	}

	@Transient
	public String getUrlStatic(Integer page) {
		StringBuilder sb = new StringBuilder(getSite().getUrl());
		if (getSite().getStaticSuffix().booleanValue()) {
			sb.append(getUrlStaticHaveSuffix(page));
			return sb.toString();
		}
		sb.append(getUrlStaticNoSuffix(page));
		return sb.toString();
	}

	@Transient
	public String getUrlStaticHaveSuffix(Integer page) {
		StringBuilder sb = new StringBuilder();
		sb.append(getChannel().getPath());
		sb.append("/");
		sb.append(getId());
		if ((page != null) && (page.intValue() > 1)) {
			sb.append("_");
			sb.append(page);
		}
		sb.append(".html");
		return sb.toString();
	}

	@Transient
	public String getUrlStaticNoSuffix(Integer page) {
		StringBuilder sb = new StringBuilder();
		sb.append(getChannel().getPath());
		sb.append("/");
		sb.append(getId());
		if ((page != null) && (page.intValue() > 1)) {
			sb.append("_");
			sb.append(page);
		}
		sb.append("/");
		return sb.toString();
	}

	@Transient
	public String getStaticRealPath() {
		return getStaticRealPath(Integer.valueOf(1));
	}

	@Transient
	public String getStaticRealPath(Integer page) {
		String path = getChannel().getChannelCoreRealPath();
		StringBuilder sb = new StringBuilder(path);
		sb.append("/");
		sb.append("doc");
		sb.append(getStaticTimePath());
		sb.append("/");
		sb.append(getId());
		if ((page != null) && (page.intValue() > 1)) {
			sb.append("_");
			sb.append(page);
		}
		sb.append(".html");
		return sb.toString();
	}

	@Transient
	public String getStaticTimePath() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getReleaseDate());
		int year = cal.get(1);
		int month = cal.get(2) + 1;
		String mm = "0" + month;
		int day = cal.get(5);
		String dd = "0" + day;
		return "/" + String.valueOf(year) + "/" + mm + "/" + dd;
	}

	@Transient
	public boolean getStaticDoc() {
		if (!StringUtils.isBlank(getLink())) {
			return false;
		}
		if ((getViewGroupsExt() != null) && (getViewGroupsExt().size() > 0)) {
			return false;
		}
		if (getSite().getStaticDoc().equals(Site.YES_STATIC)) {
			return true;
		}
		if (getSite().getStaticDoc().equals(Site.NO_STATIC)) {
			return false;
		}
		return getChannel().getStaticDoc();
	}

	@Transient
	public boolean isChanged(long time) {
		Date d = getSite().getUpdateTime();
		if (d == null) {
			return false;
		}

		return d.getTime() >= time;
	}

	@Transient
	public boolean isNew() {
		return isNew(1);
	}

	@Transient
	public boolean isNew(int day) {
		Date d = new Date();
		long s = d.getTime() - getReleaseDate().getTime();
		s /= 1000L;

		return s < 86400 * day;
	}

	public void addToGroups(Group group) {
		Set groups = getViewGroups();
		if (groups == null) {
			groups = new HashSet();
			setViewGroups(groups);
		}
		groups.add(group);
	}

	public void addToAttachmemts(String path, String name) {
		List list = getAtts();
		if (list == null) {
			list = new ArrayList();
			setAtts(list);
		}
		ArticleAttachment ca = new ArticleAttachment();
		ca.setPath(path);
		ca.setName(name);
		ca.setCount(Integer.valueOf(0));
		list.add(ca);
	}

	public void addToPictures(String path, String desc, Boolean thumb, String style) {
		List list = getPics();
		if (list == null) {
			list = new ArrayList();
			setPics(list);
		}
		ArticlePicture ap = new ArticlePicture();
		ap.setImgPath(path);
		ap.setDescription(desc);
		ap.setThumb(thumb);
		ap.setStyle(style);
		list.add(ap);
	}

	public void addToSigns(ArticleSign sign) {
		Set signs = getSigns();
		if (signs == null) {
			signs = new HashSet();
			setSigns(signs);
		}
		signs.add(sign);
	}

	@Transient
	public boolean getSign(User user) {
		if (user == null) {
			return false;
		}
		if (user.getAdmin() == null) {
			return false;
		}
		Set<ArticleSign> signs = getSigns();
		for (ArticleSign sign : signs) {
			if (sign.getDepart().equals(user.getAdmin().getDepart(getSite().getId()))) {
				return true;
			}
		}
		return false;
	}

	@Transient
	public String getStatusString() {
		Byte status = getStatus();
		if (status.byteValue() == 2)
			return "<span style='color:blue'>已审核</span>";
		if (status.byteValue() == 3)
			return "<span style='color:red'>回收站</span>";
		if (status.byteValue() == 1) {
			return "<span style='color:red'>审核中</span>";
		}
		return "<span style='color:red'>退稿</span>";
	}

	@Transient
	public boolean isChecked() {
		Byte status = getStatus();

		return status.byteValue() == 2;
	}

	@Transient
	public Set<Group> getViewGroupsExt() {
		Set set = getViewGroups();
		if ((set != null) && (set.size() > 0)) {
			return set;
		}
		return getChannel().getViewGroups();
	}

	@Transient
	public String getTplContentOrDef() {
		String tpl = getTplContent();
		String root = getSite().getSolutionPath();
		if (!StringUtils.isBlank(tpl)) {
			return root + tpl;
		}
		return root + getChannel().getTplDoc(getModel().getId());
	}

	public void init() {
		if (getRecommend() == null) {
			setRecommend(Boolean.valueOf(false));
		}
		if (getReleaseDate() == null) {
			setReleaseDate(new Timestamp(System.currentTimeMillis()));
		}
		if (getTop() == null) {
			setTop(Boolean.valueOf(false));
		}
		if (getViewGroups() == null) {
			setViewGroups(new HashSet());
		}
		if (getPics() == null) {
			setPics(new ArrayList());
		}
		if (getAtts() == null)
			setAtts(new ArrayList());
	}

	@Transient
	public int getPageCount() {
		int txtCount = getTxtCount();
		if (txtCount <= 1) {
			List pics = getPics();
			if (pics != null) {
				int picCount = pics.size();
				if (picCount > 1) {
					return picCount;
				}
			}
		}
		return txtCount;
	}

	@Transient
	public int getTxtCount() {
		ArticleTxt txt = getArticleTxt();
		if (txt != null) {
			return txt.getTxtCount();
		}
		return 1;
	}

	@Transient
	public String getTxtByNo(int pageNo) {
		ArticleTxt txt = getArticleTxt();
		if (txt != null) {
			return txt.getTxtByNo(pageNo);
		}
		return null;
	}

	@Transient
	public Integer getViewsCount() {
		DocStatis ds = getDocStatis();
		if (ds != null) {
			return ds.getViewsCount();
		}
		return Integer.valueOf(0);
	}

	@Transient
	public Integer getCommentCount() {
		DocStatis ds = getDocStatis();
		if (ds != null) {
			return ds.getCommentCount();
		}
		return Integer.valueOf(0);
	}

	@Transient
	public Integer getUps() {
		DocStatis ds = getDocStatis();
		if (ds != null) {
			return ds.getUps();
		}
		return Integer.valueOf(0);
	}

	@Transient
	public Integer getTreads() {
		DocStatis ds = getDocStatis();
		if (ds != null) {
			return ds.getTreads();
		}
		return Integer.valueOf(0);
	}

	@Transient
	public String getStitle() {
		if (getShortTitle() == null) {
			return getTitle();
		}
		return getShortTitle();
	}

	@Transient
	public String getSubTitle() {
		ArticleExt ext = getArticleExt();
		if (ext != null) {
			return ext.getSubTitle();
		}
		return null;
	}

	@Transient
	public Boolean getShowIndex() {
		ArticleExt ext = getArticleExt();
		if (ext != null) {
			return ext.getShowIndex();
		}
		return Boolean.valueOf(false);
	}

	@Transient
	public String getDescription() {
		ArticleExt ext = getArticleExt();
		if (ext != null) {
			return ext.getDescription();
		}
		return null;
	}

	@Transient
	public String getAuthor() {
		ArticleExt ext = getArticleExt();
		if (ext != null) {
			return ext.getAuthor();
		}
		return null;
	}

	@Transient
	public String getOrigin() {
		ArticleExt ext = getArticleExt();
		if (ext != null) {
			return ext.getOrigin();
		}
		return null;
	}

	@Transient
	public String getOriginUrl() {
		ArticleExt ext = getArticleExt();
		if (ext != null) {
			return ext.getOriginUrl();
		}
		return null;
	}

	@Transient
	public Boolean getCommentControl() {
		ArticleExt ext = getArticleExt();
		if ((ext != null) && (ext.getCommentControl() != null)) {
			return ext.getCommentControl();
		}

		return getChannel().getCommentControl();
	}

	@Transient
	public String getLink() {
		ArticleExt ext = getArticleExt();
		if (ext != null) {
			return ext.getLink();
		}
		return null;
	}

	@Transient
	public String getTagStr() {
		ArticleExt ext = getArticleExt();
		if (ext != null) {
			return ext.getTagStr();
		}
		return null;
	}

	@Transient
	public String getTplContent() {
		ArticleExt ext = getArticleExt();
		if (ext != null) {
			return ext.getTplContent();
		}
		return null;
	}

	@Transient
	public String getTxtValue() {
		ArticleTxt txt = getArticleTxt();
		if (txt != null) {
			return txt.getTxt();
		}
		return null;
	}

	@Transient
	public String getPicStyle(String typeId) {
		List<ArticlePicture> list = getPics();
		if (list != null) {
			for (ArticlePicture ap : list) {
				if ((ap.getStyle() != null) && (ap.getStyle().indexOf("," + typeId + ",") > -1) && (ap.getThumb().booleanValue())) {
					return ap.getImgPath();
				}
			}
		}
		return null;
	}

	@Transient
	public String getGraphic() {
		List<ArticlePicture> list = getPics();
		if (list != null) {
			for (ArticlePicture ap : list) {
				if ((ap.getStyle() != null) && (ap.getStyle().indexOf(",0,") > -1) && (ap.getThumb().booleanValue())) {
					return ap.getImgPath();
				}
			}
		}
		return null;
	}

	@Transient
	public String getCover() {
		List<ArticlePicture> list = getPics();
		if (list != null) {
			for (ArticlePicture ap : list) {
				if ((ap.getStyle() != null) && (ap.getStyle().indexOf(",1,") > -1) && (!ap.getThumb().booleanValue())) {
					return ap.getImgPath();
				}
			}
		}
		return null;
	}

	@Transient
	public Date getDate() {
		return getReleaseDate();
	}

	@Id
	@Column(name = "article_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_article", pkColumnValue = "tq_article", table = "tq_gen_table", pkColumnName = "tg_gen_name", valueColumnName = "tq_gen_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_article")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "title", nullable = false, length = 255)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "short_title", nullable = true, length = 100)
	public String getShortTitle() {
		return this.shortTitle;
	}

	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "release_date", nullable = false, length = 19)
	public Date getReleaseDate() {
		return this.releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	@Column(name = "title_color", nullable = true, length = 10)
	public String getTitleColor() {
		return this.titleColor;
	}

	public void setTitleColor(String titleColor) {
		this.titleColor = titleColor;
	}

	@Column(name = "is_bold", nullable = true)
	public Boolean getBold() {
		return this.bold;
	}

	public void setBold(Boolean bold) {
		this.bold = bold;
	}

	@Column(name = "is_top", nullable = true)
	public Boolean getTop() {
		return this.top;
	}

	public void setTop(Boolean top) {
		this.top = top;
	}

	@Column(name = "is_recommend", nullable = true)
	public Boolean getRecommend() {
		return this.recommend;
	}

	public void setRecommend(Boolean recommend) {
		this.recommend = recommend;
	}

	@Column(name = "status", nullable = false, length = 5)
	public Byte getStatus() {
		return this.status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	@Column(name = "style", nullable = true, length = 20)
	public String getStyle() {
		return this.style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	@OneToOne(cascade = { javax.persistence.CascadeType.REMOVE }, fetch = FetchType.LAZY, mappedBy = "article")
	@PrimaryKeyJoinColumn
	public ArticleExt getArticleExt() {
		return this.articleExt;
	}

	public void setArticleExt(ArticleExt articleExt) {
		this.articleExt = articleExt;
	}

	@OneToOne(cascade = { javax.persistence.CascadeType.REMOVE }, fetch = FetchType.LAZY, mappedBy = "article")
	@PrimaryKeyJoinColumn
	public ArticleTxt getArticleTxt() {
		return this.articleTxt;
	}

	public void setArticleTxt(ArticleTxt articleTxt) {
		this.articleTxt = articleTxt;
	}

	@OneToOne(cascade = { javax.persistence.CascadeType.REMOVE }, fetch = FetchType.LAZY, mappedBy = "doc")
	@PrimaryKeyJoinColumn
	public DocStatis getDocStatis() {
		return this.docStatis;
	}

	public void setDocStatis(DocStatis docStatis) {
		this.docStatis = docStatis;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "site_id", nullable = false)
	public Site getSite() {
		return this.site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "model_id", nullable = false)
	public Model getModel() {
		return this.model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", nullable = true)
	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "depart_id", nullable = true)
	public Depart getInputDepart() {
		return this.inputDepart;
	}

	public void setInputDepart(Depart inputDepart) {
		this.inputDepart = inputDepart;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "check_id", nullable = true)
	public User getCheckUser() {
		return this.checkUser;
	}

	public void setCheckUser(User checkUser) {
		this.checkUser = checkUser;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "channel_id", nullable = false)
	public Channel getChannel() {
		return this.channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tq_article_group_view", joinColumns = { @JoinColumn(name = "article_id") }, inverseJoinColumns = { @JoinColumn(name = "group_id") })
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public Set<Group> getViewGroups() {
		return this.viewGroups;
	}

	public void setViewGroups(Set<Group> viewGroups) {
		this.viewGroups = viewGroups;
	}

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "tq_article_picture", joinColumns = { @JoinColumn(name = "article_id") })
	@OrderColumn(name = "priority")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<ArticlePicture> getPics() {
		return this.pics;
	}

	public void setPics(List<ArticlePicture> pics) {
		this.pics = pics;
	}

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "tq_article_attachment", joinColumns = { @JoinColumn(name = "article_id") })
	@OrderColumn(name = "priority")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<ArticleAttachment> getAtts() {
		return this.atts;
	}

	public void setAtts(List<ArticleAttachment> atts) {
		this.atts = atts;
	}

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "tq_article_attr",
	                joinColumns = { @JoinColumn(name = "article_id") })
	@MapKeyColumn(name = "attr_name", length = 30)
	@Column(name = "attr_value", length = 255)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public Map<String, String> getAttr() {
		return this.attr;
	}

	public void setAttr(Map<String, String> attr) {
		this.attr = attr;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = { javax.persistence.CascadeType.REMOVE }, 
			   mappedBy = "article")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public Set<ArticleSign> getSigns() {
		return this.signs;
	}

	public void setSigns(Set<ArticleSign> signs) {
		this.signs = signs;
	}
	
	
}

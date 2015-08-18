package com.portal.sysmgr.entity;

import com.portal.extrafunc.entity.ForumStatis;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.apache.commons.lang.StringUtils;

@Entity
@Table(name = "tq_site")
public class Site implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final Byte NO_STATIC = Byte.valueOf((byte) 0);

	public static final Byte YES_STATIC = Byte.valueOf((byte) 1);

	public static final Byte CHANNEL_STATIC = Byte.valueOf((byte) 2);
	public static final String PROTOCOL = "http://";
	public static final String HTML = "html";
	private Integer id;
	private String domain;
	private String path;
	private String name;
	private String shortName;
	private String contextPath;
	private Integer port;
	private String tplStyle;
	private String title;
	private String keywords;
	private String description;
	private Byte staticChannel;
	private Byte staticDoc;
	private Boolean staticSuffix;
	private Date updateTime;
	private String tplIndex;
	private Boolean terminus;
	private ForumStatis forumStatis;
	private SiteConfig config;

	@Transient
	public String getUrl() {
		StringBuilder sb = new StringBuilder();
		sb.append("http://");
		sb.append(getDomain());
		if ((getPort() != null) && (!getPort().equals(Integer.valueOf(80)))) {
			sb.append(":");
			sb.append(getPort());
		}
		if (getContextPath() != null) {
			sb.append(getContextPath());
		}
		sb.append("/");
		return sb.toString();
	}

	@Transient
	public String getStaticRealPath() {
		StringBuilder sb = new StringBuilder();
		sb.append("/");
		sb.append("html").append("/");
		sb.append(getPath()).append("/");
		sb.append("index").append(".html");
		return sb.toString();
	}

	@Transient
	public String getTplIndexOrDef() {
		if (!StringUtils.isBlank(getTplIndex())) {
			return getSolutionPath() + getTplIndex();
		}
		return null;
	}

	@Transient
	public String getTplPath() {
		return "/WEB-INF/tpl/" + getPath();
	}

	@Transient
	public String getSolutionPath() {
		return getTplPath() + "/" + getTplStyle();
	}

	@Transient
	public String getResPath() {
		return "/skin/" + getPath();
	}

	@Transient
	public String getResSolutionPath() {
		return "/skin/" + getPath() + "/" + getTplStyle();
	}

	@Transient
	public String getUploadPath() {
		return "/member/upload/" + getPath();
	}

	@Transient
	public String getDefImg() {
		String skin = "/skin/" + getPath() + "/" + getTplStyle();
		return skin + "/img/nophoto.gif";
	}

	@Transient
	public Boolean getCommentCheck() {
		SiteConfig config = getConfig();
		if (config != null) {
			return config.getCommentCheck();
		}
		return Boolean.valueOf(true);
	}

	@Transient
	public Boolean getCommentLogin() {
		SiteConfig config = getConfig();
		if (config != null) {
			return config.getCommentLogin();
		}
		return Boolean.valueOf(false);
	}

	@Transient
	public Boolean getMessageCheck() {
		SiteConfig config = getConfig();
		if (config != null) {
			return config.getMessageCheck();
		}
		return Boolean.valueOf(true);
	}

	@Transient
	public Boolean getMessageLogin() {
		SiteConfig config = getConfig();
		if (config != null) {
			return config.getMessageLogin();
		}
		return Boolean.valueOf(false);
	}

	@Transient
	public Boolean getMessageName() {
		SiteConfig config = getConfig();
		if (config != null) {
			return config.getMessageName();
		}
		return Boolean.valueOf(false);
	}

	@Transient
	public Boolean getMessageMobile() {
		SiteConfig config = getConfig();
		if (config != null) {
			return config.getMessageMobile();
		}
		return Boolean.valueOf(false);
	}

	@Transient
	public Boolean getMessageEmail() {
		SiteConfig config = getConfig();
		if (config != null) {
			return config.getMessageEmail();
		}
		return Boolean.valueOf(false);
	}

	@Transient
	public Boolean getMessageAddress() {
		SiteConfig config = getConfig();
		if (config != null) {
			return config.getMessageAddress();
		}
		return Boolean.valueOf(false);
	}

	@Transient
	public Boolean getMessageZipcode() {
		SiteConfig config = getConfig();
		if (config != null) {
			return config.getMessageZipcode();
		}
		return Boolean.valueOf(false);
	}

	@Transient
	public Boolean getRegOpen() {
		SiteConfig config = getConfig();
		if (config != null) {
			return config.getRegOpen();
		}
		return Boolean.valueOf(true);
	}

	@Transient
	public Integer getRegMin() {
		SiteConfig config = getConfig();
		if (config != null) {
			return config.getRegMin();
		}
		return null;
	}

	@Transient
	public Integer getRegMax() {
		SiteConfig config = getConfig();
		if (config != null) {
			return config.getRegMax();
		}
		return null;
	}

	@Transient
	public Boolean getRegCheck() {
		SiteConfig config = getConfig();
		if (config != null) {
			return config.getRegCheck();
		}
		return Boolean.valueOf(false);
	}

	@Transient
	public Integer getLoginCount() {
		SiteConfig config = getConfig();
		if (config != null) {
			return config.getLoginCount();
		}
		return null;
	}

	@Transient
	public boolean getNeedCheck() {
		return (getLoginCount() != null) && (getLoginCount().intValue() > 0);
	}

	public static Integer[] fetchIds(Collection<Site> sites) {
		if (sites == null) {
			return null;
		}
		Integer[] ids = new Integer[sites.size()];
		int i = 0;
		for (Site s : sites) {
			ids[(i++)] = s.getId();
		}
		return ids;
	}

	public void init() {
		if (StringUtils.isBlank(getTplStyle())) {
			setTplStyle("default");
		}
		if (getTerminus() == null)
			setTerminus(Boolean.valueOf(false));
	}

	public void initTime() {
		setUpdateTime(new Timestamp(System.currentTimeMillis()));
	}

	@Id
	@Column(name = "site_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_site", pkColumnValue = "tq_site", table = "tq_gen_table", pkColumnName = "tg_gen_name", valueColumnName = "tq_gen_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_site")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "domain", unique = true, nullable = false, length = 50)
	public String getDomain() {
		return this.domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	@Column(name = "site_path", nullable = false, length = 20)
	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Column(name = "site_name", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "short_name", nullable = true, length = 100)
	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Column(name = "context_path", nullable = true, length = 20)
	public String getContextPath() {
		return this.contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	@Column(name = "port", nullable = true, length = 10)
	public Integer getPort() {
		return this.port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	@Column(name = "tpl_style", nullable = false, length = 50)
	public String getTplStyle() {
		return this.tplStyle;
	}

	public void setTplStyle(String tplStyle) {
		this.tplStyle = tplStyle;
	}

	@Column(name = "title", nullable = true, length = 80)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "keywords", nullable = true, length = 100)
	public String getKeywords() {
		return this.keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	@Column(name = "description", nullable = true, length = 255)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "is_static_channel", nullable = false, length = 1)
	public Byte getStaticChannel() {
		return this.staticChannel;
	}

	public void setStaticChannel(Byte staticChannel) {
		this.staticChannel = staticChannel;
	}

	@Column(name = "is_static_doc", nullable = false, length = 1)
	public Byte getStaticDoc() {
		return this.staticDoc;
	}

	public void setStaticDoc(Byte staticDoc) {
		this.staticDoc = staticDoc;
	}

	@Column(name = "is_static_suffix", nullable = false, length = 1)
	public Boolean getStaticSuffix() {
		return this.staticSuffix;
	}

	public void setStaticSuffix(Boolean staticSuffix) {
		this.staticSuffix = staticSuffix;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time", nullable = true, length = 19)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "tpl_index", nullable = true, length = 50)
	public String getTplIndex() {
		return this.tplIndex;
	}

	public void setTplIndex(String tplIndex) {
		this.tplIndex = tplIndex;
	}

	@Column(name = "is_terminus", nullable = false, length = 1)
	public Boolean getTerminus() {
		return this.terminus;
	}

	public void setTerminus(Boolean terminus) {
		this.terminus = terminus;
	}

	@OneToOne(cascade = { javax.persistence.CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public ForumStatis getForumStatis() {
		return this.forumStatis;
	}

	public void setForumStatis(ForumStatis forumStatis) {
		this.forumStatis = forumStatis;
	}

	@OneToOne(cascade = { javax.persistence.CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public SiteConfig getConfig() {
		return this.config;
	}

	public void setConfig(SiteConfig config) {
		this.config = config;
	}
}

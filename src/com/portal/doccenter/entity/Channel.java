package com.portal.doccenter.entity;

import com.javassf.basic.comparator.BeanComparator;
import com.portal.sysmgr.entity.Site;
import com.portal.usermgr.entity.Admin;
import com.portal.usermgr.entity.AdminCheck;
import com.portal.usermgr.entity.Depart;
import com.portal.usermgr.entity.Group;
import com.portal.usermgr.entity.Role;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "tq_channel")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Channel implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String HTML = "html";
	public static final String NORMAL = "normalstatic";
	public static final String DEPART = "departstatic";
	private Integer id;
	private String name;
	private String path;
	private String number;
	private Integer priority;
	private Boolean alone;
	private Boolean show;
	private ChannelExt ext;
	private ChannelTxt txt;
	private Site site;
	private WorkFlow flow;
	private Depart inputDepart;
	private Channel parent;
	private Set<ChnlTplSelection> tpls;
	private Set<Channel> child;
	private Set<Group> viewGroups;
	private Set<Group> contriGroups;
	private Set<Depart> departs;
	private Set<AdminCheck> checks;

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
		StringBuilder sb = new StringBuilder(getSite().getUrl());
		sb.append(getPath());
		sb.append("/");
		sb.append("index");
		if (page.intValue() > 1) {
			sb.append("_");
			sb.append(page);
		}
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
		String path = getPath();
		path = path + "/";
		path = path + getChannelPageCorePath(page);
		return path;
	}

	@Transient
	public String getUrlStaticNoSuffix(Integer page) {
		StringBuilder sb = new StringBuilder();
		sb.append(getPath());
		if ((page != null) && (page.intValue() > 1)) {
			sb.append("_");
			sb.append(page);
		}
		sb.append("/");
		return sb.toString();
	}

	@Transient
	public String getChannelCoreRealPath() {
		StringBuilder sb = new StringBuilder();
		sb.append("/");
		sb.append("html").append("/");
		sb.append(getSite().getPath()).append("/");
		sb.append("normalstatic");
		sb.append("/");
		sb.append(getPath());
		return sb.toString();
	}

	@Transient
	public String getChannelPageCorePath(Integer page) {
		StringBuilder sb = new StringBuilder();
		sb.append("index");
		if ((page != null) && (page.intValue() > 1)) {
			sb.append("_");
			sb.append(page);
		}
		sb.append(".html");
		return sb.toString();
	}

	@Transient
	public String getChannelRealPath(Integer page) {
		String path = getChannelCoreRealPath();
		path = path + "/";
		path = path + getChannelPageCorePath(page);
		return path;
	}

	@Transient
	public String getStaticDocPath() {
		String path = getChannelCoreRealPath();
		path = path + "/";
		path = path + "doc";
		return path;
	}

	@Transient
	public boolean getStaticChannel() {
		if (!StringUtils.isBlank(getLink())) {
			return false;
		}
		if (getSite().getStaticChannel().equals(Site.YES_STATIC)) {
			return true;
		}
		if (getSite().getStaticChannel().equals(Site.NO_STATIC)) {
			return false;
		}
		ChannelExt ext = getExt();
		if (ext != null) {
			return ext.getStaticChannel().booleanValue();
		}
		return false;
	}

	@Transient
	public boolean getStaticChannelSuper() {
		ChannelExt ext = getExt();
		if (ext != null) {
			return ext.getStaticChannel().booleanValue();
		}
		return false;
	}

	@Transient
	public boolean getStaticDoc() {
		ChannelExt ext = getExt();
		if (ext != null) {
			return ext.getStaticDoc().booleanValue();
		}
		return false;
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
	public List<Channel> getNodeList() {
		LinkedList list = new LinkedList();
		Channel node = this;
		while (node != null) {
			list.addFirst(node);
			node = node.getParent();
		}
		return list;
	}

	@Transient
	public Integer[] getNodeIds() {
		List<Channel> channels = getNodeList();
		Integer[] ids = new Integer[channels.size()];
		int i = 0;
		for (Channel c : channels) {
			ids[(i++)] = c.getId();
		}
		return ids;
	}

	@Transient
	public int getDeep() {
		int deep = 0;
		Channel parent = getParent();
		while (parent != null) {
			deep++;
			parent = parent.getParent();
		}
		return deep;
	}

	@Transient
	public String getTplChannelOrDef() {
		String tpl = getTplChannel();
		if (!StringUtils.isBlank(tpl)) {
			String root = getSite().getSolutionPath();
			return root + tpl;
		}
		return null;
	}

	public void addToTpls(ChnlTplSelection chnltpl) {
		Set chnltpls = getTpls();
		if (chnltpls == null) {
			chnltpls = new HashSet();
			setTpls(chnltpls);
		}
		chnltpls.add(chnltpl);
	}

	public void addToViewGroups(Group group) {
		Set groups = getViewGroups();
		if (groups == null) {
			groups = new TreeSet();
			setViewGroups(groups);
		}
		groups.add(group);
		group.getViewChannels().add(this);
	}

	public void addToContriGroups(Group group) {
		Set groups = getContriGroups();
		if (groups == null) {
			groups = new TreeSet();
			setContriGroups(groups);
		}
		groups.add(group);
		group.getContriChannels().add(this);
	}

	public void addToDeparts(Depart depart) {
		Set set = getDeparts();
		if (set == null) {
			set = new HashSet();
			setDeparts(set);
		}
		set.add(depart);
		depart.addToChannels(this);
	}

	public void addToChilds(Channel child) {
		Set set = getChild();
		if (set == null) {
			set = new HashSet();
			setChild(set);
		}
		set.add(child);
	}

	public void init() {
		if (getPriority() == null) {
			setPriority(Integer.valueOf(10));
		}
		if (getShow() == null) {
			setShow(Boolean.valueOf(true));
		}
		if (getAlone() == null)
			setAlone(Boolean.valueOf(false));
	}

	public void initTreeNumber() {
		String number = "-";
		if (getParent() != null) {
			number = getParent().getNumber();
		}
		setNumber(number + getId() + "-");
	}

	@Transient
	public String getWholeName() {
		String name = getName();
		Channel parent = getParent();
		if (parent != null) {
			name = parent.getWholeName() + "-->" + name;
		}
		return name;
	}

	@Transient
	public FlowStep getFlowStep(Integer roleId) {
		if (getFlow() == null) {
			return null;
		}
		return getFlow().getStep(roleId);
	}

	@Transient
	public FlowStep getNextFlowStep(Integer roleId) {
		if (getFlow() == null) {
			return null;
		}
		return getFlow().getNextFlowStep(roleId);
	}

	@Transient
	public Role getNextRole(Integer roleId) {
		if (getNextFlowStep(roleId) == null) {
			return null;
		}
		return getNextFlowStep(roleId).getRole();
	}

	@Transient
	public boolean isLastStep(Role role) {
		return getFlow().getLastFlowStep().getRole().equals(role);
	}

	@Transient
	public boolean isDocChecked(Admin admin) {
		if (admin == null) {
			return false;
		}
		if ((admin.haveAllManage(getSite().getId())) || (admin.getManageStatus(getSite().getId()).equals(Byte.valueOf((byte) 3)))) {
			return true;
		}
		if (admin.getManageStatus(getSite().getId()).equals(Byte.valueOf((byte) 2))) {
			if (isLastStep(admin.getRole(getSite().getId())))
				return true;
		}
		return false;
	}

	@Transient
	public String getLink() {
		ChannelExt ext = getExt();
		if (ext != null) {
			return ext.getLink();
		}
		return null;
	}

	@Transient
	public String getImgSrc() {
		ChannelExt ext = getExt();
		if (ext != null) {
			return ext.getImgSrc();
		}
		return null;
	}

	@Transient
	public String getTplChannel() {
		ChannelExt ext = getExt();
		if (ext != null) {
			return ext.getTplChannel();
		}
		return null;
	}

	@Transient
	public String getTplDoc(Integer modelId) {
		Set<ChnlTplSelection> tpls = getTpls();
		if (tpls != null) {
			for (ChnlTplSelection tpl : tpls) {
				if (tpl.getModelId().equals(modelId)) {
					return tpl.getTplDoc();
				}
			}
		}
		return null;
	}

	@Transient
	public List<Model> getModelList() {
		Set<ChnlTplSelection> tpls = getTpls();
		Comparator bc = new BeanComparator();
		if (tpls != null) {
			List<Model> modelList = new ArrayList();
			for (ChnlTplSelection tpl : tpls) {
				modelList.add(tpl.getModel());
			}
			Collections.sort(modelList, bc);//传入比较器 进行排序
			return modelList;
		}
		return null;
	}

	@Transient
	public String getTitle() {
		ChannelExt ext = getExt();
		if (ext != null) {
			return ext.getTitle();
		}
		return null;
	}

	@Transient
	public String getKeywords() {
		ChannelExt ext = getExt();
		if (ext != null) {
			return ext.getKeywords();
		}
		return null;
	}

	@Transient
	public String getDescription() {
		ChannelExt ext = getExt();
		if (ext != null) {
			return ext.getDescription();
		}
		return null;
	}

	@Transient
	public Boolean getCommentControl() {
		ChannelExt ext = getExt();
		if (ext != null) {
			return ext.getCommentControl();
		}
		return Boolean.valueOf(false);
	}

	@Transient
	public Boolean getUpdownControl() {
		ChannelExt ext = getExt();
		if (ext != null) {
			return ext.getUpdownControl();
		}
		return null;
	}

	@Transient
	public Boolean getBlank() {
		ChannelExt ext = getExt();
		if (ext != null) {
			return ext.getBlank();
		}
		return null;
	}

	@Transient
	public Boolean getSign() {
		ChannelExt ext = getExt();
		if (ext != null) {
			return ext.getSign();
		}
		return Boolean.valueOf(false);
	}

	@Transient
	public String getTxtValue() {
		ChannelTxt txt = getTxt();
		if (txt != null) {
			return txt.getTxtval();
		}
		return null;
	}

	public static Integer[] fetchIds(Collection<Channel> channels) {
		if (channels == null) {
			return null;
		}
		Integer[] ids = new Integer[channels.size()];
		int i = 0;
		for (Channel c : channels) {
			ids[(i++)] = c.getId();
		}
		return ids;
	}

	@Id
	@Column(name = "channel_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_channel", pkColumnValue = "tq_channel", table = "tq_gen_table", pkColumnName = "tg_gen_name", valueColumnName = "tq_gen_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_channel")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "channel_name", nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "channel_path", nullable = false, length = 30)
	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Column(name = "chnl_number", nullable = true, length = 100)
	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Column(name = "priority", nullable = false, length = 10)
	public Integer getPriority() {
		return this.priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	@Column(name = "is_alone", nullable = false, length = 1)
	public Boolean getAlone() {
		return this.alone;
	}

	public void setAlone(Boolean alone) {
		this.alone = alone;
	}

	@Column(name = "is_show", nullable = false, length = 1)
	public Boolean getShow() {
		return this.show;
	}

	public void setShow(Boolean show) {
		this.show = show;
	}

	@OneToOne(cascade = { javax.persistence.CascadeType.REMOVE }, fetch = FetchType.LAZY, mappedBy = "channel")
	@PrimaryKeyJoinColumn
	public ChannelExt getExt() {
		return this.ext;
	}

	public void setExt(ChannelExt ext) {
		this.ext = ext;
	}

	@OneToOne(cascade = { javax.persistence.CascadeType.REMOVE }, fetch = FetchType.LAZY, mappedBy = "channel")
	@PrimaryKeyJoinColumn
	public ChannelTxt getTxt() {
		return this.txt;
	}

	public void setTxt(ChannelTxt txt) {
		this.txt = txt;
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
	@JoinColumn(name = "flow_id", nullable = true)
	public WorkFlow getFlow() {
		return this.flow;
	}

	public void setFlow(WorkFlow flow) {
		this.flow = flow;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "depart_id", nullable = false)
	public Depart getInputDepart() {
		return this.inputDepart;
	}

	public void setInputDepart(Depart inputDepart) {
		this.inputDepart = inputDepart;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id", nullable = true)
	public Channel getParent() {
		return this.parent;
	}

	public void setParent(Channel parent) {
		this.parent = parent;
	}

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "tq_chnl_tpl_selection", joinColumns = { @JoinColumn(name = "chnl_id") })
	public Set<ChnlTplSelection> getTpls() {
		return this.tpls;
	}

	public void setTpls(Set<ChnlTplSelection> tpls) {
		this.tpls = tpls;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = { javax.persistence.CascadeType.REMOVE }, mappedBy = "parent")
	@OrderBy("priority asc,id asc")
	public Set<Channel> getChild() {
		return this.child;
	}

	public void setChild(Set<Channel> child) {
		this.child = child;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tq_chnl_group_view", joinColumns = { @JoinColumn(name = "channel_id") }, inverseJoinColumns = { @JoinColumn(name = "group_id") })
	public Set<Group> getViewGroups() {
		return this.viewGroups;
	}

	public void setViewGroups(Set<Group> viewGroups) {
		this.viewGroups = viewGroups;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tq_chnl_group_contri", joinColumns = { @JoinColumn(name = "channel_id") }, inverseJoinColumns = { @JoinColumn(name = "group_id") })
	public Set<Group> getContriGroups() {
		return this.contriGroups;
	}

	public void setContriGroups(Set<Group> contriGroups) {
		this.contriGroups = contriGroups;
	}

	@ManyToMany(mappedBy = "channels", fetch = FetchType.LAZY)
	public Set<Depart> getDeparts() {
		return this.departs;
	}

	public void setDeparts(Set<Depart> departs) {
		this.departs = departs;
	}

	@ManyToMany(mappedBy = "channels", fetch = FetchType.LAZY)
	public Set<AdminCheck> getChecks() {
		return this.checks;
	}

	public void setChecks(Set<AdminCheck> checks) {
		this.checks = checks;
	}
}
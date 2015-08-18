package com.portal.doccenter.entity;

import com.javassf.basic.comparator.BeanInterface;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import org.apache.commons.lang.StringUtils;
/**
 * 
 * 模块实体类，采用hibernate注解方式
 * 
 */
@Entity
@Table(name = "tq_model")//映射表
public class Model implements Serializable, BeanInterface {
	private static final long serialVersionUID = 1L;
	
	public static final String[] DEF_NAMES = { "channelId", "title", "shortTitle", "titleColor", "subTitle", "tagStr", "description", "author", "origin", "style", "recommend", "showIndex", "redTape", "viewGroups", "tplContent", "atts", "releaseDate", "link", "commentControl",
			"updownControl", "txt", "picture", "pics" };

	public static final String[] DEF_LABELS = { "栏目", "标题", "短标题", "标题颜色", "副标题", "Tag标签", "摘要", "作者", "来源", "新闻类型", "属性", "显示到首页", "是否需签收", "访问权限", "内容模板", "附件", "发布时间", "外部链接", "评论控制", "顶踩控制", "内容", "缩略图", "组图" };

	public static final Integer[] DEF_DATA_TYPES = { Integer.valueOf(9), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(8), Integer.valueOf(8),
			Integer.valueOf(7), Integer.valueOf(1), Integer.valueOf(8), Integer.valueOf(1), Integer.valueOf(10), Integer.valueOf(6), Integer.valueOf(1), Integer.valueOf(7), Integer.valueOf(7), Integer.valueOf(3), Integer.valueOf(10), Integer.valueOf(10) };

	public static final Boolean[] DEF_REQUIREDS = { Boolean.valueOf(true), Boolean.valueOf(true), Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(false),
			Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(true), Boolean.valueOf(false), Boolean.valueOf(true), Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(false),
			Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(false) };
	private Integer id;
	private String name;//模块名称
	private String icon;//图片
	private String tplDoc;//内容页模板
	private String tplPrint;//打印页模板
	private String tplSearch;//搜索页模板
	private String tplAdvSearch;//高级搜索页模板
	private String tplComment;//评论页模板
	private Integer priority;//排序
	private Boolean disabled;//是否禁用
	private Set<ModelField> fields;//与 模块属性 一对多关系

	public void init() {
		if (getDisabled() == null)
			setDisabled(Boolean.valueOf(false));
	}

	@Transient
	public String getModelIcon() {
		if (!StringUtils.isBlank(getIcon())) {
			int a = getIcon().lastIndexOf("/") + 1;
			int b = getIcon().lastIndexOf(".");
			if (b > a) {
				return getIcon().substring(a, b);
			}
		}
		return null;
	}

	@Id
	@Column(name = "model_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_model", pkColumnValue = "tq_model", table = "tq_gen_table", pkColumnName = "tg_gen_name", valueColumnName = "tq_gen_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_model")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "model_name", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "model_icon", nullable = true, length = 30)
	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Column(name = "model_tpl_doc", nullable = false, length = 100)
	public String getTplDoc() {
		return this.tplDoc;
	}

	public void setTplDoc(String tplDoc) {
		this.tplDoc = tplDoc;
	}

	@Column(name = "model_tpl_print", nullable = true, length = 100)
	public String getTplPrint() {
		return this.tplPrint;
	}

	public void setTplPrint(String tplPrint) {
		this.tplPrint = tplPrint;
	}

	@Column(name = "model_tpl_search", nullable = true, length = 100)
	public String getTplSearch() {
		return this.tplSearch;
	}

	public void setTplSearch(String tplSearch) {
		this.tplSearch = tplSearch;
	}

	@Column(name = "model_tpl_advsearch", nullable = true, length = 100)
	public String getTplAdvSearch() {
		return this.tplAdvSearch;
	}

	public void setTplAdvSearch(String tplAdvSearch) {
		this.tplAdvSearch = tplAdvSearch;
	}

	@Column(name = "model_tpl_comment", nullable = true, length = 100)
	public String getTplComment() {
		return this.tplComment;
	}

	public void setTplComment(String tplComment) {
		this.tplComment = tplComment;
	}

	@Column(name = "priority", nullable = false, length = 10)
	public Integer getPriority() {
		return this.priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	@Column(name = "is_disabled", nullable = false, length = 1)
	public Boolean getDisabled() {
		return this.disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = { javax.persistence.CascadeType.REMOVE }, mappedBy = "model")
	public Set<ModelField> getFields() {
		return this.fields;
	}

	public void setFields(Set<ModelField> fields) {
		this.fields = fields;
	}
}

package com.portal.datacenter.docdata.entity;

import com.portal.sysmgr.entity.Site;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "tq_keyword")
public class Keyword implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String url;
	private Boolean bold;
	private Boolean underline;
	private Boolean enable;
	private Site site;

	public void init() {
		if (getEnable() == null) {
			setEnable(Boolean.valueOf(true));
		}
		if (getBold() == null) {
			setBold(Boolean.valueOf(true));
		}
		if (getUnderline() == null)
			setUnderline(Boolean.valueOf(true));
	}

	@Id
	@Column(name = "keyword_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_keyword", pkColumnValue = "tq_keyword", table = "tq_gen_table", pkColumnName = "tg_gen_name", valueColumnName = "tq_gen_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_keyword")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "url", nullable = false, length = 100)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "is_bold", nullable = false)
	public Boolean getBold() {
		return this.bold;
	}

	public void setBold(Boolean bold) {
		this.bold = bold;
	}

	@Column(name = "is_underline", nullable = false)
	public Boolean getUnderline() {
		return this.underline;
	}

	public void setUnderline(Boolean underline) {
		this.underline = underline;
	}

	@Column(name = "is_enable", nullable = false)
	public Boolean getEnable() {
		return this.enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "site_id", nullable = false)
	public Site getSite() {
		return this.site;
	}

	public void setSite(Site site) {
		this.site = site;
	}
}

package com.portal.datacenter.docdata.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "tq_program_download")
public class ProgramDownload implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer count;

	@Id
	@Column(name = "download_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_program_download", pkColumnValue = "tq_program_download", table = "tq_gen_table", pkColumnName = "tg_gen_name", valueColumnName = "tq_gen_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_program_download")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "count", nullable = false, length = 11)
	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}

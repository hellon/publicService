package com.jovision.domain;

import java.sql.Timestamp;

/**
 * TbAdvertisementVersion entity. @author MyEclipse Persistence Tools
 */

public class TbAdvertisementVersion implements java.io.Serializable {

	// Fields

	private Integer id;
	private String url;
	private String version;
	private Integer versionValue;
	private Timestamp publishTime;

	// Constructors

	/** default constructor */
	public TbAdvertisementVersion() {
	}

	/** full constructor */
	public TbAdvertisementVersion(String url, String version,
			Integer versionValue, Timestamp publishTime) {
		this.url = url;
		this.version = version;
		this.versionValue = versionValue;
		this.publishTime = publishTime;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Integer getVersionValue() {
		return this.versionValue;
	}

	public void setVersionValue(Integer versionValue) {
		this.versionValue = versionValue;
	}

	public Timestamp getPublishTime() {
		return this.publishTime;
	}

	public void setPublishTime(Timestamp publishTime) {
		this.publishTime = publishTime;
	}

}
package com.jovision.domain;

import java.sql.Timestamp;

/**
 * TbAppVersion entity. @author MyEclipse Persistence Tools
 */

public class TbAppVersion implements java.io.Serializable {

	// Fields

	private Integer id;
	private String version;
	private Integer versionValue;
	private String url;
	private Timestamp publishTime;
	private Integer appKey;
	private String updateLog;
	private Boolean forceUpdate;
    private String language;
	// Constructors

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/** default constructor */
	public TbAppVersion() {
	}

	/** minimal constructor */
	public TbAppVersion(String version, Integer versionValue, String url,
			Timestamp publishTime,String language) {
		this.version = version;
		this.versionValue = versionValue;
		this.url = url;
		this.publishTime = publishTime;
		this.language = language;
	}

	/** full constructor */
	public TbAppVersion(String version, Integer versionValue, String url,
			Timestamp publishTime, Integer appKey, String updateLog,
			Boolean forceUpdate) {
		this.version = version;
		this.versionValue = versionValue;
		this.url = url;
		this.publishTime = publishTime;
		this.appKey = appKey;
		this.updateLog = updateLog;
		this.forceUpdate = forceUpdate;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Timestamp getPublishTime() {
		return this.publishTime;
	}

	public void setPublishTime(Timestamp publishTime) {
		this.publishTime = publishTime;
	}

	public Integer getAppKey() {
		return this.appKey;
	}

	public void setAppKey(Integer appKey) {
		this.appKey = appKey;
	}

	public String getUpdateLog() {
		return this.updateLog;
	}

	public void setUpdateLog(String updateLog) {
		this.updateLog = updateLog;
	}

	public Boolean getForceUpdate() {
		return this.forceUpdate;
	}

	public void setForceUpdate(Boolean forceUpdate) {
		this.forceUpdate = forceUpdate;
	}

}
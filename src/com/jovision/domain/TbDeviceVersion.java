package com.jovision.domain;

import java.sql.Timestamp;

/**
 * TbDeviceVersion entity. @author MyEclipse Persistence Tools
 */

public class TbDeviceVersion implements java.io.Serializable {

	// Fields

	private Integer id;
	private String version;
	private Integer versionValue;
	private Timestamp publishTime;
	private String deviceGuid;
	private String updateLog;
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
	public TbDeviceVersion() {
	}

	/** full constructor */
	public TbDeviceVersion(String version, Integer versionValue,
			Timestamp publishTime, String deviceGuid, String updateLog,String language) {
		this.version = version;
		this.versionValue = versionValue;
		this.publishTime = publishTime;
		this.deviceGuid = deviceGuid;
		this.updateLog = updateLog;
		this.language = language;
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

	public Timestamp getPublishTime() {
		return this.publishTime;
	}

	public void setPublishTime(Timestamp publishTime) {
		this.publishTime = publishTime;
	}

	public String getDeviceGuid() {
		return this.deviceGuid;
	}

	public void setDeviceGuid(String deviceGuid) {
		this.deviceGuid = deviceGuid;
	}

	public String getUpdateLog() {
		return this.updateLog;
	}

	public void setUpdateLog(String updateLog) {
		this.updateLog = updateLog;
	}

}
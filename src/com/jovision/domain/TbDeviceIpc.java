package com.jovision.domain;

/**
 * TbDeviceIpc entity. @author MyEclipse Persistence Tools
 */

public class TbDeviceIpc implements java.io.Serializable {

	// Fields

	private Integer id;
	private String deviceGuid;
	private Integer deviceSubType;
	private String deviceVersion;
	private Integer alarmSwitch;
	private Integer cloudSwitch;

	// Constructors

	/** default constructor */
	public TbDeviceIpc() {
	}

	/** minimal constructor */
	public TbDeviceIpc(String deviceGuid) {
		this.deviceGuid = deviceGuid;
	}

	/** full constructor */
	public TbDeviceIpc(String deviceGuid, Integer deviceSubType,
			String deviceVersion, Integer alarmSwitch, Integer cloudSwitch) {
		this.deviceGuid = deviceGuid;
		this.deviceSubType = deviceSubType;
		this.deviceVersion = deviceVersion;
		this.alarmSwitch = alarmSwitch;
		this.cloudSwitch = cloudSwitch;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDeviceGuid() {
		return this.deviceGuid;
	}

	public void setDeviceGuid(String deviceGuid) {
		this.deviceGuid = deviceGuid;
	}

	public Integer getDeviceSubType() {
		return this.deviceSubType;
	}

	public void setDeviceSubType(Integer deviceSubType) {
		this.deviceSubType = deviceSubType;
	}

	public String getDeviceVersion() {
		return this.deviceVersion;
	}

	public void setDeviceVersion(String deviceVersion) {
		this.deviceVersion = deviceVersion;
	}

	public Integer getAlarmSwitch() {
		return this.alarmSwitch;
	}

	public void setAlarmSwitch(Integer alarmSwitch) {
		this.alarmSwitch = alarmSwitch;
	}

	public Integer getCloudSwitch() {
		return this.cloudSwitch;
	}

	public void setCloudSwitch(Integer cloudSwitch) {
		this.cloudSwitch = cloudSwitch;
	}

}
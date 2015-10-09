package com.jovision.domain;

import java.sql.Timestamp;

/**
 * TbDeviceBasic entity. @author MyEclipse Persistence Tools
 */

public class TbDeviceBasic implements java.io.Serializable {

	// Fields

	private Integer id;
	private String deviceGuid;
	private Integer deviceType;
	private String deviceName;
	private String deviceUsername;
	private String devicePassword;
	private String deviceIp;
	private Timestamp registerTime;
	private Timestamp onlineTime;
	private String deviceVersion;
	
	
	// Constructors

	/** default constructor */
	public TbDeviceBasic() {
	}

	/** minimal constructor */
	public TbDeviceBasic(String deviceGuid, Integer deviceType) {
		this.deviceGuid = deviceGuid;
		this.deviceType = deviceType;
	}

	/** full constructor */
	public TbDeviceBasic(String deviceGuid, Integer deviceType,
			String deviceName, String deviceUsername, String devicePassword,
			String deviceIp, Timestamp registerTime, Timestamp onlineTime) {
		this.deviceGuid = deviceGuid;
		this.deviceType = deviceType;
		this.deviceName = deviceName;
		this.deviceUsername = deviceUsername;
		this.devicePassword = devicePassword;
		this.deviceIp = deviceIp;
		this.registerTime = registerTime;
		this.onlineTime = onlineTime;
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

	public Integer getDeviceType() {
		return this.deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceName() {
		return this.deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceUsername() {
		return this.deviceUsername;
	}

	public void setDeviceUsername(String deviceUsername) {
		this.deviceUsername = deviceUsername;
	}

	public String getDevicePassword() {
		return this.devicePassword;
	}

	public void setDevicePassword(String devicePassword) {
		this.devicePassword = devicePassword;
	}

	public String getDeviceIp() {
		return this.deviceIp;
	}

	public void setDeviceIp(String deviceIp) {
		this.deviceIp = deviceIp;
	}

	public Timestamp getRegisterTime() {
		return this.registerTime;
	}

	public void setRegisterTime(Timestamp registerTime) {
		this.registerTime = registerTime;
	}

	public Timestamp getOnlineTime() {
		return this.onlineTime;
	}

	public void setOnlineTime(Timestamp onlineTime) {
		this.onlineTime = onlineTime;
	}

	public String getDeviceVersion() {
		return deviceVersion;
	}

	public void setDeviceVersion(String deviceVersion) {
		this.deviceVersion = deviceVersion;
	}
	
}
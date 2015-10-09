package com.jovision.domain;

import java.sql.Timestamp;

/**
 * TbDeviceAlarm entity. @author MyEclipse Persistence Tools
 */

public class TbDeviceAlarm implements java.io.Serializable {

	// Fields

	private Integer id;
	private String deviceGuid;
	private Integer alarmType;
	private Timestamp alarmTime;
	private String userId;

	// Constructors

	/** default constructor */
	public TbDeviceAlarm() {
	}

	/** minimal constructor */
	public TbDeviceAlarm(String deviceGuid) {
		this.deviceGuid = deviceGuid;
	}

	/** full constructor */
	public TbDeviceAlarm(String deviceGuid, Integer alarmType,
			Timestamp alarmTime, String userId) {
		this.deviceGuid = deviceGuid;
		this.alarmType = alarmType;
		this.alarmTime = alarmTime;
		this.userId = userId;
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

	public Integer getAlarmType() {
		return this.alarmType;
	}

	public void setAlarmType(Integer alarmType) {
		this.alarmType = alarmType;
	}

	public Timestamp getAlarmTime() {
		return this.alarmTime;
	}

	public void setAlarmTime(Timestamp alarmTime) {
		this.alarmTime = alarmTime;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
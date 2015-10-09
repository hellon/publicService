package com.jovision.domain;

import java.sql.Timestamp;

/**
 * TbUserDevice entity. @author MyEclipse Persistence Tools
 */

public class TbUserDevice implements java.io.Serializable {

	// Fields

	private Integer id;
	private String userid;
	private String deviceGuid;
	private Integer permission;
	private Timestamp addtime;
	private String nickname;

	// Constructors

	/** default constructor */
	public TbUserDevice() {
	}

	/** minimal constructor */
	public TbUserDevice(String userid, String deviceGuid, Integer permission) {
		this.userid = userid;
		this.deviceGuid = deviceGuid;
		this.permission = permission;
	}

	/** full constructor */
	public TbUserDevice(String userid, String deviceGuid, Integer permission,
			Timestamp addtime, String nickname) {
		this.userid = userid;
		this.deviceGuid = deviceGuid;
		this.permission = permission;
		this.addtime = addtime;
		this.nickname = nickname;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getDeviceGuid() {
		return this.deviceGuid;
	}

	public void setDeviceGuid(String deviceGuid) {
		this.deviceGuid = deviceGuid;
	}

	public Integer getPermission() {
		return this.permission;
	}

	public void setPermission(Integer permission) {
		this.permission = permission;
	}

	public Timestamp getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Timestamp addtime) {
		this.addtime = addtime;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

}
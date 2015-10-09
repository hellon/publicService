package com.jovision.domain;

import java.sql.Timestamp;

/**
 * 
 * @Title: TbUser.java 
 * @Package com.jovision.domain
 * @author Joker(张凯)
 * @Description: TODO() 
 * @date 2015-9-18 下午02:38:17
 */

public class TbUser implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 444241359049487199L;
	private Integer id;
	private String userid;
	private String mail;
	private String phone;
	private String password;
	private String customType;
	private Timestamp registerTime;
	private Timestamp lastLoginTime;
	private String nickName;
	private String headimg;

	// Constructors

	/** default constructor */
	public TbUser() {
	}

	/** minimal constructor */
	public TbUser(String userid) {
		this.userid = userid;
	}

	/** full constructor */
	public TbUser(String userid, String mail, String phone, String password,
			String customType, Timestamp registerTime, Timestamp lastLoginTime,
			String nickName, String headimg) {
		this.userid = userid;
		this.mail = mail;
		this.phone = phone;
		this.password = password;
		this.customType = customType;
		this.registerTime = registerTime;
		this.lastLoginTime = lastLoginTime;
		this.nickName = nickName;
		this.headimg = headimg;
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

	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCustomType() {
		return this.customType;
	}

	public void setCustomType(String customType) {
		this.customType = customType;
	}

	public Timestamp getRegisterTime() {
		return this.registerTime;
	}

	public void setRegisterTime(Timestamp registerTime) {
		this.registerTime = registerTime;
	}

	public Timestamp getLastLoginTime() {
		return this.lastLoginTime;
	}

	public void setLastLoginTime(Timestamp lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getHeadimg() {
		return this.headimg;
	}

	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}

}
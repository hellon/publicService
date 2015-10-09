package com.jovision.domain;

/**
 * IpTables entity. @author MyEclipse Persistence Tools
 */

public class IpTables implements java.io.Serializable {

	// Fields

	private Integer id;
	private String ip;
	private Integer mask;
	private String provider;
	private Integer value;

	// Constructors

	/** default constructor */
	public IpTables() {
	}

	/** full constructor */
	public IpTables(String ip, Integer mask, String provider, Integer value) {
		this.ip = ip;
		this.mask = mask;
		this.provider = provider;
		this.value = value;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getMask() {
		return this.mask;
	}

	public void setMask(Integer mask) {
		this.mask = mask;
	}

	public String getProvider() {
		return this.provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public Integer getValue() {
		return this.value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

}
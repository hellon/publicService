package com.jovision.domain;

/**
 * TbWeburl entity. @author MyEclipse Persistence Tools
 */

public class TbWeburl implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String url;
	private Integer type;
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
	public TbWeburl() {
	}

	/** full constructor */
	public TbWeburl(String name, String url, Integer type,String language) {
		this.name = name;
		this.url = url;
		this.type = type;
		this.language = language;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
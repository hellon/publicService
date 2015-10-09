/**
 * 
 */
package com.jovision.test;

import java.io.Serializable;

/**
 * @Title: Person.java 
 * @Package com.jovision.test
 * @author Joker(张凯)
 * @Description: TODO() 
 * @date 2015-9-10 下午08:58:17
 */

public class Person implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	 
	public Person(int id, String name) {
	this.id = id;
	this.name = name;
	}
	 
	public int getId() {
	return id;
	}
	 
	public String getName() {
	return name;
	}
	}

package com.wk.dz.entity;

import java.util.Date;

public class ActUser 
{
	private Integer id;
	private String name;
	private String phone;
	private String realName;
	private Integer sex;
	private Date tsp;
	
	public ActUser() 
	{
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Date getTsp() {
		return tsp;
	}

	public void setTsp(Date tsp) {
		this.tsp = tsp;
	}
	
}

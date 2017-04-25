package com.wk.dz.entity;

import java.util.Date;

public class ActContent 
{
	private Integer id;//主键
	private String group;
	private Integer sex;//性别
	private String info;//内容信息
	private Integer isAvaliable;//是否可用
	private Date tsp;//时间戳
	
	public ActContent() 
	{
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Integer getIsAvaliable() {
		return isAvaliable;
	}
	public void setIsAvaliable(Integer isAvaliable) {
		this.isAvaliable = isAvaliable;
	}
	public Date getTsp() {
		return tsp;
	}
	public void setTsp(Date tsp) {
		this.tsp = tsp;
	}
	
	
}

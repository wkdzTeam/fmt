package com.wk.dz.entity;

import java.util.Date;

public class ActRef 
{
	private Integer id;//主键
	private Integer contentId;//内容主键
	private Integer contentOptId;//选项id
	private Integer userId;//用户id
	private Date tsp;//时间戳
	
	public ActRef() 
	{
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getContentId() {
		return contentId;
	}
	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}
	public Integer getContentOptId() {
		return contentOptId;
	}
	public void setContentOptId(Integer contentOptId) {
		this.contentOptId = contentOptId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Date getTsp() {
		return tsp;
	}
	public void setTsp(Date tsp) {
		this.tsp = tsp;
	}
	
	
	
}

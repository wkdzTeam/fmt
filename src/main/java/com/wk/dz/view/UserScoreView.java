package com.wk.dz.view;

import java.math.BigDecimal;

public class UserScoreView 
{
	private int userId;
	private String name;
	private String phone;
	private BigDecimal score;
	public UserScoreView() 
	{
		super();
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
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
	public BigDecimal getScore() {
		return score;
	}
	public void setScore(BigDecimal score) {
		this.score = score;
	}
	
	
}

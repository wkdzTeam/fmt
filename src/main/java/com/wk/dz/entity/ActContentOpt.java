package com.wk.dz.entity;

import java.util.Date;

public class ActContentOpt 
{
	private Integer id;//主键
	private Integer contentId;//内容表主键
	private String optInfo;//选项的具体内容
	private Integer optType;//选项类型（答题者/出题者）
	private Integer selected;//是否选中改选项
	private Date tsp;//时间戳
	
	public ActContentOpt() 
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

	public String getOptInfo() {
		return optInfo;
	}

	public void setOptInfo(String optInfo) {
		this.optInfo = optInfo;
	}

	public Integer getOptType() {
		return optType;
	}

	public void setOptType(Integer optType) {
		this.optType = optType;
	}

	public Integer getSelected() {
		return selected;
	}

	public void setSelected(Integer selected) {
		this.selected = selected;
	}

	public Date getTsp() {
		return tsp;
	}

	public void setTsp(Date tsp) {
		this.tsp = tsp;
	}
	
}

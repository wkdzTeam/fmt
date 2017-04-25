package com.wk.dz.view;

import java.util.List;

import com.wk.dz.entity.ActContent;
import com.wk.dz.entity.ActContentOpt;

public class ContentSelectInfoView 
{
	private int contentId;//题目id
	private ActContent actContent;//题目
	
	private List<ActContentOpt> optList;//选项列表
	
	public ContentSelectInfoView() 
	{
		super();
		// TODO Auto-generated constructor stub
	}
	public ActContent getActContent() {
		return actContent;
	}
	public void setActContent(ActContent actContent) {
		this.actContent = actContent;
	}
	public List<ActContentOpt> getOptList() {
		return optList;
	}
	public void setOptList(List<ActContentOpt> optList) {
		this.optList = optList;
	}
	public int getContentId() {
		return contentId;
	}
	public void setContentId(int contentId) {
		this.contentId = contentId;
	}
	
	
	
}

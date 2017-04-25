package com.wk.dz.dao;

import java.util.List;

import com.wk.dz.entity.ActContent;

public interface ActContentMapper 
{
	ActContent getContentById(Integer id);
	int insertContent(ActContent actContent);
	List<ActContent> getContentByIdList(List<Integer> list);
}

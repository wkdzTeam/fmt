package com.wk.dz.dao;

import java.util.List;

import com.wk.dz.entity.ActRef;

public interface ActRefMapper 
{
	List<ActRef> getRefByEntity(ActRef entity);
	ActRef getActRefById(Integer id);
	void insertActRef(ActRef acrRef);
	
}

package com.wk.dz.dao;

import java.util.List;

import com.wk.dz.entity.ActContentOpt;

public interface ActContentOptMapper 
{
	List<ActContentOpt> getOptInfoByEntity(ActContentOpt params);
	List<ActContentOpt> getOptInfoByIdList(List<Integer> idList);
	ActContentOpt getOptInfoById(Integer id);
	int insertOptInfo(ActContentOpt entity);
}

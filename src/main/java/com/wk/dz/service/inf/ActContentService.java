package com.wk.dz.service.inf;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.wk.dz.view.ContentSelectInfoView;
import com.wk.dz.view.UserScoreView;

public interface ActContentService 
{
	String insertContent(JSONObject jsonObj);
	List<ContentSelectInfoView> getContent(JSONObject jsonObj);
	void insertAnswer(JSONObject jsonObj);
	List<UserScoreView> getUserScore(JSONObject jsonObj);
}

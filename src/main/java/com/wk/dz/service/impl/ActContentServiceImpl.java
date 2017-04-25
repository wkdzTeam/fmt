package com.wk.dz.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.prism.constant.entity.MSGException;
import com.prism.constant.utils.CompareUtil;
import com.wk.dz.dao.ActContentMapper;
import com.wk.dz.dao.ActContentOptMapper;
import com.wk.dz.dao.ActRefMapper;
import com.wk.dz.dao.UserMapper;
import com.wk.dz.entity.ActContent;
import com.wk.dz.entity.ActContentOpt;
import com.wk.dz.entity.ActRef;
import com.wk.dz.service.inf.ActContentService;
import com.wk.dz.utils.RandomUtil;
import com.wk.dz.view.ContentSelectInfoView;
import com.wk.dz.view.UserScoreView;

@Service
public class ActContentServiceImpl implements ActContentService 
{
	private Logger LOGGER = LoggerFactory.getLogger(ActContentServiceImpl.class);
	@Resource
	private UserMapper userDao;
	@Resource
	private ActContentMapper actContentDao;
	@Resource
	private ActContentOptMapper contentOptDao;
	@Resource
	private ActRefMapper actRefDao;
	
	
	/* 
	 *返回组别id
	 */
	public String insertContent(JSONObject object) 
	{
		Date now = new Date();
		int userId = object.getIntValue("userId");
		Boolean isReQuestion = object.getBoolean("isReQuestion");//重新出题，将生成新的组别
		String group = object.getString("group");//组别id
		ActContent actContent = JSON.parseObject(object.getJSONObject("content").toJSONString(), ActContent.class);
		List<ActContentOpt> optList = JSON.parseArray(object.getJSONObject("opt").toJSONString(), ActContentOpt.class);
		if(null == optList || optList.isEmpty())
		{
			throw new MSGException("请选择答案选项");
		}
		if(null == isReQuestion)
		{
			LOGGER.error("============isReQuestion is null");
			throw new MSGException("参数错误");
		}
		if(isReQuestion)
		{
			group = RandomUtil.generateUiqueCode();
		}
		actContent.setTsp(now);
		actContent.setGroup(group);
		actContentDao.insertContent(actContent);
		int contentId = actContent.getId();
		for(ActContentOpt item : optList)
		{
			item.setTsp(now);
			item.setContentId(contentId);
			contentOptDao.insertOptInfo(item);
			ActRef actRef = new ActRef();
			actRef.setContentId(contentId);
			actRef.setContentOptId(item.getId());
			actRef.setUserId(userId);
			actRef.setTsp(now);
			actRefDao.insertActRef(actRef);
		}
		return group;
		
		
	}


	public List<ContentSelectInfoView> getContent(JSONObject jsonObj) 
	{
		int qestionUserId = jsonObj.getIntValue("qestionUserId");//提问者用户id
		ActRef actRef = new ActRef();
		actRef.setUserId(qestionUserId);
		List<ActRef> refList = actRefDao.getRefByEntity(actRef);
		List<Integer> contentIdList = new ArrayList<Integer>();
		List<Integer> optIdList = new ArrayList<Integer>();
		for(ActRef item : refList)
		{
			int contentId = item.getContentId();
			int optId = item.getContentOptId();
			contentIdList.add(contentId);
			optIdList.add(optId);
		}
		List<ActContent> conentList = actContentDao.getContentByIdList(contentIdList);
		List<ActContentOpt> optList = contentOptDao.getOptInfoByIdList(optIdList);
		List<ContentSelectInfoView> result = new ArrayList<ContentSelectInfoView>();
		
		if(null != conentList && !conentList.isEmpty())
		{
			for(ActContent entity : conentList)
			{
				int contentId = entity.getId();
				ContentSelectInfoView contentSelectInfoView = new ContentSelectInfoView();
				List<ActContentOpt> contentOpt = new ArrayList<ActContentOpt>();
				contentSelectInfoView.setOptList(contentOpt);
				contentSelectInfoView.setContentId(contentId);
				contentSelectInfoView.setActContent(entity);
				for(ActContentOpt opt : optList)
				{
					if(contentId == opt.getContentId())
					{
						contentOpt.add(opt);
					}
				}
				result.add(contentSelectInfoView);
			}
		}
		
		return result;
	}


	/* 
	 * 
	 * 回答者，将选项重复插入和出题者基本信息一致
	 * 就是类型不同，前期设计问题，后续可优化
	 * 
	 */
	public void insertAnswer(JSONObject jsonObj) 
	{
		Integer contentId = jsonObj.getInteger("contentId");
		Integer optId = jsonObj.getInteger("optId");
		Integer answerUserId = jsonObj.getInteger("answerUserId");//回答用户id
		if(!CompareUtil.isBiggerThan(contentId))
		{
			throw new MSGException("题目不能为空奥");
		}
		if(!CompareUtil.isBiggerThan(optId))
		{
			throw new MSGException("您未进行回答奥");
		}
		if(!CompareUtil.isBiggerThan(answerUserId))
		{
			LOGGER.error("======answerUserId={}",answerUserId);
			throw new MSGException("参数错误");
		}
		
		Date now = new Date();
		ActContentOpt opt = contentOptDao.getOptInfoById(optId);
		opt.setOptType(0);//答题者
		opt.setSelected(1);//出题者
		opt.setTsp(now);
		contentOptDao.insertOptInfo(opt);//插入答题者选项内容的信息
		int newOptId = opt.getContentId();
		ActRef ref = new ActRef();
		ref.setContentId(contentId);
		ref.setContentOptId(newOptId);
		ref.setUserId(answerUserId);
		ref.setTsp(now);
	}

	/* (non-Javadoc)
	 * @see com.wk.dz.service.inf.ActContentService#getUserScore(com.alibaba.fastjson.JSONObject)
	 * 只查询最新的题目
	 */
	public List<UserScoreView> getUserScore(JSONObject jsonObj) 
	{
		Integer answerUserId = jsonObj.getInteger("answerUserId");//答题者id
		Integer userId = jsonObj.getInteger("userId");//出题id
		if(!CompareUtil.isBiggerThan(answerUserId) || !CompareUtil.isBiggerThan(userId))
		{
			throw new MSGException("不能是匿名用户奥");
		}
		
		return null;
	}
	
	
	
	
}

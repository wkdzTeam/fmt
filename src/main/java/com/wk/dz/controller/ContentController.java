package com.wk.dz.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.prism.constant.entity.MSGException;
import com.prism.constant.entity.ResultCode;
import com.prism.constant.utils.FrameUtil;
import com.wk.dz.entity.ActContent;
import com.wk.dz.entity.ActUser;
import com.wk.dz.service.inf.ActContentService;
import com.wk.dz.service.inf.UserService;

/**
 * @author RuiChar
 *
 */
@Controller
@RequestMapping("content")
public class ContentController
{
	private static final Logger LOGGER = LoggerFactory.getLogger(ContentController.class);
	@Resource
	private UserService userSevice;
	@Resource
	private ActContentService contentSevice;
	
	/**
	 * @param request
	 * @return
	 * 出题
	 */
	@RequestMapping(value="addContent", method = RequestMethod.POST, produces = {"application/json", "application/xml" })
	@ResponseBody
	public Map<String, Object> addContent(HttpServletRequest request)
	{
		try 
		{
			JSONObject object = JSON.parseObject(IOUtils.toString(request.getInputStream()));
			String group = contentSevice.insertContent(object);
			return FrameUtil.HttpSuccessResult(group);
		} 
		catch (MSGException msgException)
		{
			LOGGER.error("===========添加用户失败{}==========", msgException.getMessage());
			return FrameUtil.HttpErrorResult("", ResultCode.SYS_ERROR, msgException.getMessage());
		}
		catch (Exception e) 
		{
			LOGGER.error("===========系统异常{}==========", e);
			return FrameUtil.HttpSYSErrorResult();
		}
	}
	
	/**
	 * @param request
	 * @return
	 * 获取出题者的题目以及选项
	 */
	@RequestMapping(value="getContent", method = RequestMethod.POST, produces = {"application/json", "application/xml" })
	@ResponseBody
	public Map<String, Object> getContent(HttpServletRequest request)
	{
		try 
		{
			JSONObject object = JSON.parseObject(IOUtils.toString(request.getInputStream()));
			return FrameUtil.HttpSuccessResult(contentSevice.getContent(object));
		} 
		catch (MSGException msgException)
		{
			LOGGER.error("===========添加用户失败{}==========", msgException.getMessage());
			return FrameUtil.HttpErrorResult("", ResultCode.SYS_ERROR, msgException.getMessage());
		}
		catch (Exception e) 
		{
			LOGGER.error("===========系统异常{}==========", e);
			return FrameUtil.HttpSYSErrorResult();
		}
	}
	
	
	/**
	 * @param request
	 * @return
	 * 答题者进行答题
	 */
	@RequestMapping(value="answerContent", method = RequestMethod.POST, produces = {"application/json", "application/xml" })
	@ResponseBody
	public Map<String, Object> answerContent(HttpServletRequest request)
	{
		try 
		{
			JSONObject object = JSON.parseObject(IOUtils.toString(request.getInputStream()));
			contentSevice.insertAnswer(object);
			return FrameUtil.HttpSuccessResult("");
		} 
		catch (MSGException msgException)
		{
			LOGGER.error("===========添加用户失败{}==========", msgException.getMessage());
			return FrameUtil.HttpErrorResult("", ResultCode.SYS_ERROR, msgException.getMessage());
		}
		catch (Exception e) 
		{
			LOGGER.error("===========系统异常{}==========", e);
			return FrameUtil.HttpSYSErrorResult();
		}
	}
	
	/**
	 * @param request
	 * @return
	 * 答题者回答完毕获取评分
	 */
	@RequestMapping(value="getCheckScore", method = RequestMethod.POST, produces = {"application/json", "application/xml" })
	@ResponseBody
	public Map<String, Object> getCheckScore(HttpServletRequest request)
	{
		try 
		{
			JSONObject object = JSON.parseObject(IOUtils.toString(request.getInputStream()));
			contentSevice.insertAnswer(object);
			return FrameUtil.HttpSuccessResult("");
		} 
		catch (MSGException msgException)
		{
			LOGGER.error("===========添加用户失败{}==========", msgException.getMessage());
			return FrameUtil.HttpErrorResult("", ResultCode.SYS_ERROR, msgException.getMessage());
		}
		catch (Exception e) 
		{
			LOGGER.error("===========系统异常{}==========", e);
			return FrameUtil.HttpSYSErrorResult();
		}
	}
	
}

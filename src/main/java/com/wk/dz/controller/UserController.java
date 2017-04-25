package com.wk.dz.controller;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
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
import com.prism.constant.utils.CompareUtil;
import com.prism.constant.utils.FrameUtil;
import com.wk.dz.entity.ActUser;
import com.wk.dz.service.inf.UserService;


/**
 * @author RuiChar
 *
 */
@Controller
@RequestMapping("user")
public class UserController 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@Resource
	private UserService userSevice;
	
	@RequestMapping(value="addUser", method = RequestMethod.POST, produces = {"application/json", "application/xml" })
	@ResponseBody
	public Map<String, Object> addUser(HttpServletRequest request)
	{
		try 
		{
			JSONObject object = JSON.parseObject(IOUtils.toString(request.getInputStream()));
			ActUser actUser = JSONObject.parseObject(object.toJSONString(), ActUser.class);
			if(StringUtils.isEmpty(actUser.getName()) )
			{
				throw new MSGException("为了方便朋友找你，请填写用户名");
			}
			
			if(CompareUtil.isSamllThan(actUser.getSex()))
			{
				throw new MSGException("您的性别不能未知奥？");
			}
			if(null != userSevice.findUserByName(actUser.getName()))
			{
				throw new MSGException("用户名已经存在请更换");
			}
			actUser.setTsp(new Date());
			userSevice.addUser(actUser);
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
	
	@RequestMapping(value="isExistUser", method = RequestMethod.POST, produces = {"application/json", "application/xml" })
	@ResponseBody
	public Map<String, Object> isExistUser(HttpServletRequest request)
	{
		try 
		{
			JSONObject object = JSON.parseObject(IOUtils.toString(request.getInputStream()));
			String userName = object.getString("name");
			if(!StringUtils.isEmpty(userName) )
			{


				ActUser u = userSevice.findUserByName(userName);
				if(null != u)
				{
					FrameUtil.HttpSuccessResult(true);
				}
			}
			return FrameUtil.HttpSuccessResult(false);
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

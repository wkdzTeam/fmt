package com.wk.dz.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author RuiChar
 *
 */
public class HttpUtils 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);
	public final static String getIpAddress(HttpServletRequest request) 
	{  
        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址 
	   String ip = request.getHeader("X-Forwarded-For");  
		try
		{
			LOGGER.info("getIpAddress(HttpServletRequest) - X-Forwarded-For - String ip={}", ip);  
	  
	        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) 
	        {  
	            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) 
	            {  
	                ip = request.getHeader("Proxy-Client-IP");  
                    LOGGER.info("getIpAddress(HttpServletRequest) - Proxy-Client-IP - String ip={}", ip);  
	            }  
	            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) 
	            {  
	                ip = request.getHeader("WL-Proxy-Client-IP");  
                    LOGGER.info("getIpAddress(HttpServletRequest) - WL-Proxy-Client-IP - String ip={}",ip);  
	            }  
	            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) 
	            {  
	                ip = request.getHeader("HTTP_CLIENT_IP");  
                    LOGGER.info("getIpAddress(HttpServletRequest) - HTTP_CLIENT_IP - String ip={}",ip);  
	            }  
	            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) 
	            {  
	                ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
                    LOGGER.info("getIpAddress(HttpServletRequest) - HTTP_X_FORWARDED_FOR - String ip={}", ip);  
	            }  
	            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) 
	            {  
	                ip = request.getRemoteAddr();  
                    LOGGER.info("getIpAddress(HttpServletRequest) - getRemoteAddr - String ip={}" , ip);  
	            }  
	        } 
	        else if (ip.length() > 15) 
	        {  
	            String[] ips = ip.split(",");  
	            for (int index = 0; index < ips.length; index++) 
	            {  
	                String strIp = (String) ips[index];  
	                if (!("unknown".equalsIgnoreCase(strIp))) 
	                {  
	                    ip = strIp;  
	                    break;  
	                }  
	            }  
	        }  
		}
		catch(Exception e)
		{
			LOGGER.error("===============Exception e={}",e);
		}
     
        
        return ip;  
    } 
	
	
	public static JSONObject getJsonObj(HttpServletRequest request) throws IOException
	{
		FrameHttpServletRequest req = null;
		if( request instanceof FrameHttpServletRequest)
		{
			req = (FrameHttpServletRequest)request;
		}
		else
		{
			req = new FrameHttpServletRequest(request);
		}
		return JSON.parseObject(IOUtils.toString(req.getInputStream()));
	}
	
	
	
}

package com.wk.dz.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wk.dz.dao.UserMapper;
import com.wk.dz.entity.ActUser;
import com.wk.dz.service.inf.UserService;

@Service
public class UserServiceImpl implements UserService
{

	@Resource
	private UserMapper userDao;
	public void login() 
	{
		System.out.println("login success......");
	}

	public void addUser(ActUser user) 
	{
		userDao.insertUser(user);
	}

	public ActUser findUserByName(String name) 
	{
		return userDao.getUserByName(name);
	}
	
}

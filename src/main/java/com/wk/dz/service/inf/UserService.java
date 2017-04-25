package com.wk.dz.service.inf;

import com.wk.dz.entity.ActUser;

public interface UserService 
{
	void login();
	void addUser(ActUser user);
	ActUser findUserByName(String name);
}

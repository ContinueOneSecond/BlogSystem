package com.mywebproject.service;


import java.util.Map;

import com.mywebproject.model.Blog;
import com.mywebproject.model.BlogUser;


public interface BlogUserService {
	BlogUser findUser(BlogUser user);

	long findUsername(Map<String,Object> map);

	void addNewUser(BlogUser user);

	
}

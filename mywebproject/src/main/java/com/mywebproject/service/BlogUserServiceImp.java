package com.mywebproject.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mywebproject.dao.BolgUserDao;
import com.mywebproject.model.BlogUser;

@Service
public class BlogUserServiceImp implements BlogUserService {
	
	@Autowired
	private BolgUserDao bolguserdao;
	
	@Override
	public BlogUser findUser(BlogUser user) {		
		return bolguserdao.selectUser(user);
	}

	@Override
	public long findUsername(Map<String,Object> map) {
		
		return bolguserdao.selectUsername(map);
	}

	@Override
	public void addNewUser(BlogUser user) {
		bolguserdao.insterUser(user);
		
	}

}

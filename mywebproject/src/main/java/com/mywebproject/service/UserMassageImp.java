package com.mywebproject.service;

import java.nio.file.Files;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mywebproject.dao.UserMassageDao;

@Service
public class UserMassageImp implements UserMassageService{

	@Autowired
	UserMassageDao userMassageDao;
	
	@Override
	public void changeUserMassage(String user_portrait, int uid) {
		userMassageDao.updataUserMassage(user_portrait,uid);
	}
	
	@Override
	public String findUserPortrait(int uid) {
		// TODO Auto-generated method stub
		return userMassageDao.selectUserPortrait(uid);
	}

	@Override
	public void addUserMassage(int uid) {
		userMassageDao.insertUserPortrait(uid);		
	}

	
}

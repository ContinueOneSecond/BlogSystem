package com.mywebproject.service;


import java.nio.file.Files;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.mywebproject.dao.FilesMassageDao;
import com.mywebproject.model.UserFiles;

@Service
public class UploadFilesImp implements UploadFiles {
	
	@Autowired
	private FilesMassageDao filesDao;
	
	@Override
	public void addFile(UserFiles thisFiles) {
		
		filesDao.insterFiles(thisFiles);
	}

	@Override
	public List<UserFiles> findFiles(Integer uid) {
		return filesDao.selectFilse(uid);
	}
	
}

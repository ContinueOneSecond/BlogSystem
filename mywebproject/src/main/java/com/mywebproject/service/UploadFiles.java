package com.mywebproject.service;


import java.util.List;

import com.mywebproject.model.UserFiles;

public interface UploadFiles {
	
	void addFile(UserFiles thisFiles);
	List<UserFiles> findFiles(Integer uid);
}

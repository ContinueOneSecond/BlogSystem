package com.mywebproject.dao;

import java.nio.file.Files;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface UserMassageDao {

	
	@Update("update usermassage set user_portrait = #{user_portrait} where uid=#{uid}")
	void updataUserMassage(String user_portrait, int uid);
	
	@Select("select user_portrait from usermassage where uid=#{uid}")
	String selectUserPortrait(int uid);
	
	@Insert("Insert into usermassage(uid) values(#{uid})")
	void insertUserPortrait(int uid);
	
	
	
}

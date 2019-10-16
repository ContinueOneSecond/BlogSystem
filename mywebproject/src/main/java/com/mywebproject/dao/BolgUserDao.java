package com.mywebproject.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import com.mywebproject.model.BlogUser;
import com.mywebproject.service.BlogUserService;


@Mapper
public interface BolgUserDao {
	
	@Select("select * from user where username=#{username} and password = #{password}")
	@ResultType(value = BlogUser.class)
	BlogUser selectUser(BlogUser user);
	
	@Select("select count(*) from user where username=#{username}")
	@ResultType(value = long.class)
	long selectUsername(Map<String,Object> map);
	
	@Insert("insert into user(username,password) values(#{username},#{password})")
	void insterUser(BlogUser user);
}

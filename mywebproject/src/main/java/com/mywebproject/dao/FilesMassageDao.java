package com.mywebproject.dao;



import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import com.mywebproject.model.UserFiles;

@Mapper
public interface FilesMassageDao {
		
	@Insert("insert into file(file_url,file_describe,file_name,uid) values(#{file_url},#{file_describe},#{file_name},#{uid})")
	void insterFiles(UserFiles thisFiles);

	@Select("select * from file where uid = #{uid}")
	@ResultType(value = UserFiles.class)
	List<UserFiles> selectFilse(Integer uid);
}

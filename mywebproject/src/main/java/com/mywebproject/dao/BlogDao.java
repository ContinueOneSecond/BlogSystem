package com.mywebproject.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.mywebproject.model.Blog;

@Mapper
public interface BlogDao {

	@Insert("insert into blog(blog_title,blog_time,blog_content,blog_body,uid) values(#{blog_title},#{blog_time},#{blog_content},#{blog_body},#{uid})")
	void insertBlog(Blog blog);

	@Select("select * from blog where uid = #{uid}")
	@ResultType(value = Blog.class)
	List<Blog> selectBlog(int uid);

	@Delete("delete from blog where bid = #{bid}")
	void delBlog(Integer bid);

	@Update("update blog set blog_title = #{blog_title},blog_content = #{blog_content} where bid=#{bid}")
	void updateBlog(Blog blog);
	
	@Select("select * from blog where uid = #{uid} limit #{startIndex},#{pageSize} ")
	@ResultType(value = Blog.class)
	List<Blog> blogPage(Map<String, Object> pageMassage);
	
	@Select("select count(*) from blog where uid=#{uid}")
	@ResultType(value = Integer.class)
	Integer getBlogCount(Integer uid);
	
	@Select("select * from "
			+ "blog where uid = #{uid} and blog_title like concat('%${searchBody}%') "
			+ "or "
			+ "blog_content like concat('%${searchBody}%') "
			+ "limit #{startIndex},#{pageSize}")
	List<Blog> selctLickBlog(Map<String, Object> map);

	
}

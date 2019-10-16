package com.mywebproject.service;


import java.util.List;
import java.util.Map;

import com.mywebproject.model.Blog;


public interface BlogService {

	void sumbitBlog(Blog blog);

	List<Blog> seekBlog(int uid);

	void deleteblog(Integer bid);
	
	void changeBlog(Blog blog);

	List<Blog> pageBlog(Map<String, Object> pageMassage);

	Integer blogCount(Integer uid);

	List<Blog>  findBlog(Map<String,Object> map);

}

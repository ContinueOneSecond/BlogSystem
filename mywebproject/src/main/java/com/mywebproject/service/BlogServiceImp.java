package com.mywebproject.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mywebproject.dao.BlogDao;
import com.mywebproject.model.Blog;

@Service
public class BlogServiceImp implements BlogService {
	
	@Autowired
	BlogDao blogdao;
	
	@Override
	public void sumbitBlog(Blog blog) {
		blogdao.insertBlog(blog);
	}

	@Override
	public List<Blog> seekBlog(int uid) {
		return blogdao.selectBlog(uid);
	}

	@Override
	public void deleteblog(Integer bid) { 
		blogdao.delBlog(bid);
		
	}

	@Override
	public void changeBlog(Blog blog) {
		blogdao.updateBlog(blog);
		
	}

	@Override
	public List<Blog> pageBlog(Map<String, Object> pageMassage) {
		return blogdao.blogPage(pageMassage);
	}

	@Override
	public Integer blogCount(Integer uid) {
		return blogdao.getBlogCount(uid);
	}

	@Override
	public List<Blog> findBlog(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return blogdao.selctLickBlog(map);
	}

	

}

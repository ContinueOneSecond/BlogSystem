package com.mywebproject.model;



import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Blog {
	private Long bid;
	private String blog_title;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date blog_time;
	
	private String blog_content;
	private String blog_body;
	private int uid;
	
	
	
	public String getBlog_title() {
		return blog_title;
	}
	public void setBlog_title(String blog_title) {
		this.blog_title = blog_title;
	}
	public Date getBlog_time() { 
		return blog_time;
	}
	public void setBlog_time(Date blog_time) {
		this.blog_time = blog_time;
	}
	
	public String getBlog_content() {
		return blog_content;
	}
	public void setBlog_content(String blog_content) {
		this.blog_content = blog_content;
	}
	public String getBlog_body() {
		return blog_body;
	}
	public void setBlog_body(String blog_body) {
		this.blog_body = blog_body;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	
	public Long getBid() {
		return bid;
	}
	@Override
	public String toString() {
		return "Blog [bid=" + bid + ", blog_title=" + blog_title + ", blog_time=" + blog_time + ", blog_content="
				+ blog_content + ", blog_body=" + blog_body + ", uid=" + uid + "]";
	}
	
	


}

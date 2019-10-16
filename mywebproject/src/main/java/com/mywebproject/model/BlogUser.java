package com.mywebproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BlogUser {

	private int uid;
	
	
	private String username;
	
	private String password;

	public BlogUser(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "BlogUser [uid=" + uid + ", username=" + username + ", password=" + password + "]";
	}

}

package com.mywebproject.service;




public interface UserMassageService {
	public void changeUserMassage(String user_portrait, int uid);
	public String findUserPortrait(int uid);
	public void addUserMassage(int uid);
	 
}

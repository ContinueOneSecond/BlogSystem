package com.mywebproject.model;

public class UserFiles {
	private  int fid;
	private  String file_url;
	private  String file_describe;
	private  String file_name;
	private  int uid;
	
	
	public UserFiles() {
		super();
	}


	public int getFid() {
		return fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public String getFile_url() {
		return file_url;
	}
	public void setFile_url(String file_url) {
		this.file_url = file_url;
	}
	public String getFile_describe() {
		return file_describe;
	}
	public void setFile_describe(String file_describe) {
		this.file_describe = file_describe;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}

	@Override
	public String toString() {
		return "Files [file_url=" + file_url + ", file_describe=" + file_describe + ", file_name=" + file_name
				+ ", uid=" + uid + "]";
	}	

	
}

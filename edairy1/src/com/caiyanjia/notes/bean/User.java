package com.caiyanjia.notes.bean;


public class User {
	private String id;
	private String password;
	private String name;
	private String note_name;

	public String getNote_name() {
		return note_name;
	}

	public void setNote_name(String note_name) {
		this.note_name = note_name;
	}

	private Boolean blacklist;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Boolean getBlacklist() {
		return blacklist;
	}

	public void setBlacklist(Boolean blacklist) {
		this.blacklist = blacklist;
	}

	@Override
	public String toString() {
		return "user [id=" + id + ", password=" + password + ", name=" + name + ", noteName=" + note_name
				+ ", blacklist=" + blacklist + "]";
	}
	public User() {
		super();
	}

	
	
	
}

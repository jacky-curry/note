package com.caiyanjia.notes.bean;

import java.util.Date;


public class Note {
	private String label;
	private String content;
	private String user_id;
	private String parent_node;

	public String getParent_node() {
		return parent_node;
	}

	public void setParent_node(String parent_node) {
		this.parent_node = parent_node;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	private  String author;


	private int like;
	private Date date;
	private boolean permission;

	@Override
	public String toString() {
		return "note{" +
				"label='" + label + '\'' +
				", content='" + content + '\'' +
				", author='" + author + '\'' +
				", like=" + like +
				", date=" + date +
				", permission=" + permission +
				'}';
	}

	public Note() {

	}

	public String getLabel() {
		return label;
	}





	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getLike() {
		return like;
	}

	public void setLike(int like) {
		this.like = like;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isPermission() {
		return permission;
	}

	public void setPermission(boolean permission) {
		this.permission = permission;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	
}

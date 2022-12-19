package com.demo;

public class User {
	private String name;
	private int id;
	
	public User() {
		
	}
	
	public User(int id, String name) {
		super();
		this.name = name;
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

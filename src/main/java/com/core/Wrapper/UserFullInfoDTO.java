package com.example.Model;

import java.io.Serializable;

public class UserFullInfoDTO implements Serializable{
	
	private int id;
	private String role;
	private String username;
	private String job;
	
	public UserFullInfoDTO() {
		super();
	}

	public UserFullInfoDTO(int id, String role, String username, String job) {
		super();
		this.id = id;
		this.role = role;
		this.username = username;
		this.job = job;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	
}

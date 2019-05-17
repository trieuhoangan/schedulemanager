package com.core.Wrapper;

public class MultiCustomerWrapper {
	private String name;
	private String phoneNumber;
	private int number;
	private String day;
	private String session;
	private String address;
	public String getAddress(){
		return address;
	}
	public void setAddress(String add){
		this.address = add;
	}
	public String getSession() {
		return session;
	}
	public void setSession(String session) {
		this.session = session;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	
}

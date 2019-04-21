package com.core.Model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQuery(name="Form.findAll", query="SELECT f FROM Form f")
@Table(name="Form")
public class Form implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(name = "name")
	private String name;
	@Column(name = "phoneNumber")
	private String phoneNumber;
	@Column(name="status")
	private String status;
	@Column(name="result")
	private String result;
	@Column(name="code")
	private String code;
	@Column(name="day")
	private String day;
	@Column(name="session")
	private String session;
	@Column(name="stay")
	private boolean stay;
	@Column(name="begin")
	private String begin;
	@Column(name="end")
	private int end;
	@Column(name="dayID")
	private Long dayID;
	
	public Form() {
		
	}

	public Form(Long id, String name, String phoneNumber, String status, String result, String code, String day,
			String session, boolean stay, String begin, int end, Long dayID) {
		super();
		this.id = id;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.status = status;
		this.result = result;
		this.code = code;
		this.day = day;
		this.session = session;
		this.stay = stay;
		this.begin = begin;
		this.end = end;
		this.dayID = dayID;
	}
	public Form(Form form) {
		this.id = form.getId();
		this.name = form.getName();
		this.phoneNumber = form.getPhoneNumber();
		this.status = form.getStatus();
		this.result = form.getResult();
		this.code = form.getCode();
		this.day = form.getDay();
		this.session = form.getSession();
		this.stay = form.isStay();
		this.begin = form.getBegin();
		this.end = form.getEnd();
		this.dayID = form.getDayID();
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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

	public String getSession() {
		return session;
	}
	public void setSession(String session) {
		this.session = session;
	}
	public boolean isStay() {
		return stay;
	}
	public void setStay(boolean stay) {
		this.stay = stay;
	}
	public String getBegin() {
		return begin;
	}
	public void setBegin(String begin) {
		this.begin = begin;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public Long getDayID() {
		return dayID;
	}
	public void setDayID(Long dayID) {
		this.dayID = dayID;
	}
	@Override
	public String toString() {
		return name+" "+phoneNumber+" "+status;
	}
}

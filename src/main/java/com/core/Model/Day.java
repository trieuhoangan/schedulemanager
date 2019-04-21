package com.core.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQuery(name="Day.findAll", query="SELECT d FROM Day d")
@Table(name="Day")
public class Day implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(name="day")
	private String day;
	@Column(name="morningCase")
	private int morningCase;
	@Column(name="afternoonCase")
	private int afternoonCase;
	@Column(name="morningMaxCase")
	private int morningMaxCase;
	@Column(name="afternoonMaxCase")
	private int afternoonMaxCase;
	public Day() {
		this.day = new String();
		this.morningCase = 0;
		this.afternoonCase = 0;
		this.morningMaxCase = 15;
		this.afternoonMaxCase = 15;
				
	}
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public int getMorningCase() {
		return morningCase;
	}
	public void setMorningCase(int morningCase) {
		this.morningCase = morningCase;
	}
	public int getAfternoonCase() {
		return afternoonCase;
	}
	public void setAfternoonCase(int afternoonCase) {
		this.afternoonCase = afternoonCase;
	}
	public int getMorningMaxCase() {
		return morningMaxCase;
	}
	public void setMorningMaxCase(int morningMaxCase) {
		this.morningMaxCase = morningMaxCase;
	}
	public int getAfternoonMaxCase() {
		return afternoonMaxCase;
	}
	public void setAfternoonMaxCase(int afternoonMaxCase) {
		this.afternoonMaxCase = afternoonMaxCase;
	}
	public boolean isAvai() {
		if((this.afternoonCase<this.afternoonMaxCase)||(this.morningCase<this.morningMaxCase)) {
			return true;
		}
		 return false;
	}
	
	
}

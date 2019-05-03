package com.core.Wrapper;

import java.util.ArrayList;
import java.util.List;

import com.core.Model.Day;


public class DayListWrapper {
	private List<Day> list;
	private int pageNumber;
	private Long numberPage;
	public DayListWrapper() {
		list = new ArrayList<Day>();
		pageNumber=0;
		numberPage= (long)0;
	}
	public DayListWrapper(List<Day> list, int pageNumber, Long numberPage) {
		super();
		this.list = list;
		this.pageNumber = pageNumber;
		this.numberPage = numberPage;
	}
	public List<Day> getList() {
		return list;
	}
	public void setList(List<Day> list) {
		this.list = list;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public Long getNumberPage() {
		return numberPage;
	}
	public void setNumberPage(Long numberPage) {
		this.numberPage = numberPage;
	}
	
}

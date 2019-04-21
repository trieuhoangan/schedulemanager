package com.core.Wrapper;

import java.util.ArrayList;
import java.util.List;

import com.core.Model.Form;

public class FormListWrapper {
	private List<Form> list;
	private int pageNumber;
	private Long numberPage;
	public FormListWrapper(List<Form> formList, int n, Long i) {
		list = formList;
		pageNumber = n;
		numberPage = i;
	}
	public FormListWrapper() {
		list = new ArrayList<Form>();
		pageNumber = 0;
		numberPage = (long) 0;
	}
	public List<Form> getList() {
		return list;
	}
	public void setList(List<Form> list) {
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

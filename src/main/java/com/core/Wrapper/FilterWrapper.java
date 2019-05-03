package com.core.Wrapper;

import java.util.ArrayList;
import java.util.List;

public class FilterWrapper {
	private List<String> field;
	private List<String> value;
	private int pageNumber;
	private int numberForm;
	public List<String> getField() {
		return field;
	}
	public void setField(List<String> field) {
		this.field = field;
	}
	public List<String> getValue() {
		return value;
	}
	public void setValue(List<String> value) {
		this.value = value;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getNumberForm() {
		return numberForm;
	}
	public void setNumberForm(int numberForm) {
		this.numberForm = numberForm;
	}
	public FilterWrapper() {
		field = new ArrayList<String>();
		value = new ArrayList<String>();
		pageNumber = 0;
		numberForm =0;
	}
	public FilterWrapper(List<String>  field, List<String>  value, int pageNumber, int numberForm) {
		super();
		this.field = field;
		this.value = value;
		this.pageNumber = pageNumber;
		this.numberForm = numberForm;
	}
	
}

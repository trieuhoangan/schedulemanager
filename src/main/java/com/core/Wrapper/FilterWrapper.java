package com.core.Wrapper;

public class FilterWrapper {
	private String field;
	private String value;
	private int pageNumber;
	private int numberForm;
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
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
		field = "";
		value = "";
		pageNumber = 0;
		numberForm =0;
	}
	public FilterWrapper(String field, String value, int pageNumber, int numberForm) {
		super();
		this.field = field;
		this.value = value;
		this.pageNumber = pageNumber;
		this.numberForm = numberForm;
	}
	
}

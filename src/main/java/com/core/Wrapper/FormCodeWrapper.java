package com.core.Wrapper;

public class FormCodeWrapper {
	private String status;
	private String code;
	public FormCodeWrapper() {
		status = "";
		code = "";
	}
	public FormCodeWrapper(String sta,String cod) {
		status = sta;
		code = cod;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}

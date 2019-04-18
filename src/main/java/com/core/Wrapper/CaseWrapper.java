package com.core.Wrapper;

import com.core.Model.CaseDetail;
import com.core.Model.Form;

public class CaseWrapper {
	private Form form;
	private CaseDetail caseDetail;
	public Form getForm() {
		return form;
	}
	public void setForm(Form form) {
		this.form = form;
	}
	public CaseDetail getCaseDetail() {
		return caseDetail;
	}
	public void setCaseDetail(CaseDetail caseDetail) {
		this.caseDetail = caseDetail;
	}
	
}

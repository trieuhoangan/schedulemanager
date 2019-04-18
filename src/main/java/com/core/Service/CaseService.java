package com.core.Service;

import java.util.List;

import com.core.Model.CaseDetail;

public interface CaseService {
	public void save(CaseDetail caseDetail);
	public List<CaseDetail> findAll();
	public CaseDetail findById(Long id);
	public CaseDetail findByFormId(Long id);
	public void update(CaseDetail caseDetail);
}

package com.core.DAO;

import java.util.List;

import com.core.Model.CaseDetail;


public interface CaseDetailRepository {
	public void save(CaseDetail caseDetail);
	public CaseDetail findById(Long id);
	public List<CaseDetail> findAll();
	public CaseDetail findByFormId(Long id);
	public void update(CaseDetail caseDetail);
}

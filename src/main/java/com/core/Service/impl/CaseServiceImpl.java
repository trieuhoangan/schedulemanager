package com.core.Service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.DAO.CaseDetailRepository;
import com.core.Model.CaseDetail;
import com.core.Service.CaseService;

@Service(value="caseDetailService")
public class CaseServiceImpl implements CaseService{

	@Autowired(required = false)
	private CaseDetailRepository caseDetailRepository;
	@Override
	public void save(CaseDetail caseDetail) {
		// TODO Auto-generated method stub
		caseDetailRepository.save(caseDetail);
	}

	@Override
	public List<CaseDetail> findAll() {
		// TODO Auto-generated method stub
		return caseDetailRepository.findAll();
	}

	@Override
	public CaseDetail findById(Long id) {
		// TODO Auto-generated method stub
		return caseDetailRepository.findById(id);
	}

	@Override
	public CaseDetail findByFormId(Long id) {
		// TODO Auto-generated method stub
		return caseDetailRepository.findByFormId(id);
	}

	@Override
	public void update(CaseDetail caseDetail) {
		// TODO Auto-generated method stub
		caseDetailRepository.update(caseDetail);
	}

}

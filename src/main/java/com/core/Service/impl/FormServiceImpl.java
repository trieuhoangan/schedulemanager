package com.core.Service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.DAO.FormRepository;
import com.core.Model.Form;
import com.core.Service.FormService;

@Service(value="formService")
public class FormServiceImpl implements FormService {

	@Autowired(required = false)
	private FormRepository formRepository;
	@Override
	public void save(Form form) {
		// TODO Auto-generated method stub
		formRepository.save(form);
	}

	@Override
	public List<Form> findAll() {
		// TODO Auto-generated method stub
		return formRepository.findAll();
	}

	@Override
	public Form findById(Long id) {
		// TODO Auto-generated method stub
		return formRepository.findById(id);
	}

	@Override
	public List<Form> findLimit(int i, int n) {
		return formRepository.findLimit(i, n);
	}

	@Override
	public Long getTotalPage() {
		// TODO Auto-generated method stub
		return formRepository.getTotalPage();
	}

	@Override
	public void update(Form form) {
		// TODO Auto-generated method stub
		formRepository.update(form);
	}

	@Override
	public Form getByCode(String code) {
		// TODO Auto-generated method stub
		return formRepository.getByCode(code);
	}

}

package com.core.Service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.DAO.DayRepository;
import com.core.DAO.FormRepository;
import com.core.Model.Day;
import com.core.Model.Form;
import com.core.Service.FormService;

@Service(value="formService")
public class FormServiceImpl implements FormService {

	@Autowired(required = false)
	private FormRepository formRepository;
	@Autowired(required = false)
	private DayRepository dayRepository;
	@Override
	public void save(Form form) {
		formRepository.save(form);
	}

	@Override
	public List<Form> findAll() {
		return formRepository.findAll();
	}

	@Override
	public Form findById(Long id) {
		return formRepository.findById(id);
	}

	@Override
	public List<Form> findLimit(int i, int n) {
		return formRepository.findLimit(i, n);
	}

	@Override
	public Long getTotalPage() {
		return formRepository.getTotalPage();
	}

	@Override
	public void update(Form form) {
		formRepository.update(form);
	}

	@Override
	public void cancelForm(String code) {
		List<Form> list= formRepository.getByCode(code);
		for(int i =0;i<list.size();i++) {
			Day d = dayRepository.findById(list.get(i).getDayID());
			if(list.get(i).getSession().matches("morning")) {
				d.setMorningCase(d.getMorningCase()-1);
			}
			if(list.get(i).getSession().matches("afternoon")) {
				d.setAfternoonCase(d.getAfternoonCase()-1);
			}
			list.get(i).setStatus("canceled");
			dayRepository.update(d);
			formRepository.update(list.get(i));
		}
		
		
	}

	@Override
	public boolean isExistCode(String code) {
		List<Form> list= formRepository.getByCode(code);
		if(list.isEmpty()) return false;
		return true;
	}

	@Override
	public boolean regisForm(Form form) {
		Day d = dayRepository.findByDay(form.getDay());
		if(d==null) {
			d = new Day();
			d.setDay(form.getDay());
			dayRepository.save(d);
			d = dayRepository.findByDay(form.getDay());
		}
		if(!d.isAvai()) return false;
		if(form.getSession().matches("morning")) {
			d.setMorningCase(d.getMorningCase()+1);
		}
		if(form.getSession().matches("afternoon")) {
			d.setAfternoonCase(d.getAfternoonCase()+1);
		}
		form.setStatus("waiting");
		formRepository.save(form);
		dayRepository.update(d);
		return true;
	}

	@Override
	public List<Form> getFilter(String field, String value) {
		return formRepository.getFilterPage(field, value);
	}

}

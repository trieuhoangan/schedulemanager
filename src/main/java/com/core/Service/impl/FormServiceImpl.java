package com.core.Service.impl;

import java.util.ArrayList;
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
			Day d = dayRepository.findByDay(list.get(i).getDay());
			if(list.get(i).getSession().matches("morning")) {
				if(d.getMorningCase()>0)
					d.setMorningCase(d.getMorningCase()-1);
			}
			if(list.get(i).getSession().matches("afternoon")) {
				if(d.getAfternoonCase()>0)
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
	public List<Form> getFilter(List<String> field, List<String> value) {
		ArrayList<Form> result = new ArrayList<Form>();
		for (int i=0 ;i <field.size();i++) {
			List<Form> list = formRepository.getFilterPage(field.get(i), value.get(i));
			if( i==0) {
				result.addAll(list);
			}
			else {
				if(result.isEmpty()) continue;
				for(int j = 0 ; j <result.size();j++) {
					if(!checkContain(list, result.get(j))) {
						result.remove(result.get(j));
						j--;
					}
				}
			}
		}
		return result;
	}

	private boolean checkContain(List<Form> list, Form f) {
		for(int i = 0;i<list.size();i++) {
			if(list.get(i).getId()==f.getId()) return true;
		}
		return false;
	}

	@Override
	public List<Form> getSpecified(List<String> field, List<String> value) {
		ArrayList<Form> result = new ArrayList<Form>();
		for (int i=0 ;i <field.size();i++) {
			List<Form> list = formRepository.getSpecific(field.get(i), value.get(i));
			if( i==0) {
				result.addAll(list);
			}
			else {
				if(result.isEmpty()) continue;
				for(int j = 0 ; j <result.size();j++) {
					if(!checkContain(list, result.get(j))) {
						result.remove(result.get(j));
						j--;
					}
				}
			}
		}
		return result;
	}
}

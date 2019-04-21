package com.core.DAO;


import java.util.List;

//import java.util.List;



import com.core.Model.Form;


public interface FormRepository{
	public void save(Form form);
	public List<Form> findAll();
	public Form findById(Long id);
	public List<Form> findLimit(int i, int n);
	public Long getTotalPage();
	public void update(Form form);
	public List<Form> getByCode(String code);
	public List<Form> getFilterPage(String field,String value);
	public List<Form> getSpecific(String field,String value);
}

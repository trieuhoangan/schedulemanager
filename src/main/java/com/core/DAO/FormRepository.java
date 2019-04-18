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
	public Form getByCode(String code);
}

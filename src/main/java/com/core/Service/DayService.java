package com.core.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.core.Model.Day;
import com.core.Model.Form;

public interface DayService {
	public Day findById(Long id);
	public void save(Day day);
	public void update(Day day);
	public void delete(Day day);
	public List<Day> findAll();
	public boolean checkStayDayAvai(String begin, int end);
	public void regisStay(Form form,String begin, int end) throws ParseException;
	public void regisDay(Form form,String day);
	public List<Day> freeDay();
	public Day findByDay(String Day);
	public List<Day> findLimit(int i, int n);
	public long getTotalPage();
	public List<Day> getFilter(List<String> field,List<String> value);
}

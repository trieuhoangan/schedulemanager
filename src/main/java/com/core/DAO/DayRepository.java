package com.core.DAO;

import java.util.Date;
import java.util.List;

import com.core.Model.Day;
import com.core.Model.Form;

public interface DayRepository {
	public Day findById(Long id);
	public void save(Day day);
	public void update(Day day);
	public void delete(Day day);
	public List<Day> findAll();
	public Day findByDay(String day);
	public List<Day> findDayAfter(String day);
	public List<Day> findLimit(int i, int n);
	public long getTotalPage();
	public List<Day> getFilterPage(String field,String value);
	public List<Day> getFilterCasePage(String field,int value);
}

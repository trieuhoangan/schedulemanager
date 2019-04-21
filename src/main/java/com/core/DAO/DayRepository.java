package com.core.DAO;

import java.util.Date;
import java.util.List;

import com.core.Model.Day;

public interface DayRepository {
	public Day findById(Long id);
	public void save(Day day);
	public void update(Day day);
	public void delete(Day day);
	public List<Day> findAll();
	public Day findByDay(String day);
	public List<Day> findDayAfter(String day);
}

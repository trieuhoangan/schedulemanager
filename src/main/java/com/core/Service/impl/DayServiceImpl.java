package com.core.Service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.DAO.DayRepository;
import com.core.DAO.FormRepository;
import com.core.Model.Day;
import com.core.Model.Form;
import com.core.Service.DayService;

@Service(value="dayService")
public class DayServiceImpl implements DayService {
	 private static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	@Autowired
	private DayRepository dayRepository;
	@Autowired
	private FormRepository formRepository;
	@Override
	public Day findById(Long id) {
		// TODO Auto-generated method stub
		return dayRepository.findById(id);
	}

	@Override
	public void save(Day day) {
		// TODO Auto-generated method stub
		dayRepository.save(day);
	}

	@Override
	public void update(Day day) {
		// TODO Auto-generated method stub
		dayRepository.update(day);
	}

	@Override
	public void delete(Day day) {
		// TODO Auto-generated method stub
		dayRepository.delete(day);
	}

	@Override
	public List<Day> findAll() {
		return dayRepository.findAll();
	}

	@Override
	public boolean checkStayDayAvai(String begin, int end) {
		List<String> list = new ArrayList<String>();
		try {
			list = this.getDayList(begin, end);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		for(int i =0;i<list.size();i++) {
			Day d = dayRepository.findByDay(list.get(i));
			if(d==null) {
				d = new Day();
				d.setAfternoonCase(1);
				d.setMorningCase(1);
				d.setDay(list.get(i));
				dayRepository.save(d);
				
			}
			else {
				if((d.getAfternoonCase()==d.getAfternoonMaxCase())||(d.getMorningMaxCase()==d.getMorningCase())) {
					return false;
				}
			}
		}
		return true;
	}
	
	@Override
	public List<String> getDayList(String begin, int end) throws ParseException{
		Calendar c = Calendar.getInstance(); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		c.setTime(sdf.parse(begin));
		List<String> list = new ArrayList<String>();
		list.add(begin);
		for(int i=1;i<end;i++) {
			c.add(Calendar.DATE, 1);
			list.add(sdf.format(c.getTime()));
		}
		return list;
	}

	@Override
	public boolean regisStay(Form form,String begin, int end) throws ParseException {
		List<String> list = this.getDayList(begin, end);
		for(int i =0;i<list.size();i++) {
			Day d = dayRepository.findByDay(list.get(i));
			if(d==null) {
				d = new Day();
				d.setDay(list.get(i));
				dayRepository.save(d);
			}
			if(!d.isAvai()) return false;
		}
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for(int i =0;i<list.size();i++) {
			
			Day d = dayRepository.findByDay(list.get(i));
			if(!(d.getAfternoonCase()>d.getAfternoonMaxCase())&&!(d.getMorningCase()<d.getMorningMaxCase())) {
				return false;
			}
			d.setAfternoonCase(d.getAfternoonCase()+1);
			d.setMorningCase(d.getMorningCase()+1);
			Form Morning = new Form(form);
			Form Afternoon = new Form(form);
			
			Morning.setDay(list.get(i));
			Afternoon.setDay(list.get(i));
			
			Morning.setSession("morning");
			Afternoon.setSession("afternoon");
			
			Morning.setDayID(d.getId());
			Afternoon.setDayID(d.getId());
			
			
			Morning.setStatus("waiting");
			Afternoon.setStatus("waiting");
			
			Morning.setType("stay");
			Afternoon.setType("stay");
			
			dayRepository.update(d);
			formRepository.save(Morning);
			formRepository.save(Afternoon);	
			
		}
		return true;
	}

	@Override
	public void regisDay(Form form, String day) {
		
		
	}

	@Override
	public List<Day> freeDay() {
		Calendar cal = Calendar.getInstance();
		String currentDay = sdf.format(cal.getTime());
		List<Day> list = dayRepository.findDayAfter(currentDay);
		for(int i =0;i<list.size();i++) {
			if(!list.get(i).isAvai()) {
				list.remove(list.get(i));  
			}
		}
		return list;
	}

	@Override
	public Day findByDay(String Day) {
		return dayRepository.findByDay(Day);
	}

	@Override
	public List<Day> findLimit(int i, int n) {
		// TODO Auto-generated method stub
		return dayRepository.findLimit(i, n);
	}

	@Override
	public long getTotalPage() {
		
		return dayRepository.getTotalPage();
	}

	@Override
	public List<Day> getFilter(List<String> field, List<String> value) {
		ArrayList<Day> result = new ArrayList<Day>();
		if(field.contains("day")) {
			result.addAll(dayRepository.getFilterPage("day", value.get(field.indexOf("day"))));
		}
		for (int i=0 ;i <field.size();i++) {
			if(field.get(i).matches("day")) continue;
			int v = Integer.parseInt(value.get(i));
			List<Day> list = dayRepository.getFilterCasePage(field.get(i), v);
			if( i==0 && result.isEmpty()) {
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
	
	private boolean checkContain(List<Day> list,Day d) {
		for(int i =0;i<list.size();i++) {
			if(list.get(i).getDay().matches(d.getDay())) return true;
		}
		return false;
		
	}


}

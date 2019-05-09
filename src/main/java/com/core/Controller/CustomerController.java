package com.core.Controller;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.core.Model.Form;
import com.core.Service.DayService;
import com.core.Service.FormService;
import com.core.Wrapper.CustomerWrapper;
import com.core.Wrapper.DayWrapper;
import com.core.Wrapper.FilterWrapper;
import com.core.Wrapper.FormCodeWrapper;
import com.core.Wrapper.FormListWrapper;
import com.core.Wrapper.MultiCustomerWrapper;
import com.core.Model.Day;
@RestController
@CrossOrigin
public class CustomerController {
	

	@Autowired
	private FormService formService;
	@Autowired
	private DayService dayService;
	
	@PostMapping(value=("/send_appointment"))
	public FormCodeWrapper receiveForm(@RequestBody Form form) {
		String token = randomAlphaNumeric(8).toString();
		form.setCode(token); 
		FormCodeWrapper wrapper = new FormCodeWrapper("good",token);
		Day d = dayService.findByDay(form.getDay());
		if(d!=null) {
			if(form.getSession().matches("morning")&&(d.getMorningCase()==d.getMorningMaxCase())) {
				wrapper.setCode("");
				wrapper.setStatus("session is not available");
				return wrapper;
			}
			if(form.getSession().matches("afternoon")&&(d.getAfternoonCase()==d.getAfternoonMaxCase())) {
				wrapper.setCode("");
				wrapper.setStatus("session is not available");
				return wrapper;
			}
		}else {
			d = new Day();
			d.setDay(form.getDay());
			dayService.save(d);	
		}
		try {
			if(!formService.regisForm(form)) {
				wrapper.setCode("");
				wrapper.setStatus("bad");
			}
		}
		catch(Exception e) {
			wrapper.setCode("");
			wrapper.setStatus("bad");
		}
		wrapper.setStatus("good");
		return wrapper;
	}
	
	@PostMapping(value=("/send_stay_appointment"))
	public FormCodeWrapper receiveStayForm(@RequestBody Form form) {
		String token = randomAlphaNumeric(8).toString();
		form.setCode(token); 
		FormCodeWrapper wrapper = new FormCodeWrapper("good",token);
		try {
			
			if(!dayService.regisStay(form, form.getBegin(), form.getEnd())){
				List<String> dayList = dayService.getDayList(form.getBegin(), form.getEnd());
				String busyDay="";
				for (int i = 0 ;i < dayList.size();i++) {
					if(!dayService.findByDay(dayList.get(i)).isAvai()) {
						busyDay = dayList.get(i);
						break;
					}
				}
				wrapper.setStatus("cant");
				wrapper.setCode(busyDay);
			}
			
		}
		catch(Exception e) {
			wrapper.setCode("");
			wrapper.setStatus("bad");
		}
		wrapper.setStatus("good");
		return wrapper;
	}
	
	@PostMapping(value=("/cancel_appointment"))
	public FormCodeWrapper cancel(@RequestBody FormCodeWrapper code) {
		FormCodeWrapper wrapper = new FormCodeWrapper("good","");
		try {
			if(formService.isExistCode(code.getCode()))
				formService.cancelForm(code.getCode());

		}
		catch (Exception e) {
			// TODO: handle exception
			wrapper.setStatus("bad");
		}
		return wrapper;
	}

	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	public static String randomAlphaNumeric(int count) {
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}
	
	@GetMapping(value="/check_available_case")
	public List<Day> getFreeDays(){
		List<Day> list = dayService.freeDay();
		return list;
	}

	@PostMapping("/multi_booking")
	public FormCodeWrapper MultiBooking(@RequestBody MultiCustomerWrapper wrapper) {
		Day day = dayService.findByDay(wrapper.getDay());
		String token = randomAlphaNumeric(8).toString();
		FormCodeWrapper responseWrapper = new FormCodeWrapper("good","");
		if(day==null) {
			responseWrapper.setCode("unexist day");
			return responseWrapper;
		}
		int totalCase = day.getMorningMaxCase()-day.getMorningCase()+day.getAfternoonMaxCase()-day.getAfternoonCase();
		if(wrapper.getNumber()>totalCase) {
			responseWrapper.setStatus("overload");
			return responseWrapper;
		}
		else {
			try {
				for( int i = 0; i < wrapper.getNumber()-1;i++) {
					Form tmp = new Form();
					tmp.setName(wrapper.getName());
					tmp.setDay(wrapper.getDay());
					tmp.setPhoneNumber(wrapper.getPhoneNumber());
					tmp.setStatus("waiting");
					tmp.setCode(token);
					
					if(day.getMorningCase()<day.getMorningMaxCase()) {
						day.setMorningCase(day.getMorningCase()+1);
						dayService.update(day);
						tmp.setSession("morning");
						formService.save(tmp);
					}else {
						tmp.setSession("afternoon");
						formService.save(tmp);
						day.setAfternoonCase(day.getAfternoonCase()+1);
						dayService.update(day);
					}
				}
			}catch(Exception e) {
				responseWrapper.setStatus("error");
				return responseWrapper;
			}
			responseWrapper.setCode(token);
			return responseWrapper;
		}
	}
	
	@PostMapping("/get_day_detail")
	public Day getDay(@RequestBody DayWrapper day) {
		Day d = dayService.findByDay(day.getDay());
		return d;
	}
	
	@PostMapping(value="/customer_result")
	public FormListWrapper getCustomerFilter(@RequestBody FilterWrapper filter){
	
		
		List<Form> list =  formService.getSpecified(filter.getField(), filter.getValue());
		List<Form> result = new ArrayList<Form>();
		int begin = (filter.getPageNumber()-1)*filter.getNumberForm();
		int end = (filter.getPageNumber()-1)*filter.getNumberForm()+filter.getNumberForm();
		if(filter.getNumberForm() <list.size()) {
			if(filter.getNumberForm()>(list.size()-begin)) {
				result = list.subList((filter.getPageNumber()-1)*filter.getNumberForm(), list.size()-1);
			}else {
				result = list.subList(begin,end);
			}
		}
		else {
			result = list;
		}
		long numberPage =(long) list.size();
		FormListWrapper wrapper = new FormListWrapper(result,filter.getPageNumber(),numberPage);

		return wrapper;
	}
	
	@PostMapping("/old_customer")
	public Form serveOldOne(@RequestBody CustomerWrapper wrapper) {
		ArrayList<String> fields = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		fields.add("name");
		values.add(wrapper.getName());
		fields.add("phoneNumber");
		values.add(wrapper.getPhoneNumber());
		List<Form> list =  formService.getSpecified(fields,values);
		if(list.isEmpty())
		return new Form();
		else return list.get(0);
	}
}

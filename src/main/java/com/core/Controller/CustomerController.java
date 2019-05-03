package com.core.Controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.core.Model.Form;
import com.core.Service.DayService;
import com.core.Service.FormService;
import com.core.Wrapper.FormCodeWrapper;
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
		}
		try {
			if(!formService.regisForm(form)) {
				wrapper.setCode("");
				wrapper.setStatus("bad");
			}
			if(form.isStay()) {
				dayService.regisStay(form, form.getBegin(), form.getEnd());
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
}

package com.core.Controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.core.Model.Form;
import com.core.Service.FormService;
import com.core.Wrapper.FormCodeWrapper;

@RestController
@CrossOrigin
public class CustomerController {
	

	@Autowired
	private FormService formService;
	@PostMapping(value=("/send_appointment"))
	public FormCodeWrapper receiveForm(@RequestBody Form form) {
		String token = UUID.randomUUID().toString();
		form.setCode(token); 
		FormCodeWrapper wrapper = new FormCodeWrapper("good",token);
		try {
			formService.save(form);
		}
		catch(Exception e) {
			wrapper.setCode("");
			wrapper.setStatus("bad");
		}
		return wrapper;
	}
	
	@PostMapping(value=("/cancel_appointment"))
	public FormCodeWrapper cancel(@RequestBody FormCodeWrapper code) {
		Form form = formService.getByCode(code.getCode());
		form.setStatus("canceled");
		FormCodeWrapper wrapper = new FormCodeWrapper("good","");
		try {
			formService.update(form);
		}
		catch (Exception e) {
			// TODO: handle exception
			wrapper.setStatus("bad");
		}
		return wrapper;
	}
}

package com.core.Controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.core.Model.Day;
import com.core.Model.Form;
import com.core.Service.DayService;
import com.core.Service.FormService;
import com.core.Service.JwtService;
import com.core.Service.UserService;
import com.core.Wrapper.CustomerWrapper;
import com.core.Wrapper.DayWrapper;
import com.core.Wrapper.FilterWrapper;
import com.core.Wrapper.FormCodeWrapper;
import com.core.Wrapper.FormListWrapper;
import com.core.Wrapper.PageNumberWrapper;
import com.core.Model.User;
import com.core.Rest.JwtAuthenticationTokenFilter;
import com.core.Wrapper.LoginDTO;

@RestController
@CrossOrigin
public class AdminController {

	@SuppressWarnings("unused")
	@Autowired
	private FormService formService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private JwtAuthenticationTokenFilter jwtAuthen;
	
	@Autowired
	private DayService dayService;
	@PostMapping(value="/admin/check_all_appointment")
	@ResponseBody
	public FormListWrapper getFormList(@RequestBody PageNumberWrapper page){
		if(formService.findAll().isEmpty()) {
			FormListWrapper wrapper = new FormListWrapper(null, page.getPageNumber(),(long) 0 );
			return wrapper;
		}
		else {
		if(page.getNumberOfForm()<1) page.setNumberOfForm(10);
		List<Form> list = formService.findLimit(page.getPageNumber(), page.getNumberOfForm());
		Long numberPage;
		if(page.getNumberOfForm()==0) {
			numberPage=(long) 0;
		}
		else {
			numberPage = formService.getTotalPage()/page.getNumberOfForm();
			if(formService.getTotalPage()%page.getNumberOfForm() >0) numberPage++;
		}
		FormListWrapper wrapper = new FormListWrapper(list, page.getPageNumber(), numberPage);
			return wrapper;
		}
		
	}
	
	@PostMapping(value="/admin/check_one_appointment")
	public Form getOneCase(@RequestBody Long id) {
		Form form = formService.findById(id);
		return form;
	}
	
	@PostMapping(value="/admin/update_appointment")
	public String updateAppointment(@RequestBody Form newform) {
		Form tmp = formService.findById(newform.getId());
		if(tmp==null) {
			formService.save(newform);
			return "saved";
		}
		else {
			formService.update(newform);
			return "updated";
		}
		
	}
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public LoginDTO login(HttpServletRequest request, @RequestBody User user) {
		String result = "";
		User u = new User();
		HttpStatus httpStatus = null;
		try {
			if (userService.checkLogin(user)) {
				result = jwtService.generateTokenLogin(user.getUsername());
				u = userService.loadUserByUsername(user.getUsername());
				u.setRole("ROLE_ADMIN");
				httpStatus = HttpStatus.OK;
			} else {
				result = null;
				httpStatus = HttpStatus.BAD_REQUEST;
			}
		} catch (Exception ex) {
			result = "Server Error";
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return userService.TokenvsProfile(result, u);

	}
	
	@PostMapping(value="/admin/get_customer_history")
	public List<Form> getHistory(@RequestBody CustomerWrapper custom){
		List<Form> list = formService.findAll();
		ArrayList<Form> result = new ArrayList<Form>();
		for (int i =0 ; i < list.size();i++) {
			if(list.get(i).getName().matches(custom.getName())&&list.get(i).getPhoneNumber().matches(custom.getPhoneNumber())) {
				result.add(list.get(i));
			}
		}
		return result;
	}
	
	@PostMapping(value="/admin/form_filter")
	public FormListWrapper getFilter(@RequestBody FilterWrapper filter){
		FormListWrapper wrapper = new FormListWrapper();
		List<Form> list =  formService.getFilter(filter.getField(), filter.getValue());
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
		long numberPage =(long) list.size()/filter.getNumberForm();
		if(list.size()%filter.getNumberForm()>0) numberPage++;
		wrapper.setList(result);
		wrapper.setNumberPage(numberPage);
		wrapper.setPageNumber(filter.getPageNumber());
		return wrapper;
	}
	
	@PostMapping(value="/admin/config_schedule")
	public FormCodeWrapper changeSchedule(@RequestBody Day d) {
		FormCodeWrapper wrapper = new FormCodeWrapper();
		wrapper.setStatus("good");
		try {
			dayService.update(d);
		}
		catch(Exception e){
			wrapper.setStatus("bad");
		}
		
		return wrapper;
	}
	
	@PostMapping(value="/admin/check_schedule")
	public List<Form> checkSchedule(@RequestBody DayWrapper wrapper){
		return formService.getFilter("day", wrapper.getDay());
	}
	
	@RequestMapping(value="/logout")
	public void logOut(HttpServletRequest httpServletRequest) {
		jwtAuthen.destroy(httpServletRequest);
	}
}


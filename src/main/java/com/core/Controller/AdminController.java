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
import com.core.Wrapper.AppointmentIDWrapper;
import com.core.Wrapper.CustomerWrapper;
import com.core.Wrapper.DayDTO;
import com.core.Wrapper.DayListWrapper;
import com.core.Wrapper.DayWrapper;
import com.core.Wrapper.FilterWrapper;
import com.core.Wrapper.FormCodeWrapper;
import com.core.Wrapper.FormListWrapper;
import com.core.Wrapper.PageNumberWrapper;
import com.core.Wrapper.kappaWrapper;
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
	public FormListWrapper getFormList(@RequestBody PageNumberWrapper page){
		if(formService.findAll().isEmpty()) {
			FormListWrapper wrapper = new FormListWrapper(new ArrayList<Form>(), page.getPageNumber(),(long) 0 );
			return wrapper;
		}
		else {
			if(page.getNumberOfForm()<1) page.setNumberOfForm(10);
			List<Form> list = formService.findLimit(page.getPageNumber()-1, page.getNumberOfForm());
			Long numberPage;
			if(page.getNumberOfForm()==0) {
				numberPage=(long) 0;
			}
			else {
				numberPage = formService.getTotalPage();
			}
			ArrayList<Form> result  = new ArrayList<Form>();
			result.addAll(list);
			FormListWrapper wrapper = new FormListWrapper();
			wrapper.setList(result);
			wrapper.setNumberPage(numberPage);
			wrapper.setPageNumber(page.getPageNumber());
			
			return wrapper;
		}
		
	}
	
	@PostMapping(value="/admin/kappa")
	public kappaWrapper getKappa(@RequestBody FilterWrapper filter) {
		List<Form> list_all = formService.getFilter(filter.getField(), filter.getValue());
		filter.getField().add("session");
		filter.getValue().add("morning");
		List<Form> list_morn = formService.getFilter(filter.getField(), filter.getValue());
		filter.getField().remove(1);
		filter.getValue().remove(1);
		filter.getField().add("status");
		filter.getValue().add("canceled");
		List<Form> canceled = formService.getFilter(filter.getField(), filter.getValue());
		kappaWrapper wrapper = new kappaWrapper();
		wrapper.setTotalForm(list_all.size());
		wrapper.setMorningForm(list_morn.size());
		wrapper.setAfternoonForm(list_all.size()-list_morn.size());
		wrapper.setCanceledForm(canceled.size());
		return wrapper;
	}
	@PostMapping(value="/admin/check_one_appointment")
	@ResponseBody
	public Form getOneCase(@RequestBody AppointmentIDWrapper id) {
//		Long ID = Long.parseLong(id.getId());
		Form form = formService.findById(id.getId());
		return form;
	}
	@PostMapping(value="/admin/cancelAppointment")
	public String cancelAppointment(@RequestBody AppointmentIDWrapper id) {
		Form form = formService.findById(id.getId());
		String result ="success";
		try {
			formService.cancelForm(form.getCode());
		}
		catch(Exception e) {
			result ="failed";
		}
		return result;
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
		long numberPage =(long) list.size();
		FormListWrapper wrapper = new FormListWrapper(result,filter.getPageNumber(),numberPage);

		return wrapper;
	}
	
	
	
	@PostMapping(value="/admin/config_schedule")
	public FormCodeWrapper changeSchedule(@RequestBody DayDTO d) {
		FormCodeWrapper wrapper = new FormCodeWrapper();
		if(d.getDay().getAfternoonCase()>0||d.getDay().getMorningCase()>0) {
			wrapper.setStatus("not allowed to change");
			return wrapper;
		}
		Day day = dayService.findByDay(d.getDay().getDay());
		if(day==null) {
			day = new Day();
			day.setDay(d.getDay().getDay());
			day.setAfternoonMaxCase(d.getDay().getAfternoonMaxCase());
			day.setMorningMaxCase(d.getDay().getMorningMaxCase());
			dayService.save(day);
			wrapper.setCode("");
			wrapper.setStatus("good");
		}
		else {
			day.setMorningMaxCase(d.getDay().getMorningMaxCase());
			day.setAfternoonMaxCase(d.getDay().getAfternoonMaxCase());
			wrapper.setStatus("good");
			try {
				dayService.update(day);
			}
			catch(Exception e){
				wrapper.setStatus("bad");
			}
		}
		return wrapper;
	}
	
	@PostMapping(value="/admin/check_schedule")
	public DayListWrapper checkSchedule(@RequestBody PageNumberWrapper page){
		if(dayService.findAll().isEmpty()) {
			DayListWrapper wrapper = new DayListWrapper(new ArrayList<Day>(), page.getPageNumber(), (long)0 );
			return wrapper;
		}
		else {
			if(page.getNumberOfForm()<1) page.setNumberOfForm(10);
			List<Day> list = dayService.findLimit(page.getPageNumber()-1, page.getNumberOfForm());
			long numberPage;
			if(page.getNumberOfForm()==0) {
				numberPage= 0;
			}
			else {
				numberPage = dayService.getTotalPage();
			}
			ArrayList<Day> result  = new ArrayList<Day>();
			result.addAll(list);
			DayListWrapper wrapper = new DayListWrapper();
			wrapper.setList(result);
			wrapper.setNumberPage(numberPage);
			wrapper.setPageNumber(page.getPageNumber());
			
			return wrapper;
		}
	}
	
	@PostMapping(value="/admin/day_filter")
	public DayListWrapper getFilterDay(@RequestBody FilterWrapper filter){
	
		
		List<Day> list =  dayService.getFilter(filter.getField(), filter.getValue());
		List<Day> result = new ArrayList<Day>();
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
		DayListWrapper wrapper = new DayListWrapper(result,filter.getPageNumber(),numberPage);
//		wrapper.setList(result);
//		wrapper.setNumberPage(numberPage);
//		wrapper.setPageNumber(filter.getPageNumber());
		return wrapper;
	}
	
	@RequestMapping(value="/logout")
	public void logOut(HttpServletRequest httpServletRequest) {
		jwtAuthen.destroy(httpServletRequest);
		
	}
	
	@PostMapping("/admin/add_account")
	public FormCodeWrapper addAccount(@RequestBody User user) {
		FormCodeWrapper result = new FormCodeWrapper("good","");
		User newUser = new User();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(user.getPassword());
		newUser.setRole("ROLE_ADMIN");
		try {
			userService.save(newUser);
		}catch(Exception e) {
			result.setStatus("error");
			return result;
		}
		return result;
	}
	
	@PostMapping("/admin/get_day_detail")
	public Day getDay(@RequestBody DayWrapper day) {
		Day d = dayService.findByDay(day.getDay());
		if(d==null)
		return new Day();
		else return d;
	}
	
}


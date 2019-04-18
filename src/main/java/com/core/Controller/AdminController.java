package com.core.Controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.core.DAO.CaseDetailRepository;
import com.core.Model.CaseDetail;
import com.core.Model.Form;
import com.core.Service.CaseService;
import com.core.Service.FormService;
import com.core.Service.JwtService;
import com.core.Service.UserService;
import com.core.Wrapper.CaseWrapper;
import com.core.Wrapper.CustomerWrapper;
import com.core.Wrapper.FormListWrapper;
import com.core.Wrapper.PageNumberWrapper;
import com.core.Model.User;
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
	
	@PostMapping(value="/admin/check_all_appointment")
	@ResponseBody
	public FormListWrapper getFormList(@RequestBody PageNumberWrapper page){
		List<Form> list = formService.findLimit(page.getPageNumber(), page.getNumberOfForm());
		Long numberPage = formService.getTotalPage()/page.getNumberOfForm();
		if(formService.getTotalPage()%page.getNumberOfForm() >0) numberPage++;
		FormListWrapper wrapper = new FormListWrapper(list, page.getPageNumber(), numberPage);
		return wrapper;
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
}

package com.core.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@CrossOrigin
@Controller
public class HomeController {

	@GetMapping(value= {"/","/home"})
	public String mainPage() {
		return "home.html";
	}
}

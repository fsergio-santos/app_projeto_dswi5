package com.crm.web.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

	@RequestMapping(value="/home", method=RequestMethod.GET)
	public String home() {
		return "home";
	}
	
	@RequestMapping(value= {"/","/login"}, method=RequestMethod.GET)
	public String loginPage(@AuthenticationPrincipal User user) {
		if ( user!=null) {
			return "redirect:/home";
		}
		return "login";
	}
	
	
	
	
}

package com.kkomiding.davena.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
	
	
	@GetMapping("/login-view")
	public String login() {
		
		return "users/login";
	}
	
	@GetMapping("/before-apply-view")
	public String afterjoin() {
		
		return "users/beforeapply";
	}
	
	@GetMapping("/after-apply-view")
	public String afterlogin() {
		
		return "users/afterapply";
	}
	
	

}

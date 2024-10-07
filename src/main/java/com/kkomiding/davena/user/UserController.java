package com.kkomiding.davena.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

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
	
	@GetMapping("/join-view")
	public String join() {
		
		return "users/join";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("userId");
		session.removeAttribute("userName");
		
		return "redirect:/user/login-view";
	}
	
	

}

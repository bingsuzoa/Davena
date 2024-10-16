package com.kkomiding.davena.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	
	//공통
	@GetMapping("/user/login-view")
	public String login() {
		
		return "users/login";
	}
	
	@GetMapping("/user/join-view")
	public String join() {
		
		return "users/join";
	}
		
	@GetMapping("/user/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("userId");
		session.removeAttribute("userName");
		
		return "redirect:/user/login-view";
	}
	
	//팀원만 들어갈 수 있는 페이지
	@GetMapping("/member/before-apply-view")
	public String afterjoin() {
		
		return "users/beforeapply";
	}
	
	@GetMapping("/member/after-apply-view")
	public String afterlogin() {
		
		return "users/afterapply";
	}
	

}

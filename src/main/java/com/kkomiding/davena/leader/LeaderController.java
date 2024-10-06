package com.kkomiding.davena.leader;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/leader")
public class LeaderController {
	
	@GetMapping("/login-view")
	public String login() {
		
		return "leader/login";
	}
	
	@GetMapping("/make-view")
	public String makeRoom() {
		
		return "leader/make";
	}
	
	@GetMapping("/all-detail-view")
	public String alldetail() {
		
		return "leader/alldetail";
	}

}

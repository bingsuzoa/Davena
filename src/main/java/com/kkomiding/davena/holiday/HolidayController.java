package com.kkomiding.davena.holiday;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kkomiding.davena.holiday.service.HolidayService;
import com.kkomiding.davena.user.domain.User;
import com.kkomiding.davena.user.service.UserService;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/holiday")
public class HolidayController {
	
	private HolidayService holidayService;
	private UserService userService;
	
	public HolidayController(HolidayService holidayService
							,UserService userService) {
		this.holidayService = holidayService;
		this.userService = userService;
	}
		
	@GetMapping("/before-apply-view")
	public String beforeapply(HttpSession session, Model model) {
		
		int userId = (Integer)session.getAttribute("userId");
		User user = userService.getUser(userId);
		
		model.addAttribute("user",user);
		
		return "holiday/beforeapply";
	}
	
	@GetMapping("/after-apply-view")
	public String afterapply() {
		
		return "holiday/afterapply";
	}

	@GetMapping("/detail-view")
	public String detailview(HttpSession session
							,Model model) {
		
		int userId = (Integer)session.getAttribute("userId");
		long count = holidayService.selectHolidayCount(userId);
		User user = userService.getUser(userId);
		
		model.addAttribute("user",user);	
		model.addAttribute("count", count);
		
		return "holiday/detailview";
	}
	
	@GetMapping("/member-detail-view")
	public String memberDetailView(HttpSession session, Model model) {
		
		int userId = (Integer)session.getAttribute("userId");
		User user = userService.getUser(userId);
		
		model.addAttribute("user",user);
		
		
		return "leader/memberdetail";
	}
	
	@GetMapping("/calendar-view")
	public String calendarView(HttpSession session, Model model) {
		
		int userId = (Integer)session.getAttribute("userId");
		User user = userService.getUser(userId);
		
		return "leader/calendarview";
	}
}

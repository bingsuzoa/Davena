package com.kkomiding.davena.holiday;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kkomiding.davena.holiday.service.HolidayService;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/holiday")
public class HolidayController {
	
	private HolidayService holidayService;
	
	public HolidayController(HolidayService holidayService) {
		this.holidayService = holidayService;
	}
		
	@GetMapping("/before-apply-view")
	public String beforeapply() {
		
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
		
		model.addAttribute("count", count);
		
		return "holiday/detailview";
	}
	
	@GetMapping("/member-detail-view")
	public String memberDetailView() {
		
		return "leader/memberdetail";
	}
	
	@GetMapping("/calendar-view")
	public String calendarView() {
		
		return "leader/calendarview";
	}
}

package com.kkomiding.davena.holiday;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/holiday")
public class HolidayController {
	
	@GetMapping("/before-apply-view")
	public String beforeapply() {
		
		return "holiday/beforeapply";
	}
	
	@GetMapping("/after-apply-view")
	public String afterapply() {
		
		return "holiday/afterapply";
	}

	@GetMapping("/detail-view")
	public String detailview() {
		
		return "holiday/detailview";
	}
}

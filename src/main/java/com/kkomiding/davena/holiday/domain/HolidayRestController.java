package com.kkomiding.davena.holiday.domain;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kkomiding.davena.holiday.service.HolidayService;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/holiday")
@RestController
public class HolidayRestController {
	
	private HolidayService holidayService;
	
	public HolidayRestController(HolidayService holidayService) {
		this.holidayService = holidayService;
	}

		
	@PostMapping("/apply")
	public Map<String,String> addRequest(
			 @RequestParam("startDay") @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm") LocalDateTime startDay
			,@RequestParam("endDay") @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm") LocalDateTime endDay
			,@RequestParam("type") String type
			,@RequestParam("comment") String comment
			,HttpSession session){
		
		String loginId = (String)session.getAttribute("userId");
		
		Holiday holiday = holidayService.insertRequest(startDay, endDay, type, comment, loginId);
		
		Map<String, String> resultMap = new HashMap<>();
		
		if(holiday != null) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		
		return resultMap;
	}

}

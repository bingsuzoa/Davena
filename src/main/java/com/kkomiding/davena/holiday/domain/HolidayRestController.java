package com.kkomiding.davena.holiday.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kkomiding.davena.holiday.dto.PersonalSchedule;
import com.kkomiding.davena.holiday.service.HolidayService;
import com.kkomiding.davena.user.service.UserService;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/holiday")
@RestController
public class HolidayRestController {
	
	private HolidayService holidayService;
	private UserService userService;
	
	public HolidayRestController(HolidayService holidayService
								,UserService userService) {
		this.holidayService = holidayService;
		this.userService = userService;
	}

		
	@PostMapping("/apply")
	public Map<String,String> addRequest(
			 @RequestParam("startDay") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime startDay
			,@RequestParam("endDay") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime endDay
			,@RequestParam("type") String type
			,@RequestParam("comment") String comment
			,HttpSession session){
		
		int userId = (Integer)session.getAttribute("userId");
		
		Holiday holiday = holidayService.insertRequest(startDay, endDay, type, comment, userId);
		
		Map<String, String> resultMap = new HashMap<>();
		
		if(holiday != null) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		
		return resultMap;
	}
	

	@PostMapping("/detail")
	public List<PersonalSchedule> getRequest(HttpSession session) {
		
		int userId = (Integer)session.getAttribute("userId");
		
		List<PersonalSchedule> personalScheduleList = holidayService.getScheduleView(userId);
				
		return personalScheduleList;	
	}
	
	@DeleteMapping("/delete")
	public Map<String, String> deleteById(@RequestParam("holidayId") int holidayId
										,HttpSession session){
		
		int userId = (Integer)session.getAttribute("userId");

		Map<String, String> resultMap = new HashMap<>();
		
		if(holidayService.deleteRequest(holidayId, userId)) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		return resultMap;
		
	}
}

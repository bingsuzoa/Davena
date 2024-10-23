package com.kkomiding.davena.holiday;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kkomiding.davena.holiday.domain.Holiday;
import com.kkomiding.davena.holiday.service.HolidayService;
import com.kkomiding.davena.room.repository.RoomRepository;
import com.kkomiding.davena.user.domain.User;
import com.kkomiding.davena.user.service.UserService;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/holiday")
public class HolidayController {
	
	private HolidayService holidayService;
	private UserService userService;
	private RoomRepository roomRepository;
	
	public HolidayController(HolidayService holidayService
							,UserService userService
							,RoomRepository roomRepository) {
		this.holidayService = holidayService;
		this.userService = userService;
		this.roomRepository = roomRepository;
	}
		
	@GetMapping("/before-apply-view")
	public String beforeapply(HttpSession session, Model model) {
		
		int userId = (Integer)session.getAttribute("userId");
		User user = userService.getUser(userId);
		
		model.addAttribute("user",user);
		
		return "holiday/beforeapply";
	}
	
	@GetMapping("/after-apply-view")
	public String afterapply(HttpSession session, Model model) {
		
		int userId = (Integer)session.getAttribute("userId");
		User user = userService.getUser(userId);
		
		model.addAttribute("user",user);
		
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
		
		model.addAttribute("user",user);
		
		int count = roomRepository.countById(user.getRoomId());
		
		//월의 마지막날짜 구하기
		LocalDate now = LocalDate.now();
		LocalDate lastDate = now.withDayOfMonth(now.lengthOfMonth());
		int lastDay = lastDate.getDayOfMonth();
		
				
		Map<String, Object> holidayMap = new HashMap<>();
		List<String> nameList = new ArrayList<>();
		//외상센터의 이번달 휴가목록을 가져오세요.(roomId, 이번달)
		List<Holiday> holidayList = holidayService.getHolidayListByUserId(userId);
		for(Holiday holiday : holidayList) {
			String name = userService.getUser(holiday.getUserId()).getName();
			holidayMap.put("name", name);
			nameList.add(name);
			
			LocalDateTime start = holiday.getStartDay();
			int startDay = start.getDayOfMonth();
			holidayMap.put("startDay", startDay);
			
			LocalDateTime end = holiday.getEndDay();
			int endDay = end.getDayOfMonth();
			holidayMap.put("endDay", endDay);
			
		}
		//외상센터 휴가목록 map 
		model.addAttribute("holidayMap", holidayMap);
		model.addAttribute("nameList", nameList);
		//방인원 수 전달 
		model.addAttribute("count", count);
		//월의 마지막날짜 전달
		model.addAttribute("lastDay", lastDay);
		
		return "leader/calendarview";
	}
}

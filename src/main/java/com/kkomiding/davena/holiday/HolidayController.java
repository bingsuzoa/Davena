package com.kkomiding.davena.holiday;


import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kkomiding.davena.holiday.dto.ScheduleTable;
import com.kkomiding.davena.holiday.service.HolidayService;
import com.kkomiding.davena.room.repository.RoomRepository;
import com.kkomiding.davena.user.domain.User;
import com.kkomiding.davena.user.service.UserService;
import com.kkomiding.davena.work.domain.Work;
import com.kkomiding.davena.work.service.WorkService;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/holiday")
public class HolidayController {
	
	private HolidayService holidayService;
	private UserService userService;
	private RoomRepository roomRepository;
	private WorkService workService;
	
	public HolidayController(HolidayService holidayService
							,UserService userService
							,RoomRepository roomRepository
							,WorkService workService) {
		this.holidayService = holidayService;
		this.userService = userService;
		this.roomRepository = roomRepository;
		this.workService = workService;
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
		
		//방인원 수 전달 
		int count = roomRepository.countById(user.getRoomId());
		model.addAttribute("count", count);
		
		//월의 마지막날짜 구하기
		LocalDate now = LocalDate.now();
		LocalDate lastDate = now.withDayOfMonth(now.lengthOfMonth());
		int lastDay = lastDate.getDayOfMonth();
		model.addAttribute("lastDay", lastDay);
		
		//table생성에 필요한 정보 전달
		List<ScheduleTable> scheduleTableList = holidayService.getHolidayListByUserId(userId);
		model.addAttribute("scheduleTableList",scheduleTableList);
		
		List<Work> workList = workService.getWorkList();
		model.addAttribute("workList", workList);
		
		return "leader/calendarview";
	}
}

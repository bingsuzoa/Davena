package com.kkomiding.davena.work;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kkomiding.davena.holiday.service.HolidayService;
import com.kkomiding.davena.user.domain.User;
import com.kkomiding.davena.user.service.UserService;
import com.kkomiding.davena.work.dto.ScheduleTable;
import com.kkomiding.davena.work.service.WorkService;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/work")
public class WorkController {
	
	private UserService userService;
	private HolidayService holidayService;
	private WorkService workService;
	
	public WorkController(UserService userService
						 ,HolidayService holidayService
						 ,WorkService workService) {
		this.userService = userService;
		this.holidayService = holidayService;
		this.workService = workService;
		
	}
	
	
	@GetMapping("/calendar-view")
	public String calendarView(HttpSession session, Model model) {
		
		//header영역 프로필에 쓸내용
		int userId = (Integer)session.getAttribute("userId");
		User user = userService.getUser(userId);
		model.addAttribute("user", user);
		
		//월의 마지막날짜 구하기
		LocalDate now = LocalDate.now();
		LocalDate lastDate = now.withDayOfMonth(now.lengthOfMonth());
		int lastDay = lastDate.getDayOfMonth();
		model.addAttribute("lastDay", lastDay);
	
			
		//worktableList 구하기
		List<ScheduleTable> scheduleTableList = workService.setWorkSchedule(userId);
		model.addAttribute("scheduleTableList", scheduleTableList);
		
		return "leader/calendarview";
	}

}

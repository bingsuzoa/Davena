package com.kkomiding.davena.holiday;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kkomiding.davena.holiday.domain.Holiday;
import com.kkomiding.davena.holiday.dto.PersonalSchedule;
import com.kkomiding.davena.holiday.service.HolidayService;
import com.kkomiding.davena.room.service.RoomService;
import com.kkomiding.davena.work.domain.Work;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/holiday")
@RestController
public class HolidayRestController {
	
	private HolidayService holidayService;
	
	public HolidayRestController(HolidayService holidayService) {
		this.holidayService = holidayService;
	}

		
	@PostMapping("/apply")
	public Map<String, Object> addRequest(
			 @RequestParam("startDay") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime startDay
			,@RequestParam("endDay") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime endDay
			,@RequestParam("type") String type
			,@RequestParam("comment") String comment
			,HttpSession session){
		
		int userId = (Integer)session.getAttribute("userId");
		
		Holiday holiday = holidayService.insertRequest(startDay, endDay, type, comment, userId);
		
		Map<String, Object> resultMap = new HashMap<>();
		
		if(holiday != null) {
			resultMap.put("success", holiday);
		} else {
			resultMap.put("fail", -1);
		}
		
		return resultMap;
	}
	
	//내 휴가 신청 현황 조회하기
	@PostMapping("/detail")
	public List<PersonalSchedule> getRequest(@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime startDate
											,@RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime endDate
											,HttpSession session) {
		
		int userId = (Integer)session.getAttribute("userId");
		
		List<PersonalSchedule> personalScheduleList = holidayService.getScheduleView(startDate, endDate, userId);
				
		return personalScheduleList;	
	}
	
	//방에 있는 사람들의 휴가 신청 현황 조회하기
	@PostMapping("/detail/all")
	public List<PersonalSchedule> getDetailAll(@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime startDate
									 ,@RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime endDate
									 ,HttpSession session) {
		
		int userId = (Integer)session.getAttribute("userId");
		
		return holidayService.getAllHolidayList(startDate, endDate, userId);
		
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

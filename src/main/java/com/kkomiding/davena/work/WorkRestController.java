package com.kkomiding.davena.work;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kkomiding.davena.work.domain.Work;
import com.kkomiding.davena.work.service.WorkService;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/work")
@RestController
public class WorkRestController {
	
	private WorkService workService;
	
	
	public WorkRestController(WorkService workService) {
		this.workService = workService;
	}

	
	@PostMapping("/insert/id")
	public Map<String, String> insertUserId(@RequestParam("userId") int userId){
		
		Work work = workService.insertUserId(userId);
		
		
		Map<String, String> resultMap = new HashMap<>();
		
		if(work != null) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		
		return resultMap;
	}
	
	
	@PostMapping("/insert/holiday")
	public Map<String, String> insertHoliday(@RequestParam("type") String type
										   ,@RequestParam("startDay") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime startDay
										   ,@RequestParam("endDay")  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime endDay
										   ,@RequestParam("userId") int userId){
		
		Work work = workService.getWork(type, startDay, endDay, userId);
		
		Map<String, String> resultMap = new HashMap<>();
		
		if(work != null) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		
		return resultMap;
		
	}
	
	@PostMapping("/reset")
	public Map<String, String> deleteWorkTable(HttpSession session){
		
		int userId = (Integer)session.getAttribute("userId");
		List<Work> workList = workService.resetWorkTable(userId);
		
		Map<String, String> resultMap = new HashMap<>();
		
		if(workList != null) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		
		return resultMap;
		
	}
	
	@PostMapping("/random/day1")
	public Map<String, String> randomList(@RequestParam("Dduty") int Dduty
										 ,@RequestParam("Eduty") int Eduty
										 ,@RequestParam("Nduty") int Nduty
										 ,HttpSession session){
		
		int userId = (Integer)session.getAttribute("userId");
		
		//1일 근무표배열 생성
		List<Work> workAllList = workService.getWorkArr(Dduty, Eduty, Nduty, userId);
		Map<String, String> resultMap = new HashMap<>();
		if(workAllList != null) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		return resultMap;		
	}
}

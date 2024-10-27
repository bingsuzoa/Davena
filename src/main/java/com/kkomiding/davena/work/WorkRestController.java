package com.kkomiding.davena.work;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kkomiding.davena.work.domain.Work;
import com.kkomiding.davena.work.service.WorkService;

@RequestMapping("/work")
@RestController
public class WorkRestController {
	
	private WorkService workService;
	
	
	public WorkRestController(WorkService workService) {
		this.workService = workService;
	}

	
	@PostMapping("/insert")
	public Map<String, String> insertUserId(@RequestParam("type") String type
										   ,@RequestParam("startDay") LocalDateTime startDay
										   ,@RequestParam("endDay") LocalDateTime endDay
										   ,@RequestParam("holidayId") int holidayId
										   ,@RequestParam("userId") int userId){
		
		Work work = workService.getWork(type, startDay, endDay, holidayId, userId);
		
		Map<String, String> resultMap = new HashMap<>();
		
		if(work != null) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		
		return resultMap;
		
	}
}
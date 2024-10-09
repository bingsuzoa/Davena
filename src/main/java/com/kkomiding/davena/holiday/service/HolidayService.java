package com.kkomiding.davena.holiday.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kkomiding.davena.holiday.domain.Holiday;
import com.kkomiding.davena.holiday.repository.HolidayRepository;
import com.kkomiding.davena.user.service.UserService;

@Service
public class HolidayService {
	
	private HolidayRepository holidayRepository;
	private UserService userService;
	
	public HolidayService(HolidayRepository holidayRepository
						 ,UserService userService) {
		this.holidayRepository = holidayRepository;
		this.userService = userService;
	}
	
	public Holiday insertRequest(LocalDateTime startDay, LocalDateTime endDay
								,String type, String comment, String loginId) {
		
		int userId = userService.getUser(loginId).getId();
		
		Holiday holiday = Holiday.builder()
						 .userId(userId)
						 .startDay(startDay)
						 .endDay(endDay)
						 .dateCount(1)
						 .month(1)
						 .type(type)
						 .comment(comment)
						 .build();
						 		
		return holidayRepository.save(holiday);		
	}
	
	public List<Holiday> selectByUserId(int userId){
		
		return holidayRepository.findByUserId(userId);
	}

}

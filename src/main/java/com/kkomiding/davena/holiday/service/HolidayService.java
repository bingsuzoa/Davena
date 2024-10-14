package com.kkomiding.davena.holiday.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kkomiding.davena.holiday.domain.Holiday;
import com.kkomiding.davena.holiday.dto.PersonalSchedule;
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
								,String type, String comment, int userId) {
		
		int roomId = userService.getUser(userId).getRoomId();
		
		Holiday holiday = Holiday.builder()
						 .userId(userId)
						 .roomId(roomId)
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
	
	//HolidayDto
	public List<PersonalSchedule> getScheduleView(LocalDateTime startDate
												 ,LocalDateTime endDate
												 ,int userId){
		
		
		List<Holiday> holidayByUserList = holidayRepository.findByUserIdAndStartDayGreaterThanAndEndDayLessThan(userId
																											   ,startDate
																											   ,endDate);
		List<PersonalSchedule> personalScheduleList = new ArrayList<>();
		
		for(Holiday holiday : holidayByUserList) {
					
			PersonalSchedule personalSchedule = PersonalSchedule.builder()
												.title(holiday.getType())
												.start(holiday.getStartDay())
												.end(holiday.getEndDay())
												.holidayId(holiday.getId())
												.userId(userId)
												.build();
			
			personalScheduleList.add(personalSchedule);
		}
		
		return personalScheduleList;
	}
	
	public List<Holiday> getAllHolidayList(LocalDateTime startDate
										  ,LocalDateTime endDate
										  ,int roomId) {
		return holidayRepository.findByRoomIdAndStartDayGreaterThanAndEndDayLessThan(roomId, startDate, endDate);
	}
	
	//삭제 -1/2
	public boolean deleteRequest(int holidayId, int userId) {
		Optional<Holiday> optionalHoliday = holidayRepository.findByIdAndUserId(holidayId, userId);
		Holiday holiday = optionalHoliday.orElse(null);
		
		if(holiday != null) {
			holidayRepository.deleteByIdAndUserId(holidayId, userId);
			return true;
		} else {
			return false;
		}
	}
	//삭제 -2/2
	public void deleteByIdAndUserId(int holidayId, int userId) {	
	}
	
	public List<Holiday> findByRoomId(int roomId) {
		return holidayRepository.findByRoomId(roomId);
	}
}

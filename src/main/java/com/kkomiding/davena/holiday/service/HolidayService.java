package com.kkomiding.davena.holiday.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kkomiding.davena.holiday.domain.Holiday;
import com.kkomiding.davena.holiday.dto.PersonalSchedule;
import com.kkomiding.davena.holiday.repository.HolidayRepository;
import com.kkomiding.davena.user.domain.User;
import com.kkomiding.davena.user.service.UserService;

import jakarta.servlet.http.HttpSession;

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
		
		int month = startDay.getMonthValue();
		
		
		
		Holiday holiday = Holiday.builder()
						 .userId(userId)
						 .roomId(roomId)
						 .startDay(startDay)
						 .endDay(endDay)
						 .dateCount(1)
						 .month(month)
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
												.title(holiday.getComment())
												.start(holiday.getStartDay())
												.end(holiday.getEndDay())
												.holidayId(holiday.getId())
												.type(holiday.getType())
												.userId(userId)
												.build();
			
			personalScheduleList.add(personalSchedule);
		}
		
		return personalScheduleList;
	}
	
	//전체 근무자 일정 캘린더에 연동하기
	public List<PersonalSchedule> getAllHolidayList(LocalDateTime startDate
										  ,LocalDateTime endDate
										  ,int userId) {
		
		User user = userService.getUser(userId);
		int roomId = user.getRoomId();
		List<Holiday> holidayByRoomList = holidayRepository.findByRoomIdAndStartDayGreaterThanAndEndDayLessThan(roomId, startDate, endDate);
			
		List<PersonalSchedule> allScheduleList = new ArrayList<>();
		
		for(Holiday holiday : holidayByRoomList) {
			
			int id = holiday.getUserId();
			User holidayUser = userService.getUser(id);
			
			PersonalSchedule allSchedule = PersonalSchedule.builder()
										   .title(holidayUser.getName())
										   .start(holiday.getStartDay())
										   .end(holiday.getEndDay())
										   .holidayId(holiday.getId())
										   .type(holiday.getType())
										   .userId(userId)
										   .build();
			
			allScheduleList.add(allSchedule);										   
		}
		return allScheduleList;
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
	
	
	//내 연가 조회하기
	public int selectHolidayCount(int userId) {
		
		//이번년도로 시작하는것만 조회
		LocalDateTime currentTime = LocalDateTime.now();
		int year = currentTime.getYear();
		String first = year + "-01-01 00:00";
		String last = year + "-12-31 23:59";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime thisFirstYear = LocalDateTime.parse(first, formatter);
		LocalDateTime thisLastYear = LocalDateTime.parse(last, formatter);
		
		int count = holidayRepository.countByUserIdAndTypeAndStartDayGreaterThanAndEndDayLessThan(userId
																					 			,"연가"
																					 			,thisFirstYear
																					 			,thisLastYear);
		return count;
		
	}
}

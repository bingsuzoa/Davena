package com.kkomiding.davena.holiday.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.kkomiding.davena.holiday.domain.Holiday;
import com.kkomiding.davena.holiday.dto.PersonalSchedule;
import com.kkomiding.davena.holiday.repository.HolidayRepository;
import com.kkomiding.davena.user.domain.User;
import com.kkomiding.davena.user.service.UserService;
import com.kkomiding.davena.work.domain.Work;
import com.kkomiding.davena.work.repository.WorkRepository;

@Service
public class HolidayService {
	
	private HolidayRepository holidayRepository;
	private UserService userService;
	private WorkRepository workRepository;
	
	public HolidayService(HolidayRepository holidayRepository
						 ,UserService userService
						 ,WorkRepository workRepository) {
		this.holidayRepository = holidayRepository;
		this.userService = userService;
		this.workRepository = workRepository;
	}
	
	//휴가 신청할때 workdto에도 저장되도록 만들기
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
		
		Optional<Work> optionalWork = workRepository.findByUserId(userId);
		Work work =  optionalWork.orElse(null);
			
		if(holiday != null) {
			LocalDateTime startDay = holiday.getStartDay();
			LocalDateTime endDay = holiday.getEndDay();
			int start = startDay.getDayOfMonth();
			int end = 0;
			
			LocalDate localDate = endDay.toLocalDate().minusMonths(1);	
			YearMonth month = YearMonth.from(localDate);
			LocalDate endDate = month.atEndOfMonth();		
			if(endDay.getDayOfMonth() == 1) {
				end = endDate.getDayOfMonth();
			} else {			
				end = endDay.getDayOfMonth()-1;
			}
			
			work.setDay(start, end, null);
			work.setUpdatedAt(LocalDateTime.now());
			workRepository.save(work);			
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
	
	
	//내가 1년동안 사용한 연가 수 조회하기
	public long selectHolidayCount(int userId) {
		
		//이번년도로 시작하는것만 조회
		LocalDateTime currentTime = LocalDateTime.now();
		int year = currentTime.getYear();
		String first = year + "-01-01 00:00";
		String last = year + "-12-31 23:59";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime thisFirstYear = LocalDateTime.parse(first, formatter);
		LocalDateTime thisLastYear = LocalDateTime.parse(last, formatter);
		
		long dateCount = 0;
		
		List<Holiday> holidayList = holidayRepository.findByUserIdAndTypeAndStartDayGreaterThanAndEndDayLessThan(userId
																					 							,"연가"
																					 							,thisFirstYear
																					 							,thisLastYear);
		for(Holiday holiday : holidayList) {
			LocalDateTime startDay = holiday.getStartDay();
			LocalDateTime endDay = holiday.getEndDay();
			LocalDate startDate = startDay.toLocalDate();
			LocalDate endDate = endDay.toLocalDate();
			
			dateCount += startDate.until(endDate, ChronoUnit.DAYS);			
		}
		return dateCount;		
	}
	
	//내가 이번달 신청한 휴가현황 가져오기
	public List<Holiday> getListByUserIdAndMonth(int userId) {
		
		//현재 무슨 월 
		LocalDateTime currentTime = LocalDateTime.now();
		Month month = currentTime.getMonth();
		int monthValue = month.getValue();
		
		return holidayRepository.findByUserIdAndMonth(userId, monthValue);
	}
	
	//휴가 신청한 사람들 카운트
	public Map<String, Object> selectThisMonth(int userId) {
		
		//현재 무슨 월 
		LocalDateTime currentTime = LocalDateTime.now();
		Month month = currentTime.getMonth();
		int monthValue = month.getValue();

		
		//user의 방id
		int roomId = userService.getUser(userId).getRoomId();
		
		//신청한 사람들의 userId가 적혀있는 set, List
		Set<Integer> userIdSet = new HashSet<>();
		List<Integer> userIdList = new ArrayList<>();
		
		//방사람들의 이번달 신청현황
		List<Holiday> thisMonthHolidayList = holidayRepository.findByRoomIdAndMonth(roomId, monthValue);
		for(Holiday holiday : thisMonthHolidayList) {
			
			userIdSet.add(holiday.getUserId());
		}
		
		for(int i : userIdSet) {
			userIdList.add(i);
		}
		
		//전체 userList
		List<Integer> allUserIdList = new ArrayList<>();
		
		//방이 같은 사람들의 모든 리스트 조회하기
		List<User> userListByRoomId = userService.getUserListByRoomId(roomId);
		
		for(User user : userListByRoomId) {
			allUserIdList.add(user.getId());
		}
		
		//신청안한사람 현황 조회하기
		List<String> notApplyUserIdList = new ArrayList<>();
		for(int i = 0; i < allUserIdList.size(); i++) {
			if(!userIdList.contains(allUserIdList.get(i))){
				int notApplyId = allUserIdList.get(i);
				String name = userService.getUser(notApplyId).getName();
				notApplyUserIdList.add(name);
			}
		}
		
		
		Map<String, Object> resultMap = new HashMap<>();
			resultMap.put("allCount", allUserIdList.size());
			resultMap.put("applyCount", userIdList.size());
			resultMap.put("notApplyUserIdList", notApplyUserIdList);
			
		return resultMap;
	}
	
}

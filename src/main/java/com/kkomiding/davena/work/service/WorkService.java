package com.kkomiding.davena.work.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kkomiding.davena.holiday.service.HolidayService;
import com.kkomiding.davena.work.domain.Work;
import com.kkomiding.davena.work.repository.WorkRepository;


@Service
public class WorkService {
	
	
	private WorkRepository workRepository;
	
	public WorkService(WorkRepository workRepository
					  ,HolidayService holidayService) {
		this.workRepository = workRepository;
	}

	
	public Work insertUserId(int userId) {
		
		Work work = Work.builder()
						.userId(userId)
						.build();
		return workRepository.save(work);
	}
	
	public List<Work> getWorkList(){
		
		return workRepository.findAll();
	}
	
	
	//휴가 신청하면 work table에 저장하기
	public Work getWork(String type
					   ,LocalDateTime startDay
					   ,LocalDateTime endDay
					   ,int userId) {
	
		
		Optional<Work> optionalWork = workRepository.findByUserId(userId);
		Work work =  optionalWork.orElse(null);
		
		//날짜데이터 내가 원하는 타입에 맞게 수정하기
		int start = startDay.getDayOfMonth();
		
		//마지막날짜 다음달로 넘어가는 경우가 있으니깐; 
		int end = 0;
		LocalDate localDate = endDay.toLocalDate().minusMonths(1);	
		YearMonth month = YearMonth.from(localDate);
		LocalDate endDate = month.atEndOfMonth();	
		if(endDay.getDayOfMonth() == 1) {
			end = endDate.getDayOfMonth();
		} else {			
			end = endDay.getDayOfMonth()-1;
		}
		
		//workentity저장하기
		work.setDay(start, end, type);
		work.setUpdatedAt(LocalDateTime.now());
		Work newWork = workRepository.save(work);
		
		return newWork;
	
		
		
	}
}

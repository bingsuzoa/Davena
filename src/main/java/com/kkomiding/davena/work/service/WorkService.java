package com.kkomiding.davena.work.service;

import java.time.LocalDateTime;
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
					   ,int holidayId
					   ,int userId) {
	
		
		Optional<Work> optionalWork = workRepository.findByUserId(userId);
		Work work =  optionalWork.orElse(null);
		
		//날짜데이터 내가 원하는 타입에 맞게 수정하기
		int start = startDay.getDayOfMonth();
		int end = endDay.getDayOfMonth();
		
		//workentity저장하기
		work.setHolidayId(holidayId);
		work.setDay(start, end, type);
		work.setUpdatedAt(LocalDateTime.now());
		Work newWork = workRepository.save(work);
		
		return newWork;
	
		
		
	}
}

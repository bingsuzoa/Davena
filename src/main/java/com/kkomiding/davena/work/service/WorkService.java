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
	
	public Work getWork(int userId
						,LocalDateTime startDate
						,LocalDateTime endDate) {
	
		
		Optional<Work> optionalWork = workRepository.findByUserId(userId);
		Work work = optionalWork.orElse(null);
		
		//날짜데이터 내가 원하는 타입에 맞게 수정하기
		int start = startDate.getDayOfMonth();
		int end = endDate.getDayOfMonth();
		
		//신청한 휴가일을 넣는다.
		String column = "";
		for(int i = start; i <= end; i++) {
				column = "day" + i;  
				
		}
		
		
		
	}
}

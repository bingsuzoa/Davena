package com.kkomiding.davena.holiday.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kkomiding.davena.holiday.domain.Holiday;

import jakarta.transaction.Transactional;

public interface HolidayRepository extends JpaRepository<Holiday, Integer>  {
	
	public List<Holiday> findByUserId(int userId);
	
	
	//한명의 근무자 휴가리스트 조회
	public List<Holiday> findByUserIdAndStartDayGreaterThanAndEndDayLessThan(int userId
																			,LocalDateTime startDate
																			,LocalDateTime endDate);	
	
	//방전체 근무자 휴가리스트 조회
	public List<Holiday> findByRoomIdAndStartDayGreaterThanAndEndDayLessThan(int roomId
																			,LocalDateTime startDate
																			,LocalDateTime endDate);
	
	//한근무자의 연가 개수 조회
	public int countByUserIdAndTypeAndStartDayGreaterThanAndEndDayLessThan(int userId
																		 ,String Type
																		 ,LocalDateTime thisFirstYear
																		 ,LocalDateTime thisLastYear);
	
	public Optional<Holiday> findByIdAndUserId(int holidayId, int userId);
	
	public List<Holiday> findByRoomId(int roomId);
	
	@Transactional
	public void deleteByIdAndUserId(int holidayId, int userId);

}

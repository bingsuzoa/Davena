package com.kkomiding.davena.holiday.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kkomiding.davena.holiday.domain.Holiday;

import jakarta.transaction.Transactional;

public interface HolidayRepository extends JpaRepository<Holiday, Integer>  {
	
	public List<Holiday> findByUserId(int userId);
	
	public List<Holiday> findByUserIdAndStartDayGreaterThanAndEndDayLessThan(int userId
																			,LocalDateTime startDate
																			,LocalDateTime endDate);	
	
	public Optional<Holiday> findByIdAndUserId(int holidayId, int userId);
	
	public List<Holiday> findByRoomId(int roomId);
	
	@Transactional
	public void deleteByIdAndUserId(int holidayId, int userId);

}

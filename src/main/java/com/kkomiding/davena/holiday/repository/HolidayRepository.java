package com.kkomiding.davena.holiday.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kkomiding.davena.holiday.domain.Holiday;

import jakarta.transaction.Transactional;

public interface HolidayRepository extends JpaRepository<Holiday, Integer>  {
	
	public List<Holiday> findByUserId(int userId);
	
	public Optional<Holiday> findHolidayByIdAndUserId(int holidayId, int userId);
	
	@Transactional
	public void deleteHolidayByIdAndUserId(int holidayId, int userId);

}

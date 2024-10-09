package com.kkomiding.davena.holiday.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kkomiding.davena.holiday.domain.Holiday;

public interface HolidayRepository extends JpaRepository<Holiday, Integer>  {
	
	public List<Holiday> findByUserId(int userId);

}

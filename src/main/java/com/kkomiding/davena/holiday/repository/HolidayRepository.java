package com.kkomiding.davena.holiday.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kkomiding.davena.holiday.domain.Holiday;

public interface HolidayRepository extends JpaRepository<Holiday, Integer>  {

}

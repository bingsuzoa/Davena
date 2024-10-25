package com.kkomiding.davena.work.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class WorkDto {
	
	
	private int id;
	

	private int userId;
	
	
	private int holidayId;
	
	private String day1;

}

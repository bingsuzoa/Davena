package com.kkomiding.davena.holiday.dto;



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
public class ScheduleTable {

	
	private int holidayId;
	private int roomId;
	
	private String name;	
	private int start;
	private int end;
	
}

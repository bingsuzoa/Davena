package com.kkomiding.davena.work.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name ="work")
@Entity
public class Work {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="userId")
	private int userId;
	
	@Column(name="holidayId")
	private int holidayId;
	
	private String day1;	
	private String day2;
	private String day3;
	private String day4;
	private String day5;
	private String day6;
	private String day7;
	private String day8;
	private String day9;
	private String day10;
	private String day11;
	private String day12;
	private String day13;
	private String day14;
	private String day15;
	private String day16;
	private String day17;
	private String day18;
	private String day19;
	private String day20;
	private String day21;
	private String day22;
	private String day23;
	private String day24;
	private String day25;
	private String day26;
	private String day27;
	private String day28;
	private String day29;
	private String day30;
	private String day31;
	
	public void setDay(int start, int end String holiday) {
		if(start <= 1 && 1 <= end) {
			this.day1 = holiday;
		}
		if(start <= 2 && 2 <= end) {
			this.day2 = holiday;
		}
		if(start <= 3 && 3 <= end) {
			this.day3 = holiday;
		}
		if(start <= 4 && 4 <= end) {
			this.day4 = holiday;
		}
		if(start <= 5 && 5 <= end) {
			this.day5 = holiday;
		}
		if(start <= 6 && 6 <= end) {
			this.day6 = holiday;
		}
		if(start <= 7 && 7 <= end) {
			this.day7 = holiday;
		}
		if(start <= 8 && 8 <= end) {
			this.day8 = holiday;
		}
		if(start <= 9 && 9 <= end) {
			this.day9 = holiday;
		}
		if(start <= 10 && 10 <= end) {
			this.day10 = holiday;
		}
		if(start <= 11 && 11 <= end) {
			this.day11 = holiday;
		}
		if(start <= 12 && 12 <= end) {
			this.day12 = holiday;
		}
		if(start <= 13 && 13 <= end) {
			this.day13 = holiday;
		}
		if(start <= 14 && 14 <= end) {
			this.day14 = holiday;
		}
		if(start <= 15 && 15 <= end) {
			this.day15 = holiday;
		}
		if(start <= 16 && 16 <= end) {
			this.day16 = holiday;
		}
		if(start <= 17 && 17 <= end) {
			this.day17 = holiday;
		}
		if(start <= 18 && 18 <= end) {
			this.day18 = holiday;
		}
		if(start <= 19 && 19 <= end) {
			this.day19 = holiday;
		}
		if(start <= 20 && 20 <= end) {
			this.day20 = holiday;
		}
		if(start <= 21 && 21 <= end) {
			this.day21 = holiday;
		}
		if(start <= 22 && 22 <= end) {
			this.day22 = holiday;
		}
		if(start <= 23 && 23 <= end) {
			this.day23 = holiday;
		}
		if(start <= 24 && 24 <= end) {
			this.day24 = holiday;
		}
		if(start <= 25 && 25 <= end) {
			this.day25 = holiday;
		}
		if(start <= 26 && 26 <= end) {
			this.day26 = holiday;
		}
		if(start <= 27 && 27 <= end) {
			this.day27 = holiday;
		}
		if(start <= 28 && 28 <= end) {
			this.day28 = holiday;
		}
		if(start <= 29 && 29 <= end) {
			this.day29 = holiday;
		}
		if(start <= 30 && 30 <= end) {
			this.day30 = holiday;
		}
		if(start <= 31 && 31 <= end) {
			this.day31 = holiday;
		}
	}
	
	@CreationTimestamp
	@Column(name="createdAt")
	private LocalDateTime createdAt;
	
	
	@UpdateTimestamp
	@Column(name="updatedAt")
	private LocalDateTime updatedAt;

}

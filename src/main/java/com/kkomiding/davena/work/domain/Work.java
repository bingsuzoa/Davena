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
	
	
	@CreationTimestamp
	@Column(name="createdAt")
	private LocalDateTime createdAt;
	
	
	@UpdateTimestamp
	@Column(name="updatedAt")
	private LocalDateTime updatedAt;

}

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
@Table(name ="work")
@Entity
public class Work {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="userId")
	private int userId;
	
	@Column(name="roomId")
	private int roomId;
	
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
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getRoomId() {
		return roomId;
	}
	
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	
	public void setDay(int date, String type) {
		switch(date) {
		case 1 :
			this.day1 = type;
			break;
		case 2 :
			this.day2 = type;	
			break;
		case 3 :
			this.day3 = type;		
			break;
		case 4 :
			this.day4 = type;	
			break;
		case 5 :
			this.day5 = type;
			break;
		case 6 :
			this.day6 = type;
			break;
		case 7 :
			this.day7 = type;
			break;
		case 8 :
			this.day8 = type;
			break;
		case 9 :
			this.day9 = type;
			break;
		case 10:
			this.day10 = type;
			break;
		case 11:
			this.day11 = type;
			break;
		case 12 :
			this.day12 = type;		
			break;
		case 13 :
			this.day13 = type;	
			break;
		case 14 :
			this.day14 = type;	
			break;
		case 15 :
			this.day15 = type;	
			break;
		case 16 :
			this.day16 = type;
			break;
		case 17 :
			this.day17 = type;
			break;
		case 18 :
			this.day18 = type;
			break;
		case 19 :
			this.day19 = type;
			break;
		case 20:
			this.day20 = type;
			break;
		case 21 :
			this.day21 = type;
			break;
		case 22 :
			this.day22 = type;
			break;
		case 23 :
			this.day23 = type;	
			break;
		case 24 :
			this.day24 = type;	
			break;
		case 25 :
			this.day25 = type;
			break;
		case 26 :
			this.day26 = type;
			break;
		case 27 :
			this.day27 = type;
			break;
		case 28 :
			this.day28 = type;
			break;
		case 29 :
			this.day29 = type;
			break;
		case 30:
			this.day30 = type;	
			break;
		case 31:
			this.day31 = type;
			break;
			
		}
	}
	
	public void setDay(int start, int end, String type) {
		if(start <= 1 && 1 <= end) {
			this.day1 = type;
		}
		if(start <= 2 && 2 <= end) {
			this.day2 = type;
		}
		if(start <= 3 && 3 <= end) {
			this.day3 = type;
		}
		if(start <= 4 && 4 <= end) {
			this.day4 = type;
		}
		if(start <= 5 && 5 <= end) {
			this.day5 = type;
		}
		if(start <= 6 && 6 <= end) {
			this.day6 = type;
		}
		if(start <= 7 && 7 <= end) {
			this.day7 = type;
		}
		if(start <= 8 && 8 <= end) {
			this.day8 = type;
		}
		if(start <= 9 && 9 <= end) {
			this.day9 = type;
		}
		if(start <= 10 && 10 <= end) {
			this.day10 = type;
		}
		if(start <= 11 && 11 <= end) {
			this.day11 = type;
		}
		if(start <= 12 && 12 <= end) {
			this.day12 = type;
		}
		if(start <= 13 && 13 <= end) {
			this.day13 = type;
		}
		if(start <= 14 && 14 <= end) {
			this.day14 = type;
		}
		if(start <= 15 && 15 <= end) {
			this.day15 = type;
		}
		if(start <= 16 && 16 <= end) {
			this.day16 = type;
		}
		if(start <= 17 && 17 <= end) {
			this.day17 = type;
		}
		if(start <= 18 && 18 <= end) {
			this.day18 = type;
		}
		if(start <= 19 && 19 <= end) {
			this.day19 = type;
		}
		if(start <= 20 && 20 <= end) {
			this.day20 = type;
		}
		if(start <= 21 && 21 <= end) {
			this.day21 = type;
		}
		if(start <= 22 && 22 <= end) {
			this.day22 = type;
		}
		if(start <= 23 && 23 <= end) {
			this.day23 = type;
		}
		if(start <= 24 && 24 <= end) {
			this.day24 = type;
		}
		if(start <= 25 && 25 <= end) {
			this.day25 = type;
		}
		if(start <= 26 && 26 <= end) {
			this.day26 = type;
		}
		if(start <= 27 && 27 <= end) {
			this.day27 = type;
		}
		if(start <= 28 && 28 <= end) {
			this.day28 = type;
		}
		if(start <= 29 && 29 <= end) {
			this.day29 = type;
		}
		if(start <= 30 && 30 <= end) {
			this.day30 = type;
		}
		if(start <= 31 && 31 <= end) {
			this.day31 = type;
		}
	}
	
	
	public String getDay(int a) {
		String day = null;
		
		switch(a) {
		
		case 1: day = day1;
		case 2: day = day2;		
		case 3: day = day3;
		case 4: day = day4;
		case 5: day = day5;
		case 6: day = day6;
		case 7: day = day7;
		case 8: day = day8;
		case 9: day = day9;
		
		case 10: day = day10;
		case 11: day = day11;
		case 12: day = day12;		
		case 13: day = day13;
		case 14: day = day14;
		case 15: day = day15;
		case 16: day = day16;
		case 17: day = day17;
		case 18: day = day18;
		case 19: day = day19;
		
		case 20: day = day20;	
		case 21: day = day21;
		case 22: day = day22;		
		case 23: day = day23;
		case 24: day = day24;
		case 25: day = day25;
		case 26: day = day26;
		case 27: day = day27;
		case 28: day = day28;
		case 29: day = day29;
		
		case 30: day = day30;
		case 31: day = day31;
		
		}
		return day;
	}
	
	
	public String getDay1() {
		return day1;
	}
	public String getDay2() {
		return day2;
	}
	public String getDay3() {
		return day3;
	}
	public String getDay4() {
		return day4;
	}
	public String getDay5() {
		return day5;
	}
	public String getDay6() {
		return day6;
	}
	public String getDay7() {
		return day7;
	}
	public String getDay8() {
		return day8;
	}
	public String getDay9() {
		return day9;
	}
	public String getDay10() {
		return day10;
	}
	public String getDay11() {
		return day11;
	}
	public String getDay12() {
		return day12;
	}
	public String getDay13() {
		return day13;
	}
	public String getDay14() {
		return day14;
	}
	public String getDay15() {
		return day15;
	}
	public String getDay16() {
		return day16;
	}
	public String getDay17() {
		return day17;
	}
	public String getDay18() {
		return day18;
	}
	public String getDay19() {
		return day19;
	}
	public String getDay20() {
		return day20;
	}
	public String getDay21() {
		return day21;
	}
	public String getDay22() {
		return day22;
	}
	public String getDay23() {
		return day23;
	}
	public String getDay24() {
		return day24;
	}
	public String getDay25() {
		return day25;
	}
	public String getDay26() {
		return day26;
	}
	public String getDay27() {
		return day27;
	}
	public String getDay28() {
		return day28;
	}
	public String getDay29() {
		return day29;
	}
	public String getDay30() {
		return day30;
	}
	public String getDay31() {
		return day31;
	}
	
	@CreationTimestamp
	@Column(name="createdAt")
	private LocalDateTime createdAt;
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	
	@UpdateTimestamp
	@Column(name="updatedAt")
	private LocalDateTime updatedAt;
	
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

}

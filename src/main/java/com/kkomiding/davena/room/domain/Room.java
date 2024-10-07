package com.kkomiding.davena.room.domain;

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


@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "room")
@Entity
public class Room {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	
	@Column(name="userId")
	private int userId;
	
	@Column(name="roomName")
	private String roomName;
	
	
	@Column(name="roomPassword")
	private String roomPassword;
	
	@CreationTimestamp
	@Column(name="createdAt")
	private LocalDateTime createdAt;
	
	
	@UpdateTimestamp
	@Column(name="updatedAt")
	private LocalDateTime updatedAt;
}

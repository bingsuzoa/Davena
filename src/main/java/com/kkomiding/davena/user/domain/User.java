package com.kkomiding.davena.user.domain;

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
@Table(name ="user")
@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="loginId")
	private String loginId;
	
	private String password;
	
	private String salt;
	
	private String name;
	
	private String profile;
	
	private String position;
	
	@Column(name="roomId")
	private int roomId;
	
	@Column(name="roomName")
	private String roomName;
	
	@Column(name="roomPassword")
	private String roomPassword;
	
	private String approve;
	
	@CreationTimestamp
	@Column(name="createdAt")
	private LocalDateTime createdAt;
	
	
	@UpdateTimestamp
	@Column(name="updatedAt")
	private LocalDateTime updatedAt;

}


package com.kkomiding.davena.room.service;

import org.springframework.stereotype.Service;

import com.kkomiding.davena.room.domain.Room;
import com.kkomiding.davena.room.repository.RoomRepository;
import com.kkomiding.davena.user.domain.User;
import com.kkomiding.davena.user.service.UserService;

import jakarta.servlet.http.HttpSession;

@Service
public class RoomService {
	
	private RoomRepository roomRepository;
	private UserService userService;
	
	public RoomService(RoomRepository roomRepository
					  ,UserService userService) {
		this.roomRepository = roomRepository;
		this.userService = userService;
	}
	
	
	public Room addRoom(String roomName
					  ,String roomPassword
					  ,String loginId) {
				
		User user = userService.getUser(loginId);
		
		Room room = Room.builder()
					.userId(user.getId())
					.roomName(roomName)
					.roomPassword(roomPassword)
					.build();
		
		return roomRepository.save(room);
	}

}

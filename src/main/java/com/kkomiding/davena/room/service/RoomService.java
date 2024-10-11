package com.kkomiding.davena.room.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kkomiding.davena.room.domain.Room;
import com.kkomiding.davena.room.repository.RoomRepository;
import com.kkomiding.davena.user.domain.User;
import com.kkomiding.davena.user.service.UserService;

@Service
public class RoomService {
	
	private RoomRepository roomRepository;
	private UserService userService;
	
	public RoomService(RoomRepository roomRepository
					  ,UserService userService) {
		this.roomRepository = roomRepository;
		this.userService = userService;
	}
	
	//방만들기 
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
	
	//방 조회해오기
	public Room getRoom(String roomName, String roomPassword) {
		
		Optional<Room> optionalRoom = roomRepository.findByRoomNameAndRoomPassword(roomName, roomPassword);
		Room room = optionalRoom.orElse(null);
		
		return room;
	}

}

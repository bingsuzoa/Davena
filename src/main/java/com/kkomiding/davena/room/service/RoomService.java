package com.kkomiding.davena.room.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kkomiding.davena.room.domain.Room;
import com.kkomiding.davena.room.repository.RoomRepository;
import com.kkomiding.davena.user.domain.User;
import com.kkomiding.davena.user.service.UserService;
import com.kkomiding.davena.work.domain.Work;
import com.kkomiding.davena.work.repository.WorkRepository;

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
	public int addRoom(String roomName
					  ,String roomPassword
					  ,int userId) {
				
		User user = userService.getUser(userId);
		
		Room room = Room.builder()
					.userId(user.getId())
					.roomName(roomName)
					.roomPassword(roomPassword)
					.build();				
		roomRepository.save(room);
		 int roomId = room.getId();
		
		userService.updateUser(userId, roomId);
		
		return roomId;

	}
	//방 만들때 방이름 중복확인
		public Room checkRoom(String roomName) {
			
			Optional<Room> optionalRoom = roomRepository.findByRoomName(roomName);
			Room room = optionalRoom.orElse(null);
			
			return room;
		}
		
	
	//방 조회해오기
	public Room getRoom(String roomName, String roomPassword) {
		
		Optional<Room> optionalRoom = roomRepository.findByRoomNameAndRoomPassword(roomName, roomPassword);
		Room room = optionalRoom.orElse(null);
		
		return room;
	}
	
	public Room getRoomById(int roomId) {
		Optional<Room> optionalRoom = roomRepository.findById(roomId);
		Room room = optionalRoom.orElse(null);
		
		return room;
	}
	
	public Room getRoomByUserId(int userId) {
		Optional<Room> optionalRoom = roomRepository.findByUserId(userId);
		Room room = optionalRoom.orElse(null);
		
		return room;
	}

}

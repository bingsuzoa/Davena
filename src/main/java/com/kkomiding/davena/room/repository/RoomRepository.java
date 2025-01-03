package com.kkomiding.davena.room.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kkomiding.davena.room.domain.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {
	
	public Optional<Room> findByRoomNameAndRoomPassword(String roomName, String roomPassword);
	
	public Optional<Room> findByUserId(int userId);
	
	public Optional<Room> findByRoomName(String roomName);
	
	public Optional<Room> findById(int roomId);
	
	public int countById(int roomId);
	

}

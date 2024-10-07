package com.kkomiding.davena.room.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kkomiding.davena.room.domain.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {

}

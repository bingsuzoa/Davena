package com.kkomiding.davena.work.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kkomiding.davena.work.domain.Work;

public interface WorkRepository extends JpaRepository<Work, Integer> {
	

	public Optional<Work> findByUserId(int userId);
	
	public List<Work> findByRoomId(int roomId);
	
	public int countByRoomId(int roomId);
	
}

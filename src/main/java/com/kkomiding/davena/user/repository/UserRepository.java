package com.kkomiding.davena.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kkomiding.davena.user.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	//User객체 1개 가져오기
	public Optional<User> findById(int userId);
	
	public Optional<User> findByLoginId(String loginId);
	
	//User객체 1개 가져오기
	public Optional<User> findByIdAndPassword(int userId, String Hashing_password);
	
	//User list가져오기
	public List<User> findByRoomNameAndRoomPasswordAndApproveAndPosition(String roomName, String roomPassword, String approve, String position);
	
	


}

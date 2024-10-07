package com.kkomiding.davena.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kkomiding.davena.user.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	//User객체 1개 가져오기
	public Optional<User> findByLoginId(String loginId);
	
	//User객체 1개 가져오기
	public Optional<User> findByLoginIdAndPassword(String loginId, String Hashing_password);
	
	

}

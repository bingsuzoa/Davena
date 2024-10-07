package com.kkomiding.davena.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kkomiding.davena.user.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	

}

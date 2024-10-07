package com.kkomiding.davena.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kkomiding.davena.common.hash.FileManager;
import com.kkomiding.davena.common.hash.SaltwithSHAHasingEncoder;
import com.kkomiding.davena.user.domain.User;
import com.kkomiding.davena.user.repository.UserRepository;

@Service
public class UserService {
	
	private UserRepository userRepository;
	private SaltwithSHAHasingEncoder salting;
	
	public UserService(UserRepository userRepository
					   ,SaltwithSHAHasingEncoder salting) {
		this.userRepository = userRepository;
		this.salting = salting;
	}
	
	
	public User addUser(String loginId,String password ,String name
					  ,MultipartFile profile
					  ,String position
					  ,String roomName ,String roomPassword) throws Exception {
		
		String urlPath = FileManager.saveFile(loginId, profile);
		List<String> saltAndPw = salting.setSaltPw(loginId, password);
		String salt = saltAndPw.get(0);
		String encryptpassword = saltAndPw.get(1);
				
		User user = User.builder()
					.loginId(loginId)
					.password(encryptpassword)
					.salt(salt)
					.name(name)
					.profile(urlPath)
					.position(position)
					.roomName(roomName)
					.roomPassword(roomPassword)
					.approve("미승인")
					.build();
		
		return user;
					
	}	
}

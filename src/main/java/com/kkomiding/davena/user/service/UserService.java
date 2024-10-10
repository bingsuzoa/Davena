package com.kkomiding.davena.user.service;

import java.util.Map;
import java.util.Optional;

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
	
	//회원가입, insert
	public User addUser(String loginId,String password ,String name
					  ,String position
					  ,MultipartFile profile
					  ,String roomName ,String roomPassword) throws Exception {
		//프로필 사진
		String urlPath = FileManager.saveFile(loginId, profile);
		
		//Hashing
		Map<String,String> saltAndPw = salting.setSaltPw(loginId, password);
		String salt = saltAndPw.get("salt");
		String encryptpassword = saltAndPw.get("saltedPassword");
				
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
		
		return userRepository.save(user);					
	}
	
	//Id중복확인, User객체 얻어오기
	public User getUser(String loginId) {
		Optional<User> optionalUser = userRepository.findByLoginId(loginId);
		User user = optionalUser.orElse(null);
		return user;
	}
	
	//id, password통해 User객체 얻어오기
	public User getUserAndPw(String loginId, String password, String salt) throws Exception {
		
		byte[] byte_pw = password.getBytes();
		String Hashing_password = salting.Hashing(byte_pw, salt);
		
		Optional<User> optionalUser = userRepository.findByLoginIdAndPassword(loginId, Hashing_password);
		User user = optionalUser.orElse(null);
		return user;
	}
}

package com.kkomiding.davena.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kkomiding.davena.common.hash.FileManager;
import com.kkomiding.davena.common.hash.SaltwithSHAHasingEncoder;
import com.kkomiding.davena.room.domain.Room;
import com.kkomiding.davena.room.service.RoomService;
import com.kkomiding.davena.user.domain.User;
import com.kkomiding.davena.user.repository.UserRepository;

@Service
public class UserService {
	
	private UserRepository userRepository;
	private SaltwithSHAHasingEncoder salting;
	private RoomService roomService;

	
	public UserService( UserRepository userRepository
					   ,SaltwithSHAHasingEncoder salting
					   ,RoomService roomService) {
		this.userRepository = userRepository;
		this.salting = salting;
		this.roomService = roomService;

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
	public User getUserAndPw(String loginId, String password) throws Exception {
		
		User userForSalt = getUser(loginId);
		String salt = userForSalt.getSalt();
		
		byte[] byte_pw = password.getBytes();
		String Hashing_password = salting.Hashing(byte_pw, salt);
		
		Optional<User> optionalUser = userRepository.findByLoginIdAndPassword(loginId, Hashing_password);
		User userForLogin = optionalUser.orElse(null);
		return userForLogin;
	}
	
	//방 이름, 방 비밀번호 조회해서 팀장 userId, 미승인된 user리스트 가져오기
	public Map<String, Object> useRoomInfo(String roomName, String roomPassword) {
		
		List<User> userListByApprove = userRepository.findByRoomNameAndRoomPasswordAndApprove(roomName, roomPassword, "미승인");
		
		int leaderId =  roomService.getRoom(roomName, roomPassword).getUserId();
		
		Map<String, Object> notApproveDirectory = new HashMap<>();
		notApproveDirectory.put("leaderId", leaderId);
		notApproveDirectory.put("notApproveUser", userListByApprove);	
		
		return notApproveDirectory;
	}
	
}

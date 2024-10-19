package com.kkomiding.davena.user.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import com.kkomiding.davena.common.hash.FileManager;
import com.kkomiding.davena.common.hash.SaltwithSHAHasingEncoder;
import com.kkomiding.davena.room.domain.Room;
import com.kkomiding.davena.room.repository.RoomRepository;
import com.kkomiding.davena.user.domain.User;
import com.kkomiding.davena.user.domain.UserDto;
import com.kkomiding.davena.user.repository.UserRepository;

@Service
public class UserService {
	
	private UserRepository userRepository;
	private SaltwithSHAHasingEncoder salting;
	private RoomRepository roomRepository;
	
	public UserService( UserRepository userRepository
					   ,SaltwithSHAHasingEncoder salting
					   ,RoomRepository roomRepository) {
		this.userRepository = userRepository;
		this.salting = salting;
		this.roomRepository = roomRepository;
	}
	
	
	//회원가입 유효성 검증
//	@Transactional(readOnly=true)
//	public Map<String, String> validateHandling(Errors errors) {
//		Map<String, String> validatorResult = new HashMap<>();
//		
//		for(FieldError error : errors.getFieldErrors()) {
//			String validKeyName = String.format("valid_%s", error.getField());
//			validatorResult.put(validKeyName, error.getDefaultMessage());
//		}
//		return validatorResult;
//	}
	
	public User userJoin(UserDto userDto) throws Exception {
		
		String loginId = userDto.getLoginId();
		MultipartFile profile = userDto.getProfile();
		String password = userDto.getPasswordCheck();
		String roomName = userDto.getRoomName();
		String roomPassword = userDto.getRoomPassword();
		String position = userDto.getPosition();
		String name = userDto.getName();
		
		//프로필 사진
		String urlPath = FileManager.saveFile(loginId, profile);
		
		//Hashing
		Map<String,String> saltAndPw = salting.setSaltPw(loginId, password);
		String salt = saltAndPw.get("salt");
		String encryptpassword = saltAndPw.get("saltedPassword");
		
		//roomId찾기
		Optional<Room> optionalRoom = roomRepository.findByRoomNameAndRoomPassword(roomName, roomPassword);
		Room room = optionalRoom.orElse(null);
		
		if(position.equals("팀원")) {
			User user = User.builder()
						.loginId(loginId)
						.password(encryptpassword)
						.salt(salt)
						.name(name)
						.profile(urlPath)
						.position(position)
						.roomId(room.getId())
						.roomName(roomName)
						.roomPassword(roomPassword)
						.approve("미승인")
						.build();
			return userRepository.save(user);					
		} else {
			User user = User.builder()
					.loginId(loginId)
					.password(encryptpassword)
					.salt(salt)
					.name(name)
					.profile(urlPath)
					.position(position)
					.roomId(0)
					.roomName(roomName)
					.roomPassword(roomPassword)
					.approve("승인")
					.build();
		return userRepository.save(user);
		}
		
	}

	
//	//회원가입, insert
//	public User addUser(String loginId,String password ,String name
//					  ,String position
//					  ,MultipartFile profile
//					  ,String roomName ,String roomPassword) throws Exception {
//		//프로필 사진
//		String urlPath = FileManager.saveFile(loginId, profile);
//		
//		//Hashing
//		Map<String,String> saltAndPw = salting.setSaltPw(loginId, password);
//		String salt = saltAndPw.get("salt");
//		String encryptpassword = saltAndPw.get("saltedPassword");
//		
//		//roomId찾기
//		Optional<Room> optionalRoom = roomRepository.findByRoomNameAndRoomPassword(roomName, roomPassword);
//		Room room = optionalRoom.orElse(null);
//				
//		if(position.equals("팀원")) {
//			User user = User.builder()
//						.loginId(loginId)
//						.password(encryptpassword)
//						.salt(salt)
//						.name(name)
//						.profile(urlPath)
//						.position(position)
//						.roomId(room.getId())
//						.roomName(roomName)
//						.roomPassword(roomPassword)
//						.approve("미승인")
//						.build();
//			return userRepository.save(user);					
//		} else {
//			User user = User.builder()
//					.loginId(loginId)
//					.password(encryptpassword)
//					.salt(salt)
//					.name(name)
//					.profile(urlPath)
//					.position(position)
//					.roomId(0)
//					.roomName(roomName)
//					.roomPassword(roomPassword)
//					.approve("승인")
//					.build();
//		return userRepository.save(user);
//		}
//		
//	}
	
	//팀장 방만들면 user객체 update수정
	public User updateUser(int userId, int roomId) {
		
		
		
		Optional<Room> optionalRoom = roomRepository.findById(roomId);
		Room room = optionalRoom.orElse(null);
		
		User user = userRepository.findById(userId).orElse(null);
			user = user.toBuilder()
					.roomId(room.getId())
					.roomName(room.getRoomName())
					.roomPassword(room.getRoomPassword())
					.updatedAt(LocalDateTime.now())
					.build();
		
		return userRepository.save(user);
	}
	
	//User객체 얻어오기
	public User getUser(int userId) {
		Optional<User> optionalUser = userRepository.findById(userId);
		User user = optionalUser.orElse(null);
		return user;
	}
	
	//Id중복확인
	public User getLoginId(String loginId) {
		Optional<User> optionalUser = userRepository.findByLoginId(loginId);
		User user = optionalUser.orElse(null);
		return user;
	}
	
	//id, password통해 User객체 얻어오기
	public User getUserAndPw(int userId, String password) throws Exception {
		
		User userForSalt = getUser(userId);
		String salt = userForSalt.getSalt();
		
		byte[] byte_pw = password.getBytes();
		String Hashing_password = salting.Hashing(byte_pw, salt);
		
		Optional<User> optionalUser = userRepository.findByIdAndPassword(userId, Hashing_password);
		User userForLogin = optionalUser.orElse(null);
		return userForLogin;
	}
	
	//방 이름, 방 비밀번호 조회해서 미승인이며, 팀원인 user리스트 가져오기
	public List<User> useRoomInfo(int leaderId) {
		
		Optional<Room> optionalRoom = roomRepository.findByUserId(leaderId);
		Room room = optionalRoom.orElse(null);
		
		String roomName = null;
		String roomPassword = null;
		
		if(room != null) {
			roomName = room.getRoomName();
			roomPassword = room.getRoomPassword();
		}
		
		List<User> userListByApprove = userRepository.findByRoomNameAndRoomPasswordAndApproveAndPosition(roomName, roomPassword, "미승인", "팀원");
		
		
		return userListByApprove;
	}
	
	public User changeApprove(int userId) {
		Optional<User> optionalUser = userRepository.findById(userId);
		User user = optionalUser.orElse(null);
		
		user = user.toBuilder()
				.approve("승인")
				.updatedAt(LocalDateTime.now())
				.build();
		
		return userRepository.save(user);
	}
	
	public User rejectApprove(int userId) {
		Optional<User> optionalUser = userRepository.findById(userId);
		User user = optionalUser.orElse(null);
		
		user = user.toBuilder()
				.approve("거부")
				.updatedAt(LocalDateTime.now())
				.build();
		
		return userRepository.save(user);
	}
	
	//전체근무자 휴가신청한 사람 카운트하기위해 '승인'여부 확인필요
	public List<User> getUserListByRoomId(int roomId) {
		return userRepository.findByRoomIdAndApprove(roomId, "승인");
	}

	
}

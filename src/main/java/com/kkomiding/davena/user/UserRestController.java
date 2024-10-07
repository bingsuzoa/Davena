package com.kkomiding.davena.user;


import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kkomiding.davena.user.domain.User;
import com.kkomiding.davena.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/user")
@RestController
public class UserRestController {
	
	private UserService userService;
	
	public UserRestController(UserService userService) {
		this.userService = userService;
	}
	
	//회원가입
	@PostMapping("/join")
	public Map<String, String> join(
									 @RequestParam("join-idInput") String loginId
									,@RequestParam("join-checkPwInput") String password
									,@RequestParam("join-userName") String name
									,@RequestParam("position") String position
									,@RequestParam(value="profile", required = false) MultipartFile profile
									,@RequestParam("join-roomName") String roomName
									,@RequestParam("join-roomPw") String roomPassword) throws Exception {
		
		
		User user = userService.addUser(loginId, password, name
										,position
										,profile
										,roomName, roomPassword);
		
		Map<String, String> resultMap = new HashMap<>();
		
		if(user != null) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		
		return resultMap;
	}
	
	//Id 중복확인
	@GetMapping("/duplicate-id")
	public Map<String, Boolean> selectLoginId(@RequestParam("join-idInput") String loginId) {
		
		User user = userService.getUser(loginId);
		
		Map<String, Boolean> resultMap = new HashMap<>();
		
		if(user == null) {
			resultMap.put("isDuplicate", false);
		} else {
			resultMap.put("isDuplicate", true);
		}
		
		return resultMap;
	}
	
	//로그인작업 - Id의 Hashing값 찾기, salt값 가져오기
	public String getSALT(@RequestParam("idInput") String loginId) {
		
		User user = userService.getUser(loginId);
		
		if(user == null) {
			return null;
		} else {
			return user.getSalt();
		}
	}
	
	//로그인 작업 - Id와 Hashing비밀번호 모두 일치하는지 확인
	@PostMapping("/login")
	public Map<String, String> loginUser(@RequestParam("idInput") String loginId
										,@RequestParam("pwInput") String password
										,HttpServletRequest request) throws Exception{
		
		String salt = getSALT(loginId);
		User user = userService.getUserAndPw(loginId, password, salt);
		
		Map<String, String> resultMap = new HashMap<>();
		if(user != null) {
			resultMap.put("result", "success");
			
			HttpSession session = request.getSession();
			session.setAttribute("userId", user.getLoginId());
			session.setAttribute("userName", user.getName());
			
		} else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}

}

package com.kkomiding.davena.user;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kkomiding.davena.user.domain.User;
import com.kkomiding.davena.user.domain.UserDto;
import com.kkomiding.davena.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
									 @RequestParam("join_idInput") String loginId
									,@RequestParam("join_checkPwInput") String password
									,@RequestParam("join_userName") String name
									,@RequestParam("position") String position
									,@RequestParam(value="profile", required = false) MultipartFile profile
									,@RequestParam("join_roomName") String roomName
									,@RequestParam("join_roomPw") String roomPassword) throws Exception {
	
		
		User newUser = userService.addUser(loginId, password, name
										,position
										,profile
										,roomName, roomPassword);
		
		Map<String, String> resultMap = new HashMap<>();
		
		if(newUser != null) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		
		return resultMap;
	}
	
	
	//Id 중복확인
	@GetMapping("/duplicate-id")
	public Map<String, Boolean> selectLoginId(@RequestParam("join_idInput") String loginId) {
		
		User user = userService.getLoginId(loginId);
		
		Map<String, Boolean> resultMap = new HashMap<>();
		
		if(user == null) {
			resultMap.put("isDuplicate", false);
		} else {
			resultMap.put("isDuplicate", true);
		}
		
		return resultMap;
	}

	//로그인 작업 - Id와 Hashing비밀번호 모두 일치하는지 확인
	@PostMapping("/login")
	public Map<String, String> loginUser(@RequestParam("idInput") String loginId
										,@RequestParam("pwInput") String password
										,HttpServletRequest request) throws Exception{
		
		int userId = userService.getLoginId(loginId).getId();
		User user = userService.getUserAndPw(userId, password);
		String position = user.getPosition();
		String approve = user.getApprove();
		
		Map<String, String> resultMap = new HashMap<>();
		if(position != null) {
			if(position.equals("팀원")) {
				if(approve.equals("미승인")) {
					resultMap.put("position", "member");
					resultMap.put("isApprove", "notApprove");		
				} else {
					resultMap.put("position", "member");
					resultMap.put("isApprove", "approve");	
				}
			} else {
				resultMap.put("position", "leader");
				resultMap.put("isApprove", "approve");	
			}
			HttpSession session = request.getSession();
			session.setAttribute("userId", user.getId());
			session.setAttribute("userName", user.getName());
			session.setAttribute("approve", user.getApprove());
		}
		return resultMap;
	}

	@PostMapping("/apply")
	public Map<String, String> approveJoin(HttpSession session){
		
		int userId = (Integer)session.getAttribute("userId");
		
		//modal에 띄울 리스트
		List<User> notApproveDirectory = userService.useRoomInfo(userId);
		
		Map<String, String> resultMap = new HashMap<>();
		if(notApproveDirectory != null) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		return resultMap;
		
	}
	
	@PostMapping("/apply/approve")
	public Map<String, String> changeApprove(@RequestParam("userId") int userId) {
		
		String approve = userService.changeApprove(userId).getApprove();
		
		Map<String, String> resultMap = new HashMap<>();
		if(approve.equals("승인")) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}
	
	@PostMapping("/apply/reject")
	public Map<String, String> rejectApprove(@RequestParam("userId") int userId) {
		
		String approve = userService.rejectApprove(userId).getApprove();
		
		Map<String, String> resultMap = new HashMap<>();
		if(approve.equals("거부")) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}
	
	//회원가입 검증과정
	
}

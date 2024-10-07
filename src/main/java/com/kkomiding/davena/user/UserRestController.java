package com.kkomiding.davena.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kkomiding.davena.user.domain.User;
import com.kkomiding.davena.user.service.UserService;

@RequestMapping("/user")
@RestController
public class UserRestController {
	
	private UserService userService;
	
	public UserRestController(UserService userService) {
		this.userService = userService;
	}
	
	
	@PostMapping("/join")
	public Map<String, String> join(
									@RequestParam("join-idInput") String loginId
									,@RequestParam("join-checkPwInput") String password
									,@RequestParam("join-userName") String name
									,@RequestParam(value="profile", required = false) MultipartFile profile
									,@RequestParam("positionBtn") String position
									,@RequestParam("join-roomName") String roomName
									,@RequestParam("join-roomPw") String roomPassword) throws Exception {
		
		
		User user = userService.addUser(loginId, password, name
										,profile, position
										,roomName, roomPassword);
		
		Map<String, String> resultMap = new HashMap<>();
		
		if(user != null) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		
		return resultMap;
	}

}

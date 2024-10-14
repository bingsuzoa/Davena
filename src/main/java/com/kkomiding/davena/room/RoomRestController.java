package com.kkomiding.davena.room;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kkomiding.davena.room.domain.Room;
import com.kkomiding.davena.room.service.RoomService;
import com.kkomiding.davena.user.domain.User;
import com.kkomiding.davena.user.service.UserService;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/room")
@RestController
public class RoomRestController {
	
	private RoomService roomService;
	
	public RoomRestController(RoomService roomService) {
		this.roomService = roomService;
	}
	
	@PostMapping("make")
	public Map<String, String> makeRoom(@RequestParam("roomName") String roomName
										,@RequestParam("roomPw") String roomPassword
										,HttpSession session){
		
		int userId = (Integer)session.getAttribute("userId");
		
		Room room = roomService.addRoom(roomName, roomPassword, userId);
		
		
		Map<String, String> resultMap = new HashMap<>();
		if(room != null) { 
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		
		return resultMap;
	}
	
	@PostMapping("duplicate-room")
	public Map<String, Boolean> duplicateRoom(@RequestParam("roomName") String roomName
											,@RequestParam("roomPw") String roomPassword){
		
		
		Room room = roomService.getRoom(roomName, roomPassword);
		
		Map<String, Boolean> resultMap = new HashMap<>();
		if(room != null) { 
			resultMap.put("isDuplicate", true);
		} else {
			resultMap.put("isDuplicate", false);
		}
		
		return resultMap;
	}
}

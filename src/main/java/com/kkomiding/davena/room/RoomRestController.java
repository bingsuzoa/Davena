package com.kkomiding.davena.room;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kkomiding.davena.room.domain.Room;
import com.kkomiding.davena.room.service.RoomService;
import com.kkomiding.davena.work.domain.Work;
import com.kkomiding.davena.work.repository.WorkRepository;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/room")
@RestController
public class RoomRestController {
	
	private RoomService roomService;
	private WorkRepository workRepository;
	
	public RoomRestController(RoomService roomService
							 ,WorkRepository workRepository) {
		this.roomService = roomService;
		this.workRepository = workRepository;
	}
	
	
	@PostMapping("/make")
	public Map<String, String> makeRoom(@RequestParam("roomName") String roomName
										,@RequestParam("roomPw") String roomPassword
										,HttpSession session){
		
		int userId = (Integer)session.getAttribute("userId");
		
		Optional<Work> optionalWork = workRepository.findByUserId(userId);
		Work work =  optionalWork.orElse(null);
		
		int roomId = roomService.addRoom(roomName, roomPassword, userId);
		
		
		Map<String, String> resultMap = new HashMap<>();
		if(roomId > 0) { 
			work.setRoomId(roomId);
			workRepository.save(work);
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		
		return resultMap;
	}
	
	
	@PostMapping("/duplicate-room")
	public Map<String, Boolean> duplicateRoom(@RequestParam("roomName") String roomName
											,@RequestParam("roomPw") String roomPassword
											,HttpSession session){
		
		int userId = (Integer)session.getAttribute("userId");
		Room madeRoomByUser = roomService.getRoomByUserId(userId);
		Room existRoom = roomService.checkRoom(roomName);
		
		Map<String, Boolean> resultMap = new HashMap<>();
		if(madeRoomByUser == null  && existRoom == null) {
			resultMap.put("isDuplicate", false);
		} 
		else if(madeRoomByUser != null  && existRoom == null){
			resultMap.put("madeRoomByUser", true);
		}
		else if(madeRoomByUser == null  && existRoom != null){
			resultMap.put("existRoom", true);
		} else {
			resultMap.put("isDuplicate", true);
		}
		
		return resultMap;
	}
}

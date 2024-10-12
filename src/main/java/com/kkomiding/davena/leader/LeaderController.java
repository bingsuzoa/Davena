package com.kkomiding.davena.leader;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kkomiding.davena.user.domain.User;
import com.kkomiding.davena.user.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/leader")
public class LeaderController {
	
	private UserService userService;
	
	public LeaderController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/login-view")
	public String login() {
		
		return "leader/login";
	}
	
	@GetMapping("/make-view")
	public String makeRoom() {
		
		return "leader/make";
	}
	
	@GetMapping("/all-detail-view")
	public String alldetail(Model model, HttpSession session) {
		
		int userId =(Integer)session.getAttribute("userId");
		String position = userService.getUser(userId).getPosition();
		List<User> userListByApprove =userService.useRoomInfo(userId);
		
		model.addAttribute("notApproveUserList" , userListByApprove);
		model.addAttribute("position", position);
		
		return "leader/alldetail";
	}
	
	@GetMapping("/holiday-set-view")
	public String holidaySetView() {
		
		return "leader/holidaysetview";
	}
	
	@GetMapping("/work-set-view")
	public String workySetView() {
		
		return "leader/worksetview";
	}
	
	@GetMapping("/member-detail-view")
	public String memberDetailView() {
		
		return "leader/memberdetail";
	}
	
	@GetMapping("/calendar-view")
	public String calendarView() {
		
		return "leader/calendarview";
	}

}

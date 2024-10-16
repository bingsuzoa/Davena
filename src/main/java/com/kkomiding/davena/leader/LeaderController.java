package com.kkomiding.davena.leader;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kkomiding.davena.holiday.service.HolidayService;
import com.kkomiding.davena.user.domain.User;
import com.kkomiding.davena.user.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/leader")
public class LeaderController {
	
	private UserService userService;
	private HolidayService holidayService;
	
	public LeaderController(UserService userService
						   ,HolidayService holidayService) {
		this.userService = userService;
		this.holidayService = holidayService;
	}
	
	@GetMapping("/login-view")
	public String login(HttpSession session, Model model) {
		
		int userId = (Integer)session.getAttribute("userId");
		User user = userService.getUser(userId);
		
		model.addAttribute("user",user);
		
		return "leader/login";
	}
	
	@GetMapping("/make-view")
	public String makeRoom(HttpSession session, Model model) {
		
		int userId = (Integer)session.getAttribute("userId");
		User user = userService.getUser(userId);
		
		model.addAttribute("user",user);
		
		return "leader/make";
	}
	
	@GetMapping("/all-detail-view")
	public String alldetail(Model model
						   ,HttpSession session) {
		
		int userId =(Integer)session.getAttribute("userId");
		
		//여기는 회원가입 승인을 위한 코드입니다.
		String position = userService.getUser(userId).getPosition();
		List<User> notApproveUserList =userService.useRoomInfo(userId);

		model.addAttribute("position", position);
		model.addAttribute("notApproveUserList", notApproveUserList);
		
		//여기는 휴가 신청을 안한 명단을 찾기위한 코드입니다.
		Map<String, Object> resultMap = holidayService.selectThisMonth(userId);
			
		model.addAttribute("allCount", resultMap.get("allCount"));
		model.addAttribute("applyCount", resultMap.get("applyCount"));
		model.addAttribute("notApplyUserIdList" , resultMap.get("notApplyUserIdList"));
		
		return "leader/alldetail";
	}
	
	@GetMapping("/holiday-set-view")
	public String holidaySetView() {
		
		return "leader/holidaysetview";
	}
	
	@GetMapping("/work-set-view")
	public String workySetView(HttpSession session, Model model) {
		
		int userId = (Integer)session.getAttribute("userId");
		User user = userService.getUser(userId);
		
		model.addAttribute("user",user);
		
		return "leader/worksetview";
	}
	

}

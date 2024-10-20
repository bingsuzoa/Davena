package com.kkomiding.davena.user;





import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.kkomiding.davena.user.domain.User;
import com.kkomiding.davena.user.domain.UserDto;
import com.kkomiding.davena.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class UserController {
	
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	
	//공통
	@GetMapping("/user/login-view")
	public String login() {
		
		return "users/login";
	}
	
	@GetMapping("/user/join-view")
	public String join(Model model) {
		
		model.addAttribute("userDto", new UserDto());
	
		return "users/join";
	}
	
	@PostMapping("/auth/joinProc")
	public String joinProc(@Valid @ModelAttribute UserDto userDto, Errors errors, Model model) {
		
		if(errors.hasErrors()) {
			model.addAttribute("userDto", userDto);
			
			Map<String, String> validatorResult = userService.validateHandling(errors);
			for(String key : validatorResult.keySet()) {
				model.addAttribute(key, validatorResult.get(key));
			}
			return "users/join";
		}
		String position = userDto.getPositionBtn();
		if(position.equals("팀장")) {
			return "leader/login-view";
		} else {
			return "member/before-apply-view";
		}
		
	}
	
	@GetMapping("/user/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("userId");
		session.removeAttribute("userName");
		
		return "redirect:/user/login-view";
	}
	
	//팀원만 들어갈 수 있는 페이지
	@GetMapping("/member/before-apply-view")
	public String afterjoin() {
		
		return "users/beforeapply";
	}
	
	@GetMapping("/member/after-apply-view")
	public String afterlogin(HttpSession session
							,Model model) {
		
		int userId = (Integer)session.getAttribute("userId");
		User user = userService.getUser(userId);
		
		model.addAttribute("user",user);
		return "users/afterapply";
	}
	

}

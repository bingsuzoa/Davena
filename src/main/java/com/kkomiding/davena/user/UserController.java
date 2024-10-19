package com.kkomiding.davena.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.kkomiding.davena.user.domain.User;
import com.kkomiding.davena.user.domain.UserDto;
import com.kkomiding.davena.user.service.UserService;
import com.kkomiding.davena.validator.CheckLoginIdValidator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class UserController {
	
	private UserService userService;
	private final CheckLoginIdValidator checkLoginIdValidator;
	
	public UserController(UserService userService, CheckLoginIdValidator checkLoginIdValidator) {
		this.userService = userService;
		this.checkLoginIdValidator = checkLoginIdValidator;
	}
	
	@InitBinder
	public void validatorBinder(WebDataBinder binder) {
		binder.addValidators(checkLoginIdValidator);
	}
	
	
	//공통
	@GetMapping("/user/login-view")
	public String login() {
		
		return "users/login";
	}
	
	@GetMapping("/user/join-view")
	public String join() {
		
		return "users/join";
	}
	
	//회원가입 validate 
	@GetMapping("/auth/join")
	public String validateJoin() {
		
		return "users/join";
	}
	
	//회원가입 유효성검사
	@PostMapping("/auth/joinProc")
	public String joinPOST(@Valid UserDto userDto
						  ,BindingResult bindingResult 	
						  ,Model model) throws Exception {
			
		if(bindingResult.hasErrors()) {
			//회원가입 실패 시 입력 데이터 값 유지
			model.addAttribute("userDto", userDto);
			
			//유효성 검사 통과하지 못했을 때
			Map<String, String> errorMap = new HashMap<>();
			
			for(FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put("valid_" + error.getField(), error.getDefaultMessage());
			}
			return "/users/join";
		}
			
			User user = userService.userJoin(userDto);
			String approve = user.getPosition();
			if(approve.equals("팀장")) {
				return "redirct:/leader/login";
			} else {
				return "redirect:/holiday/beforeapply";
			}
			
			
	}
	
	
	@GetMapping("/auth/joinProc/{loginId}/exists")
	public ResponseEntity<Boolean> checkLoginIdDuplicate(@PathVariable String loginId){
		//responseEntity를 사용하면 적절한 상태코드와 응답헤더 및 응답본문을 생성해서 클라이언트에 전달가능
		return ResponseEntity.ok(userService.checkLoginIdDuplication(loginId));
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

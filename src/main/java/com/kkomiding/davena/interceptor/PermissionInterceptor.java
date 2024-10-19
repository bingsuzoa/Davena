package com.kkomiding.davena.interceptor;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.kkomiding.davena.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class PermissionInterceptor implements HandlerInterceptor {
	
	
	@Autowired
	private UserService userService;
	
	
	@Override
	public boolean preHandle(HttpServletRequest request
							,HttpServletResponse response
							,Object handle) throws IOException {
		
		HttpSession session = request.getSession();
		Integer userId = (Integer) session.getAttribute("userId");
		
		String uri = request.getRequestURI();
		
		if(userId == null) {
			if(uri.startsWith("/leader") || uri.startsWith("/member")) {
				response.sendRedirect("/user/login-view");
				return false;
			}
		} else {
			String position = userService.getUser(userId).getPosition();
			if(position.equals("팀원")) {
				if(uri.startsWith("/leader") || uri.equals("/user/login-view")) {
					response.sendRedirect("/member/after-apply-view");
					return false;
				}
			}
			else if(position.equals("팀장")) {
				if(uri.startsWith("/member") || uri.equals("/user/login-view")) {
					response.sendRedirect("/leader/login-view");
					return false;
				}
			}
		}
		return true;
	}

}

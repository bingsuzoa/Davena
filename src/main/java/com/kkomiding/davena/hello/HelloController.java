package com.kkomiding.davena.hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
	
	
	@ResponseBody
	@RequestMapping("/hello")
	public String hello() {
		return "hello world";
	}
	
	
	@ResponseBody
	@RequestMapping("/schedule")
	public String[][] schedule() {
		
		String [][] schedule = new String[31][10];
		
		for(int i = 0; i < schedule.length; i++) {
			for(int j = 0; j < schedule[i].length; j++) {
				schedule[i][j] = "Day";
			}
		}
		return schedule;

	}

}

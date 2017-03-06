package org.test.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.test.po.User;
import org.test.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService service;
	
	@RequestMapping("/findAllUsers")
	public String findAllUsers(HttpServletRequest req){
		List<User> userList = service.findAllUsers();
		
		req.setAttribute("userList", userList);
		
		 
		return "userList";
	};
	
	@RequestMapping("/toRegist")
	public String toRegist(){
		return "regist";
	}
	
	@RequestMapping("/toLogin")
	public String toLogin(){
		return "login";
	}
	
	@RequestMapping("/regist")
	public String regist(@RequestParam String name,@RequestParam String username,@RequestParam int age,@RequestParam String password,@RequestParam String mail,
			@RequestParam int gender,@RequestParam String phonenum){
		
		service.regist(name, username, password, age, gender, mail, phonenum);
		return "common/success";
		
	}
	
	@RequestMapping("/login")
	public String login(@RequestParam String username,@RequestParam String password){
		boolean logflag = service.login(username,password);
		if(logflag){
			return "common/success";
		}
		return "common/error";
	}

}

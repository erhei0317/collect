package com.candata.controller;


import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.candata.coremodel.User;
import com.candata.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping("/login")
	public String userlogin(int id,HttpServletRequest req){
		User user = userService.getUserByid(id);
		req.setAttribute("user", user);
		return "show";
		
	}
	

}

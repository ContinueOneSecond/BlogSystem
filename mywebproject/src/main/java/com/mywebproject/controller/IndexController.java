package com.mywebproject.controller;


import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;



import com.mywebproject.service.BlogUserService;

@Controller
//@RequestMapping("/")
public class IndexController {

	@RequestMapping("/index")
	public String index(Model model,HttpServletRequest request) {
				
	return "thymeleaf/index";
	}
	
	
	
	
}

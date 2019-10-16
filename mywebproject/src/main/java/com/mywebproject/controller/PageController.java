package com.mywebproject.controller;

import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.mywebproject.model.BlogUser;
import com.mywebproject.service.BlogService;
import com.mywebproject.service.BlogUserService;
import com.mywebproject.service.UserMassageService;

import utils.CookiesUtils;
import utils.LimitUtil;
import utils.SessionUtils;

@Controller
@RequestMapping("/index")
public class PageController {

	@Autowired
	private BlogUserService bolguser;
	
	@Autowired
	private UserMassageService userMassageService;
	
	@Autowired
	BlogController blog;

	private boolean isLogin = false;

	@RequestMapping("/login")
	public String login() {
		return "thymeleaf/login";
	}

	@RequestMapping("/blog")
	public String bolg() {
		return "thymeleaf/blog";
	}

	@RequestMapping("/resgiter")
	public String resgiter() {
		return "thymeleaf/register";
	}
	
	@RequestMapping("/uploadfile")
	public String upLoadFile() {
		return "thymeleaf/upLoadFile";
	}
		
	@RequestMapping(value = "/adduser", method = RequestMethod.POST)
	public String addUser(BlogUser user) {

		if (user != null) {
			try {
				bolguser.addNewUser(user);
				BlogUser thisuser = bolguser.findUser(user);
				int uid = thisuser.getUid();			
				userMassageService.addUserMassage(uid);
			} catch (Exception e) {
				e.printStackTrace();			
				return "erro";
			}

			return "redirect:/index/";
		} else {
			return "redirect:/index/register";
		}
	}

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response) {

		HttpSession session = request.getSession();
		isLogin = (boolean) session.getAttribute("isLogin");
		
		
		
		if (isLogin == true) {
			
			isLogin = false;
			
			
			session.setAttribute("isLogin", isLogin);
			session.setMaxInactiveInterval(0);
			
			CookiesUtils.cookiesMethod(response, null, null, 0);
		}
		return "redirect:/index";
	}

	@RequestMapping(value = "/checkusername", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkUserName(@RequestBody Map<String, Object> map) {

		long exist = bolguser.findUsername(map);
		boolean isExist = false;

		if (exist > 0) {
			isExist = true;
		} else {
			isExist = false;
		}
		return isExist;
	}

	@RequestMapping("/checkuser")
	@ResponseBody
	public boolean checkUser(@RequestBody BlogUser user, HttpServletResponse response,
			HttpServletRequest request) {

		int uid = 0;
		int life = 0;

		String blogusername = null;

		if (user.getUsername() == "" && user.getPassword() == "")
			return false;

		BlogUser bloguser = bolguser.findUser(user);

		System.out.println(bloguser);

		if (bloguser != null) {
			String username = bloguser.getUsername();
			uid = bloguser.getUid();

			life = 1 * 60 * 60;

			if (request.getCookies() == null) {

				String sessionID = SessionUtils.sessionMethod(request, isLogin, username, uid, life);

				CookiesUtils.cookiesMethod(response, sessionID, username, life);

			} else {
				Cookie[] cookie = request.getCookies();
				for (Cookie cookies : cookie) {

					blogusername = cookies.getName();

					if (bloguser.getUsername() != blogusername) {

						String sessionID = SessionUtils.sessionMethod(request, isLogin, username, uid, life);

						CookiesUtils.cookiesMethod(response, sessionID, username, life);

					}
				}
			}

			HttpSession session = request.getSession();
			isLogin = true;
			session.setAttribute("isLogin", isLogin);
			
			String userPortrait  = userMassageService.findUserPortrait(uid);
			
			if(userPortrait == null) {
				userPortrait ="/index/images/Portrait.jpg";
			}
			
			session.setAttribute("userPortrait", userPortrait);
			
			System.out.println("查找用户方法执行完毕");

			return true;
		} else {
			return false;
		}
	}
}

package utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtils {

	public static String sessionMethod( HttpServletRequest request,boolean isLogin, String username,int uid,int life) {
		
		
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(life);
		isLogin = true;
		session.setAttribute("isLogin", isLogin);
		session.setAttribute("username", username);
		session.setAttribute("uid", uid);
		String sessionID = session.getId();
		
		
		System.out.println(uid);
		System.out.println(sessionID);
		
		return sessionID;
	}
}

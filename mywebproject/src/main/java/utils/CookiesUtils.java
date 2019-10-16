package utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class CookiesUtils {

	public static void cookiesMethod(HttpServletResponse response,String sessionID ,String username,int life) {
		
		
		Cookie cookie1 = new Cookie("JSESSIONID", sessionID);
		cookie1.setMaxAge(life);
		Cookie cookie2 = new Cookie("username", username);
		cookie2.setMaxAge(life);
		response.addCookie(cookie1);
		response.addCookie(cookie2);
	}
}

package utils;

import javax.servlet.http.HttpServletRequest;

public class UrlParamsUtil {
	
	public  int getUserPam(String url) {
		
	
		//获取pageIndex=的位置
		int urlPageIndex = url.indexOf("pageIndex=")+10;
		//获取&符号位置
		int urland = url.indexOf('&');
		//获取中间的String
		String pageIndexValue =  url.substring(urlPageIndex, urland);
			
		return Integer.parseInt(pageIndexValue);
	}
	
	//获得链接链接参数
	public String getUrlparm(String url,HttpServletRequest request) {
		
		return request.getQueryString();
	}
	
	
}

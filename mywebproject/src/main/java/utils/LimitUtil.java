package utils;

import java.util.HashMap;
import java.util.Map;

public class LimitUtil {
	
	
	
	public Map<String,Object> limitUtil(Integer pageSize,Integer pageIndex){
		Integer startIndex = (pageIndex - 1) * pageSize;

		Map<String, Object> pageMassage = new HashMap<String, Object>();
		pageMassage.put("pageSize", pageSize);
		pageMassage.put("startIndex", startIndex);
		return pageMassage;
	
	}
	
	public int getPageCount(Integer blogCount,Integer pageSize) {
		Integer pageCount = 0;
		
		if (blogCount < pageSize) {
			pageCount = 1;
			return pageCount;
		} else if (blogCount % pageSize == 0) {
			pageCount = blogCount / pageSize;
			return pageCount;
		} else {
			pageCount = blogCount / pageSize + 1;
			return pageCount;
		}
		
	}
}

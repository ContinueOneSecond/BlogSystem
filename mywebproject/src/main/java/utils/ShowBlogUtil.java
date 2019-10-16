package utils;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.mywebproject.model.Blog;

public class ShowBlogUtil{

	public void showBlogUtils(Integer index, HttpServletRequest request, Model model) {

		if (index != null) {
			HttpSession session = request.getSession();

			@SuppressWarnings("unchecked")
			List<Blog> blogs = (List<Blog>) session.getAttribute("blogs");

			Blog blog = blogs.get(index);

			model.addAttribute("blog", blog);
		}
	}
}

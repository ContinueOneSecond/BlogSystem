package com.mywebproject.controller;

import java.io.BufferedInputStream;

import java.io.FileInputStream;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mywebproject.model.Blog;
import com.mywebproject.model.UserFiles;
import com.mywebproject.service.BlogService;
import com.mywebproject.service.UploadFiles;
import com.mywebproject.service.UserMassageService;

import utils.LimitUtil;
import utils.ShowBlogUtil;
import utils.UpLoad;
import utils.UrlParamsUtil;

@Controller
@RequestMapping("/index/blog")
public class BlogController {

	@Autowired
	public BlogService bolgService;

	@Autowired
	public UserMassageService userMassageService;

	@Autowired
	public UploadFiles uplodfiles;

	private final static String REAL_PATH = "E:\\Java\\SSM\\Myproject\\mywebproject\\src\\main\\resources\\static\\index\\images\\";
	private final static String REAL_FILES_PATH = "E:\\Java\\SSM\\Myproject\\mywebproject\\src\\main\\resources\\static\\index\\files\\";

	@RequestMapping("/editor")
	public String editor() {

		return "thymeleaf/editor";
	}

//	@RequestMapping("/searBlog")
//	public String searchBlog() {
//		return "thymeleaf/searBlog";
//	}

//	@RequestMapping("/findblog")
//	@ResponseBody
//	public boolean findBlog(Integer uid, HttpServletRequest request) {
//
//		List<Blog> blogs = bolgService.seekBlog(uid);
// 
//		if (uid != null && blogs != null) {
//
//			HttpSession session = request.getSession();
//			session.setAttribute("blogs", blogs);
//			session.setAttribute("test", "test");
//			System.out.println("执行findblog方法");
//
//			return true;
//		} else {
//			return false;
//		}
//	}

	@RequestMapping("/blogPage")
	public String blogPage(Integer uid, @RequestParam(defaultValue = "1") int pageIndex, HttpServletRequest request,
			LimitUtil limitutils, Model model) {
		
		String thisReqeustUrl = request.getServletPath();
		
		//http://localhost:8080/index/blog/blogPage?pageIndex=1&uid=3
				
		//使用正则表达式截取，使用group前必须使用.find()
		String getReqeustPath = "blog\\/(.*)";
		
		Pattern pt=Pattern.compile(getReqeustPath);
		Matcher mt = pt.matcher(thisReqeustUrl);
		
		while(mt.find()) {
			thisReqeustUrl = mt.group();
		}
		
		System.out.println(thisReqeustUrl);
		
		int pageSize = 4;
		int blogCount = 0;
		Integer pageCount = null;
		List<Blog> blogs = null;
		int[] pagetatol = null;
		HttpSession session = null;

		Map<String, Object> pageMassage = limitutils.limitUtil(pageSize, pageIndex);
		
		session = request.getSession();
		
		int sessionUid = (int) session.getAttribute("uid");

		System.out.println("session id:"+sessionUid);
		
		System.out.println(uid == sessionUid);
		
		if (uid != null && uid == sessionUid) {

			pageMassage.put("uid", uid);

			System.out.println("当前用户的uid是" + uid);

			blogs = bolgService.pageBlog(pageMassage);

			blogCount = bolgService.blogCount(uid);

			pageCount = limitutils.getPageCount(blogCount, pageSize);

			pagetatol = new int[pageCount];


			if (blogs.size() >= 0) {

				// 这次请求的url
				model.addAttribute("thisUrl", thisReqeustUrl);

				// 博客
				session.setAttribute("blogs", blogs);

				// 当前页
				session.setAttribute("pageIndex", pageIndex);

				// 页面总数数组
				session.setAttribute("pagetatol", pagetatol);

				// 页面总数
				session.setAttribute("lastpage", pageCount);

				// 博客总条数
				session.setAttribute("blogCount", blogCount);
				
				if (thisReqeustUrl.equals("blog/blogPage")) {
					return "thymeleaf/blog";
				} else {
					return "thymeleaf/userMassage";
				}

			} else {
				return "thymeleaf/Index";
			}

		} else {

			return "thymeleaf/Index";
		}
	}

	@RequestMapping("/addblog")
	@ResponseBody
	public boolean addBlog(@RequestBody Blog blog, Model model, LimitUtil limi, HttpServletRequest request) {

		int blogCount = 0;
		int pageCount = 0;
		Integer uid = null;

		System.out.print(blog);
		try {
			if (blog != null) {

				bolgService.sumbitBlog(blog);

				uid = (Integer) request.getSession().getAttribute("uid");

				blogCount = bolgService.blogCount(uid);

				pageCount = limi.getPageCount(blogCount, 4);

				request.getSession().setAttribute("lastpage", pageCount);

				System.out.println("发布后的页码为" + request.getSession().getAttribute("lastpage"));

				return true;

			} else {

				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();

			return false;
		}
	}

	@RequestMapping(value = "/showblog", params = { "index" })
	public String showblog(Integer index, HttpServletRequest request, Model model) {

		System.out.println(index);
		if (index != null) {
			HttpSession session = request.getSession();

			@SuppressWarnings("unchecked")
			List<Blog> blogs = (List<Blog>) session.getAttribute("blogs");

			Blog blog = blogs.get(index);

			model.addAttribute("blog", blog);

			return "thymeleaf/showblog";
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/upload")
	@ResponseBody
	public Map<String, Object> upload(@RequestParam(value = "editormd-image-file", required = false) MultipartFile file,
			UpLoad upLoafImage, HttpServletRequest request) {

		return upLoafImage.upLoafImage(file, REAL_PATH);
	}

	@RequestMapping(value = "/uploadfile")
	@ResponseBody
	public boolean uploadfile(@RequestParam(value = "uploadFile", required = false) MultipartFile file, String fileName,
			String file_describe, UpLoad upLoadFile, HttpServletRequest request) {

		try {
			UserFiles thisFiles = upLoadFile.upLoaFile(file, fileName, REAL_FILES_PATH);
			int uid = (int) request.getSession().getAttribute("uid");
			thisFiles.setFile_describe(file_describe);
			thisFiles.setUid(uid);

			System.out.println(thisFiles.toString());

			uplodfiles.addFile(thisFiles);
			return true;
		} catch (IOException e1) {
			e1.printStackTrace();
			return false;
		}
	}

	@RequestMapping(value = ("/downloadfile"), params = { "fid" })
	public String downLoadFile(int fid, HttpServletRequest request, HttpServletResponse response) throws IOException {

		System.out.println("执行downloadfile");
		System.out.println(fid);
		String url = null;
		String fileName = null;

		@SuppressWarnings("unchecked")
		List<UserFiles> files = (List<UserFiles>) request.getSession().getAttribute("files");

		System.out.println(files == null);

		for (UserFiles file : files) {

			if (file.getFid() == fid) {
				int filesIndex = files.indexOf(file);
				UserFiles thisFile = files.get(filesIndex);
				fileName = thisFile.getFile_name();
				String oldsuffix = fileName.substring(fileName.lastIndexOf(".")+1);
				
				System.out.println(oldsuffix);
				
				if(oldsuffix.equals("txt")) {
					System.out.println("修改文件名称编码方法");
					fileName = URLEncoder.encode(fileName, "UTF-8");
				}
				
				url = REAL_FILES_PATH+thisFile.getFile_name();
				//url = thisFile.getFile_name();
				System.out.println("需要下载文的url是" + url);
				
				response.setContentType(request.getServletContext().getMimeType(fileName));
				response.setContentType("application/force-download");
				response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);

				FileInputStream in = null;
				BufferedInputStream bufferInPut = null;
				ServletOutputStream out = null;
				try {
					in = new FileInputStream(url);
					bufferInPut = new BufferedInputStream(in);
					out = response.getOutputStream();

					byte[] buffer = new byte[1024];

					int i = bufferInPut.read(buffer);

					while (i != -1) {
						out.write(buffer, 0, i);
						i = bufferInPut.read(buffer);
					}

				} catch (IOException e) {
					e.printStackTrace();
					return "失败";
				} finally {
					if (bufferInPut != null) {
						bufferInPut.close();
					}
					
					if (in != null) {
						in.close();
					}
					
					if (out != null) {
						out.close();
					}

				}

			}

		}
		return null;

	}

	@RequestMapping("/uploadimage")
	@ResponseBody
	public String uploadimage(@RequestParam(value = "uploadImage", required = false) MultipartFile file,
			UpLoad upLoafImage, HttpServletRequest request) {

		System.out.println(file == null);

		// String lastUrl = request.getHeader("Referer");
		Map<String, Object> resultMap = null;

		if (file != null) {
			resultMap = upLoafImage.upLoafImage(file, REAL_PATH);

			String user_portrait = (String) resultMap.get("url");

			int uid = (int) request.getSession().getAttribute("uid");

			try {
				userMassageService.changeUserMassage(user_portrait, uid);
				String userPortrait = userMassageService.findUserPortrait(uid);
				request.getSession().setAttribute("userPortrait", userPortrait);

				return userPortrait;
			} catch (Exception e) {
				e.printStackTrace();
				return "erro";
			}

		} else {
			return "erro";
		}

	}

	@RequestMapping(value = "/delblog", params = { "bid", "index" })
	public String delBlog(Integer bid, int index, HttpServletRequest request, UrlParamsUtil urlutil,
			LimitUtil limitutils, Model model) {

		if (bid != null) {

			String lastUrl = request.getHeader("Referer");

			try {
				bolgService.deleteblog(bid);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

			if (index == 0) {

				// 获得pageIndex的值
				int pageIndex = urlutil.getUserPam(lastUrl);

				int urlPageIndex = lastUrl.indexOf("pageIndex=") + 10;

				System.out.println("pageIndex的值为" + pageIndex);

				// pageIndex-1
				int newpageIndex = pageIndex - 1;

				if (newpageIndex <= 0) {
					newpageIndex = 1;
				}

				// 将newpageIndex转为String
				String newPageIndex = String.valueOf(newpageIndex);

				// 临时变量lastURl暂时保存lastUrl
				StringBuilder lastUrl2 = new StringBuilder(lastUrl);

				// 替换lasturl pageIndex的值
				lastUrl2.replace(urlPageIndex, urlPageIndex + 1, newPageIndex);

				// 转换为String
				lastUrl = lastUrl2.toString();

				System.out.println(lastUrl);

				return "redirect:" + lastUrl;
			} else {

				System.out.println(lastUrl);
				return "redirect:" + lastUrl;
			}
//			return "redirect:" + lastUrl;
		} else {

			return null;
		}
	}

	@RequestMapping(value = "/changeblog", params = { "index" })
	public String changeBlog(Integer index, HttpServletRequest request, Model model, ShowBlogUtil show,
			UrlParamsUtil urlutil) {

		show.showBlogUtils(index, request, model);

		String lastUrl = request.getHeader("Referer");

		System.out.println(lastUrl);

		model.addAttribute("lastUrl", lastUrl);

		return "thymeleaf/changeEditor";
	}

	@RequestMapping(value = "updatablog")
	@ResponseBody
	public boolean updataBlog(@RequestBody Blog blog, HttpServletRequest request) {

		System.out.println(blog);

		if (blog != null) {

			try {

				bolgService.changeBlog(blog);
				return true;

			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		} else {
			return false;
		}

	}

	@RequestMapping("/searchBlog")
	public String searchBlog(String searchBody, @RequestParam(defaultValue = "1") int pageIndex,
			HttpServletRequest reqeust, UrlParamsUtil urlutil, LimitUtil limitutils, Model model) {

		System.out.println("查找:" + searchBody);

		int pageSize = 10;
		int blogCount = 0;
		Integer pageCount = null;
		int[] pagetatol = null;
		HttpSession session = null;
		Integer uid = null;

		if (searchBody != null) {

			session = reqeust.getSession();
			uid = (Integer) session.getAttribute("uid");

			Map<String, Object> map = limitutils.limitUtil(pageSize, pageIndex);

			map.put("uid", uid);
			map.put("searchBody", searchBody);

			blogCount = bolgService.blogCount(uid);

			List<Blog> seekBlog = bolgService.findBlog(map);
			int findBlogCount = seekBlog.size();

			pageCount = limitutils.getPageCount(findBlogCount, pageSize);

			pagetatol = new int[pageCount];

			// url
			model.addAttribute("thisUrl", "searchBlog");

			// 博客
			session.setAttribute("blogs", seekBlog);

			// 当前页
			session.setAttribute("pageIndex", pageIndex);

			// 页面总数数组(用于循环遍历)
			session.setAttribute("pagetatol", pagetatol);

			// 页面总数(用于判断)
			session.setAttribute("lastpage", pageCount);

			// 博客总条数(用于显示博客总数)
			session.setAttribute("blogCount", blogCount);

			// 将searchbody放入model
			model.addAttribute("searchBody", searchBody);

			System.out.println("执行了search方法");

			return "thymeleaf/blog";
		} else {
			blogPage(uid, pageIndex, reqeust, limitutils, model);
			return "thymeleaf/blog";
		}

	}

	@RequestMapping("/usermassage")
	public String userMassage(BlogController blog, Integer uid, @RequestParam(defaultValue = "1") int pageIndex,
			HttpServletRequest request, LimitUtil limitutils, Model model) {

		System.out.println("执行usermassage");

		List<UserFiles> allfiles = uplodfiles.findFiles(uid);

		System.out.println(allfiles.toString());

		request.getSession().setAttribute("files", allfiles);

		return blogPage(uid, pageIndex, request, limitutils, model);

	}

}

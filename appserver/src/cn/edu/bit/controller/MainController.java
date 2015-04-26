package cn.edu.bit.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/main")
public class MainController {
	
	
	
	@RequestMapping("index")
	public String index(HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("Loging");
		if(request.getSession()!=null && request.getSession().getAttribute("LoginUser")!=null){
			return "main/index";
		}
		
		return "/login";
	}
	
	/**
	 * 加载左侧的课程和课次的列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "loadUserList", produces = "plain/text;charset=UTF-8")
	public String loadTree(){
		
		
		return "[]";
	}
	

}

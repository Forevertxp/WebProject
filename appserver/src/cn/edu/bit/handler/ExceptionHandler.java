package cn.edu.bit.handler;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
//异常类 Exception arg3为异常的类型
public class ExceptionHandler implements HandlerExceptionResolver {
	//记录日志
	Logger logger  =  Logger.getLogger(ExceptionHandler.class);
	@Override
	//ModelAndView
	public ModelAndView resolveException(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3) {
		// TODO Auto-generated method stub
		logger.error(arg3.getMessage());
		Map<String,Object> map = new HashMap<String,Object>(); 
		map.put("errorMsg", arg3.getMessage());
		//返回到错误页面，传入键值
		return new ModelAndView("framework/error", map);
	}

}

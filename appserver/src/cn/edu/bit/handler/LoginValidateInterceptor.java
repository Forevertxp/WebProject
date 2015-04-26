package cn.edu.bit.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginValidateInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res,
			Object cls) throws Exception {

		String path = req.getServletPath();
		if (path.indexOf("/images/") == 0
				|| path.indexOf("/scripts") == 0
				|| path.indexOf("css") == 0
				|| path.equals("/main/index")
				|| path.equals("/user/validateLoginUser")
				|| path.equals("/user/syncUsers")
				|| path.equals("/attention/syncAttention")
				|| path.equals("/consult/loadNoReadConsultById")
				|| path.equals("/consult/loadConsultList")
				|| path.equals("/pushMsg/loadPushMsgFromService")
				|| path.equals("/pushMsg/giveReadLogToService")				
				|| path.equals("/detection/syncDetections")
				|| path.equals("/detectioninfo/getDectionInfoByValue")
				|| (req.getSession() != null && req.getSession().getAttribute(
						"LoginUser") != null)) {
			return true;

		}
		throw new Exception("请<a href='../login.jsp'>重新登录.</a>"); 
	}

}

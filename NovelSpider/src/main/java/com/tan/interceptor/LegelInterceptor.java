package com.tan.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.tan.model.Root;
import com.tan.tool.StringUtil;

public class LegelInterceptor implements HandlerInterceptor {

	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {
	}
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		
	}
	
	@InitBinder("root")  
    public void initBinder1(WebDataBinder binder) {  
		binder.setFieldDefaultPrefix("root.");  
    }
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
			Object arg2) throws Exception {
		HttpSession session=request.getSession();
		//System.out.println("---preHandle---");
		HandlerMethod handle=((HandlerMethod) arg2);
		String url=request.getParameter("root.url");
		String encode=request.getParameter("root.encode");
		boolean flag=false;
		if(session==null)
		{
			//System.out.println("---session==null---");
			return false;
		}
		if(session.getAttribute("login")==null)
		{
			session.setAttribute("login", false);
		}
		else
		{
			flag=(boolean) session.getAttribute("login");
		}
		if(flag)
		{
			return true;
		}
		if((StringUtil.ifempty(url))||StringUtil.ifempty(encode))
		{
			//System.out.println("---Redirect---");
			response.sendRedirect("/springmvc/index.do");
			return false;
		}
		return true;
	}
	
}

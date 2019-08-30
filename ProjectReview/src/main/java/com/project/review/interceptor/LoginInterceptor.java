package com.project.review.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	private static final String LOGIN = "login";

	@Override // 컨트롤러 실행후 실행
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		HttpSession session = request.getSession();
		ModelMap modelMap = modelAndView.getModelMap();
		Object memberDTO = modelMap.get("member");

		if (memberDTO != null) {
			session.setAttribute(LOGIN, memberDTO);
			Object destination = session.getAttribute("destination");
			response.sendRedirect(destination != null ? (String) destination : "/");
			session.setAttribute("destination", null);
		}
	}

	@Override // 컨트롤러 실행 전 실행
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		if (session.getAttribute(LOGIN) != null) {
			session.removeAttribute(LOGIN);
			return false;
		}
		return true;
	}
}
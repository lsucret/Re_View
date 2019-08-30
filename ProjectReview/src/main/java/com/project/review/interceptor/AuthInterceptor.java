package com.project.review.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	private void saveDestination(HttpServletRequest request) {
		String uri = request.getRequestURI(); // URI가져오기
		String query = request.getQueryString();
		if (query == null || query.equals("null")) {
			query = "";
		} else {
			query = "?" + query;
		}
		request.getSession().setAttribute("destination", uri + query);
	}

	@Override // 컨트롤러 실행 전 실행
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		if (session.getAttribute("login") == null) {
			saveDestination(request);
			response.sendRedirect("/account/login");
			return false;
		}
		return true;
	}
}
package com.gxuwz.medical.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * 用户退出
 * @author 演示
 *
 */
public class LogoutServlet extends BaseServlet {

	private static final Logger logger = LogManager.getLogger(LoginServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		     String path="login.jsp";
		     request.getSession().invalidate();
		     //跳转页面
		     process(request, response, path);
		
	}
	


}

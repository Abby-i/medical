package com.gxuwz.medical.web.servlet;

import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gxuwz.medical.domain.menu.Menu;
import com.gxuwz.medical.domain.role.Role;
import com.gxuwz.medical.domain.user.User;
import com.gxuwz.medical.exception.UserNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends BaseServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = LogManager.getLogger(LoginServlet.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		login(request, response);
	}

	private void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path="login.jsp";
		LOG.info("用户登录");
		try{
			String userid=request.getParameter("userid");
			String pwd   =request.getParameter("pwd");
			User user=new User(userid,pwd);
			request.getSession().setAttribute("username", user.getFullname());
			/**
			 * 查询出用户对的角色所拥有的权限，并存到session域对象
			 */
			for(Role role:user.getRoles()){
				for(Menu menu:role.getMenus()){
					String name=menu.getMenuid();
					String value=menu.getMenuid();
					request.getSession().setAttribute(name, value);
				}
			}

			path="index.jsp";
			}catch(UserNotFoundException e){
				LOG.info(e.getMessage(),e);
				path="login.jsp";
			}
			 //跳转页面
			process(request, response, path);
			
		}
}

package com.gxuwz.medical.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * �Ƿ����ʹ�����
 * @author ��ʾ
 *
 */

public class AuthorFilter implements Filter {

	public void destroy() {}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			//判断Session
			HttpSession httpSession=((HttpServletRequest) request).getSession();
			if(httpSession.getAttribute("username")==null){
				((HttpServletResponse) response).sendRedirect("/medical/login.jsp");
			}else{
			//放行
			chain.doFilter(request, response);
			
		}

	}

	public void init(FilterConfig config) throws ServletException {
		

	}

}

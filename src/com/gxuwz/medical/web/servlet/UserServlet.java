package com.gxuwz.medical.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gxuwz.medical.dao.RoleDao;
import com.gxuwz.medical.dao.UserDao;
import com.gxuwz.medical.domain.role.Role;
import com.gxuwz.medical.domain.user.User;
import com.gxuwz.medical.domain.vo.PageBean;

public class UserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String m =request.getParameter("m");
		if("list".equals(m)){
			list(request, response);
		}else if("add".equals(m)){
			add(request, response);
		}else if("get".equals(m)){
			get(request, response);
		}else if("del".equals(m)){
			del(request, response);
		}else if("input".equals(m)){
			input(request, response);
		}else if("edit".equals(m)){
			edit(request, response);
		}
	}

	private void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String userid = request.getParameter("userid");
		
		UserDao userdao = new UserDao();
		//查询出该用户的信息
		User user = userdao.querybyId(userid);
		System.out.println();
		System.out.println(user.getFullname());
		request.setAttribute("user", user);
		
		RoleDao roledao = new RoleDao();
		//查询出所有角色信息
		try {
			String sql="select * from t_role";
			Object[]params={};
			List<Role> roleList=roledao.queryOjects(sql, params);
			
			request.setAttribute("roleList", roleList);
			
			//查询出原已选中的角色信息
			List<String> hasChecked = roledao.queryhasChecked(userid);
			
			request.setAttribute("hasChecked", hasChecked);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		request.getRequestDispatcher("/page/user/edit.jsp").forward(request, response);
		
	}

	private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String userid = request.getParameter("userid");
		String pwd = request.getParameter("pwd");
		String fullname = request.getParameter("fullname");
		String agencode = request.getParameter("agencode");
		String status ="1";//默认正常
		String[] roleids = request.getParameterValues("roleids");
		//1:实例化User
		User user =new User(userid, pwd, fullname, status, agencode);
		//
		try {
			user.editUser(roleids);
			list(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void input(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		RoleDao roledao = new RoleDao();
		List<Role> roles = roledao.queryRoles();
		
		request.setAttribute("roles", roles);
		
		request.getRequestDispatcher("/page/user/add.jsp").forward(request, response);
	}

	private void del(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		String userid = request.getParameter("userid");
		System.out.println(userid);
		try {
			//1:实例化User
			User user =new User();
			//删除+解除绑定
			user.delUser(userid);
			request.getRequestDispatcher("/system/UserServlet?m=list").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException  {
		
		String userid = request.getParameter("userid");
		String pwd = request.getParameter("pwd");
		String fullname = request.getParameter("fullname");
		String agencode = request.getParameter("agencode");
		String status ="1";//默认正常
		String[] roleids = request.getParameterValues("roleids");
		//1:实例化User
		User user =new User(userid, pwd, fullname, status, agencode);
		//2:调用方法
		try{
			user.addUser(roleids);
			request.getRequestDispatcher("/page/user/tips.jsp").forward(request, response);
		}catch(Exception e){
			e.printStackTrace();
			request.getRequestDispatcher("/page/user/tips_error.jsp").forward(request, response);
		}
	}

	private void list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException  {

		pageDao(request, response);
		request.getRequestDispatcher("/page/user/user_list.jsp").forward(request, response);
	}

	private void pageDao(HttpServletRequest request, HttpServletResponse response){
		PageBean pageBean = new PageBean();
		
		UserDao userdao = new UserDao();
		//1.3从用户参数中获取当前页数据（currPage）
		String currentPage = request.getParameter("currentPage");
		String pageSize = request.getParameter("pageSize");
		if(currentPage==null || currentPage.equals("")){
			  currentPage="1";
		}
		if(pageSize==null || pageSize.equals("")){
			pageSize = "10";
		}
		pageBean.setCurrentPage(Integer.parseInt(currentPage));
		//从数据库查询出总数
		int count = pageBean.queryCount("t_user");
		pageBean.setTotalCount(count);
		//1.5 每页显示记录数
		pageBean.setPageSize(Integer.parseInt(pageSize));		
		
		//从数据库中读取当前页数据
		List<User> users = userdao.queryUser(pageBean.getCurrentPage(), pageBean.getPageSize());
		pageBean.setData(users);
				
		//2)把PageBean对象放入域对象中
		request.setAttribute("pageBean", pageBean);
		
		HttpSession httpSession = request.getSession();
		httpSession.setAttribute("users", users);
		
	}
}
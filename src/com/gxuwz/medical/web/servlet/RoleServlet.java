package com.gxuwz.medical.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gxuwz.medical.dao.RoleDao;
import com.gxuwz.medical.domain.role.Role;
import com.gxuwz.medical.domain.vo.PageBean;
/**
 * 角色管理控制模块
 * @author 演示
 *
 */
public class RoleServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String m =request.getParameter("m");
		if("list".equals(m)){
			list(request, response);
		}else if("add".equals(m)){
			add(request, response);
		}else if("edit".equals(m)){
			edit(request, response);
		}else if("input".equals(m)){
			process(request, response, "/page/role/role_add.jsp");
		}else if("get".equals(m)){
			get(request, response);
		}else if("del".equals(m)){
			del(request, response);
			list(request, response);
		}else if("input".equals(m)){
			input(request, response);
		}
	}

	private void input(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.getRequestDispatcher("/page/role/role_add.jsp").forward(request, response);
	}

	private void del(HttpServletRequest request, HttpServletResponse response) {
		String roleid = request.getParameter("roleid");
		
		//2:实例化角色Role
		Role role=new Role();
		
		try {
			//3：删除记录
			role.delRole(roleid);
			//不用再跳了
		} catch (Exception e) {

			e.printStackTrace();
		}
		
	}

	private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String roleid=request.getParameter("roleid");
		String rolename = request.getParameter("rolename");
		//权限树勾选的模块
		String moduleIds = request.getParameter("moduleIds");
		//选中的menuid
		String menuids[] = moduleIds.split(",");
		Role role=new Role(roleid,rolename);
		try {
			//1.修改角色信息2.解绑角色、权限3.再捆绑
			role.editRole(menuids);
			list(request,response);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}

	private void list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		pageDao(request, response);
		request.getRequestDispatcher("/page/role/role_list.jsp").forward(request, response);
	}
	

	private void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		String roleid = request.getParameter("roleid");
		String rolename = request.getParameter("rolename");
		
		String moduleIds = request.getParameter("moduleIds");
		String menuids[] = moduleIds.split(",");
		Role role = new Role(roleid,rolename);
		try {
			role.addRole(rolename, menuids);
			request.getRequestDispatcher("/page/role/tips_add_success.jsp").forward(request, response);
		} catch (Exception e) {
			request.getRequestDispatcher("/page/role/tips_add_error.jsp").forward(request, response);
		}
		
		
	}
	
	private void get(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		String roleid = request.getParameter("roleid");
		RoleDao roledao = new RoleDao();
		//查询出修改角色的信息
		Role role = roledao.querybyId(roleid);
		request.setAttribute("role", role);
		request.getRequestDispatcher("/page/role/role_edit.jsp").forward(request, response);
		
	}
	
	private void pageDao(HttpServletRequest request, HttpServletResponse response){
		PageBean pageBean = new PageBean();
		
		RoleDao roledao = new RoleDao();
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
		int count = pageBean.queryCount("t_role");
		pageBean.setTotalCount(count);
		//1.5 每页显示记录数
		pageBean.setPageSize(Integer.parseInt(pageSize));		
		
		//从数据库中读取当前页数据
		List<Role> roles = roledao.queryRole(pageBean.getCurrentPage(), pageBean.getPageSize());
		pageBean.setData(roles);
				
		//2)把PageBean对象放入域对象中
		request.setAttribute("pageBean", pageBean);
		
		HttpSession httpSession = request.getSession();
		httpSession.setAttribute("roles", roles);
	}
    
}

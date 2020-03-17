package com.gxuwz.medical.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gxuwz.medical.dao.IllExpenseDao;
import com.gxuwz.medical.dao.RemittanceExpenseDao;
import com.gxuwz.medical.domain.illExpense.IllExpense;
import com.gxuwz.medical.domain.vo.PageBean;

/**
 * Servlet implementation class RemittanceExpenseServlet
 */
public class RemittanceExpenseServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	RemittanceExpenseDao remittanceExpenseDao = new RemittanceExpenseDao();
	IllExpense illExpense = new IllExpense();
	IllExpenseDao illExpenseDao = new IllExpenseDao();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String m =request.getParameter("m");
		if("list".equals(m)){
			list(request,response);
		}else if("edit".equals(m)){
			edit(request,response);
		}else if("get".equals(m)){
			get(request,response);
		}
		
	}

	private void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		IllExpense illExpense = illExpenseDao.queryById(Integer.valueOf(id));
		System.out.println(illExpense);
		request.setAttribute("illExpense", illExpense);
		process(request, response, "/page/remittanceExpense/remittanceExpense_edit.jsp");
	}

	private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String remittanceStatuses = request.getParameter("remittanceStatuses");
		String id = request.getParameter("id");
		String agreetor=request.getParameter("agreetor");
		System.out.println("remittanceStatus:"+remittanceStatuses);
		System.out.println("id:"+id);
		System.out.println("agreetor:"+agreetor);
		try {
			illExpense.update(remittanceStatuses,agreetor,id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PageBean pageBean = new PageBean();
		String town = request.getParameter("town");
		String village = request.getParameter("village");
		String group = request.getParameter("group");
		String name = request.getParameter("name");
		String remittanceStatus=request.getParameter("remittanceStatus");//汇款状态
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
		int count = remittanceExpenseDao.queryCount(town,village,group,name,remittanceStatus);
		pageBean.setTotalCount(count);
		//1.5 每页显示记录数
		pageBean.setPageSize(Integer.parseInt(pageSize));		
		
		//从数据库中读取当前页数据
		//System.out.println("household:"+household);
		List<IllExpense> illExpenses = remittanceExpenseDao.queryExpense(town,village,group,name,remittanceStatus, pageBean.getCurrentPage(), pageBean.getPageSize());
		System.out.println(illExpenses);
		pageBean.setData(illExpenses);
				
		//2)把PageBean对象放入域对象中
		request.setAttribute("pageBean", pageBean);
		
		HttpSession httpSession = request.getSession();
		httpSession.setAttribute("illExpenses", illExpenses);
		//response.sendRedirect(request.getContextPath()+"/page/remittanceExpense/remittanceExpense_list.jsp");
		process(request, response, "/page/remittanceExpense/remittanceExpense_list.jsp");
		
	}

}

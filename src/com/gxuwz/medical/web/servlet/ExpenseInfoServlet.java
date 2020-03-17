package com.gxuwz.medical.web.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gxuwz.medical.dao.ChronicdisDao;
import com.gxuwz.medical.dao.ExpenseInfoDao;
import com.gxuwz.medical.domain.chronicdis.Chronicdis;
import com.gxuwz.medical.domain.illExpense.IllExpense;
import com.gxuwz.medical.domain.util.PoiUtil;
import com.gxuwz.medical.domain.vo.PageBean;

public class ExpenseInfoServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	ChronicdisDao chronicdisDao = new ChronicdisDao();
	ExpenseInfoDao expenseInfoDao = new ExpenseInfoDao();
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String m =request.getParameter("m");
		if("list".equals(m)){
			list(request,response);
		}else if("outExcel".equals(m)){
			outExcel(request,response);
		}
		
	}

	private void outExcel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
	
		List<IllExpense> illExpense = (List<IllExpense>) request.getSession().getAttribute("illExpensesInfo");
		
		try{
		    	response.setContentType("application/x-excel");
		    	response.setHeader("Content-Disposition", "attachment;filename=" + new String("参合缴费凭证.xls".getBytes(), "ISO-8859-1"));
		    	OutputStream outputStream = response.getOutputStream();
		    	PoiUtil poiUtil = new PoiUtil();
		    		poiUtil.exportExpenseInfo(illExpense,outputStream);
			    	if(outputStream != null){
			    		outputStream.close();
			    	}
			
		    
		    }catch(IOException e){
		    	e.printStackTrace();
		    	
		    }
		
	}

	private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String illCode = request.getParameter("illCode");
		String town = request.getParameter("town");
		String village = request.getParameter("village");
		String group = request.getParameter("group");
		String name = request.getParameter("name");
		try {
			
			Object[] params = {};
			List<Chronicdis> chronicdisList = chronicdisDao.queryObject("select * from t_chronicdis", params);
			System.out.println(chronicdisList);
			request.setAttribute("chronicdisList", chronicdisList);
			
			PageDao(illCode,town,village,group,name,request);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		process(request, response, "/page/expenseInfo/expenseInfo_list.jsp");
	}

	private void PageDao(String illCode, String town, String village, String group, String name, HttpServletRequest request) {
		PageBean pageBean = new PageBean();
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
		int count = expenseInfoDao.queryCount(illCode,town,village,group,name);
		pageBean.setTotalCount(count);
		//1.5 每页显示记录数
		pageBean.setPageSize(Integer.parseInt(pageSize));		
		
		//从数据库中读取当前页数据
		//System.out.println("household:"+household);
		List<IllExpense> illExpensesInfo = expenseInfoDao.queryExpenseInfo(illCode,town,village,group,name, pageBean.getCurrentPage(), pageBean.getPageSize());
		System.out.println(illExpensesInfo);
		pageBean.setData(illExpensesInfo);
				
		//2)把PageBean对象放入域对象中
		request.setAttribute("pageBean", pageBean);
		
		HttpSession httpSession = request.getSession();
		httpSession.setAttribute("illExpensesInfo", illExpensesInfo);
		
	}

}

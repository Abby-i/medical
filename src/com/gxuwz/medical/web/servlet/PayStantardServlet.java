package com.gxuwz.medical.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gxuwz.medical.dao.PayStandardDao;
import com.gxuwz.medical.domain.payStandard.PayStantard;
import com.gxuwz.medical.domain.util.AgeUtil;
import com.gxuwz.medical.domain.vo.PageBean;

public class PayStantardServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	PayStandardDao standardDao = new PayStandardDao();
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String m =request.getParameter("m");
		if("list".equals(m)){
			list(request,response);
		}else if("add".equals(m)){
			String annual  = request.getParameter("annual");
			String accountStr  = request.getParameter("account");
			String startTimeStr  = request.getParameter("startTime");
			String endTimeStr  = request.getParameter("endTime");
			AgeUtil ageUtil = new AgeUtil();
			try {
				double account =Double.parseDouble(accountStr);
				Date startTime = ageUtil.toDateFormat(startTimeStr);
				Date endTime = ageUtil.toDateFormat(endTimeStr);
				PayStantard payStantard = new PayStantard(annual,account,startTime,endTime);
				payStantard.add();
			} catch (Exception e) {
				process(request, response, "该年度缴费标准已存在！");
			}
			
			
		}else if("edit".equals(m)){
			edit(request,response);
		}else if("input".equals(m)){
			input(request,response);
		}else if("get".equals(m)){
			get(request,response);
		}else if("del".equals(m)){
			del(request,response);
		}
	}

	private void del(HttpServletRequest request, HttpServletResponse response) {
		String annual  = request.getParameter("id");
		try {
			PayStantard payStantard = new PayStantard();
			payStantard.del(annual);
			list(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void edit(HttpServletRequest request, HttpServletResponse response) {
		String annual  = request.getParameter("annual");
		String accountStr  = request.getParameter("account");
		String startTimeStr  = request.getParameter("startTime");
		String endTimeStr  = request.getParameter("endTime");
		AgeUtil ageUtil = new AgeUtil();
		double account =Double.parseDouble(accountStr);
		
		try {
			Date startTime = ageUtil.toDateFormat(startTimeStr);
			Date endTime = ageUtil.toDateFormat(endTimeStr);
			PayStantard payStantard = new PayStantard(annual,account,startTime,endTime);
			payStantard.edit();
			list(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	private void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String annual = request.getParameter("id");
		PayStantard payStantard =standardDao.findById(annual);
		request.setAttribute("payStantard",payStantard );
		process(request, response, "/page/payStandard/payStantard_edit.jsp");
	}

	private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		pageDao(request, response);
		process(request, response, "/page/payStandard/payStantard_list.jsp");
	}

	private void pageDao(HttpServletRequest request, HttpServletResponse response) {
		PageBean pageBean = new PageBean();
		
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
		int count = pageBean.queryCount("t_pay_standard");
		pageBean.setTotalCount(count);
		//1.5 每页显示记录数
		pageBean.setPageSize(Integer.parseInt(pageSize));		
		
		//从数据库中读取当前页数据
		List<PayStantard> stantards = standardDao.queryPages("select * from t_pay_standard limit ?,?", pageBean.getCurrentPage(), pageBean.getPageSize());
		pageBean.setData(stantards);
				
		//2)把PageBean对象放入域对象中
		request.setAttribute("pageBean", pageBean);
		
		HttpSession httpSession = request.getSession();
		httpSession.setAttribute("stantards", stantards);
	}
		

	private void input(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AgeUtil ageUtil = new AgeUtil();
		String nowyear = ageUtil.getNowyear();
		request.setAttribute("nowyear", nowyear);
		process(request, response, "/page/payStandard/payStantard_add.jsp");
	}

}

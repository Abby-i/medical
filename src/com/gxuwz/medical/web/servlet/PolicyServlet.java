package com.gxuwz.medical.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gxuwz.medical.dao.PolicyDao;
import com.gxuwz.medical.domain.policy.Policy;
import com.gxuwz.medical.domain.vo.PageBean;

/**
 * Servlet implementation class PolicyServlet
 */
public class PolicyServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PolicyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String m = request.getParameter("m");
		if ("list".equals(m)) {
			list(request,response);
		} else if("del".equals(m)){
			del(request,response);
		} else if ("input".equals(m)) {
			
			process(request, response, "/page/policy/policy_add.jsp");
		} else if ("get".equals(m)) {
			get(request, response);
		} else if("add".equals(m)){
		  add(request,response);
		}else if("edit".equals(m)){
			edit(request,response);
		}
	}

	private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id =Integer.parseInt(request.getParameter("id"));
		System.out.println("id="+id);
		String annual = request.getParameter("annual");
		Double ceiling =Double.parseDouble(request.getParameter("ceiling"));
		Double ratio = Double.parseDouble(request.getParameter("ratio"));
		Policy policy = new Policy();
		try {
			policy.edit(id,annual,ceiling,ratio);
			list(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String annual = request.getParameter("annual");
		Double ceiling =Double.parseDouble(request.getParameter("ceiling"));
		Double ratio = Double.parseDouble(request.getParameter("ratio"));
		Policy policy = new Policy();
		try {
			policy.add(annual,ceiling,ratio);
			list(request, response);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	private void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PolicyDao policyDao = new PolicyDao();
		String id = request.getParameter("id");
		Policy policy = policyDao.queryById(id);
		request.setAttribute("policy", policy);
		process(request, response, "/page/policy/policy_edit.jsp");
	}

	private void del(HttpServletRequest request, HttpServletResponse response) {
		int  id = Integer.parseInt(request.getParameter("id"));
		Policy policy = new Policy();
		try {
			policy.del(id);
			list(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		pageDao(request, response);
		process(request, response, "/page/policy/policy_list.jsp");
		
	}

	private void pageDao(HttpServletRequest request, HttpServletResponse response) {
		
		PageBean pageBean = new PageBean();
		PolicyDao policyDao = new PolicyDao();
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
		int count = pageBean.queryCount("t_policy");
		pageBean.setTotalCount(count);
		//1.5 每页显示记录数
		pageBean.setPageSize(Integer.parseInt(pageSize));		
		
		//从数据库中读取当前页数据
		List<Policy> policylist = policyDao.queryPages("select * from t_policy ORDER BY annual asc limit ?,?",pageBean.getCurrentPage(), pageBean.getPageSize());
		pageBean.setData(policylist);
				
		//2)把PageBean对象放入域对象中
		request.setAttribute("pageBean", pageBean);
		
		HttpSession httpSession = request.getSession();
		httpSession.setAttribute("policylist", policylist);
		
	}
		

}
